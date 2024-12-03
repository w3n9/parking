package tech.w3n9.ddd.parking.domain.aggregation

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import tech.w3n9.ddd.parking.domain.Plate
import tech.w3n9.ddd.parking.domain.aggregation.ParkingImpl
import tech.w3n9.ddd.parking.domain.command.CheckInCommand
import tech.w3n9.ddd.parking.domain.command.CheckOutCommand
import tech.w3n9.ddd.parking.domain.command.PayFeeCommand
import tech.w3n9.ddd.parking.domain.event.CheckInSucceedEvent
import tech.w3n9.ddd.parking.domain.exception.DomainException
import java.time.OffsetDateTime

class ParkingImplTest(
) {

    private lateinit var plate: Plate
    private lateinit var parking: ParkingImpl

    @BeforeEach
    internal fun setUp() {
        this.plate =  Plate("测试牌照")
        this.parking =  ParkingImpl(id = this.plate)
    }

    @Test
    fun `test calculateFee without checkInTime`() {
        try{
            parking.calculateFee(OffsetDateTime.now())
        }catch (e: DomainException){
            assertEquals(e.message,"没有入场信息")
        }
    }

    @Test
    fun `test calculateFee within one hour`(){
        val now = OffsetDateTime.now()
        parking.checkInTime = now.minusMinutes(20)
        assertEquals(1,parking.calculateFee(now))
    }

    @Test
    fun `test calculateFee more than one hour but less than two hours`(){
        val now = OffsetDateTime.now()
        parking.checkInTime = now.minusMinutes(70)
        assertEquals(2,parking.calculateFee(now))
    }

    @Test
    fun `test calculateFee less than 15 minutes since last payTime`(){
        //模拟停了59分钟就付款，然后10分钟后再次触发计算费用
        val now = OffsetDateTime.now()
        val tenMinutesAgo = now.minusMinutes(10)
        parking.checkInTime = now.minusMinutes(69)
        parking.totalPaid = parking.calculateFee(tenMinutesAgo)
        parking.lastPaidTime = tenMinutesAgo
        assertEquals(0,parking.calculateFee(now))
    }

    @Test
    fun `test calculateFee more than 15 minutes since last payTime`(){
        //模拟停了59分钟就付款，然后10分钟后再次触发计算费用
        val now = OffsetDateTime.now()
        val sixteenMinutesAgo = now.minusMinutes(16)
        parking.checkInTime = now.minusMinutes(69)
        parking.totalPaid = parking.calculateFee(sixteenMinutesAgo)
        parking.lastPaidTime = sixteenMinutesAgo
        assertEquals(1,parking.calculateFee(now))
    }

    @Test
    fun `complex scenario`(){
        val now = OffsetDateTime.now()
        val checkInTime = now.minusMinutes(10)
        val thirtyMinutesSinceCheckInTime = checkInTime.plusMinutes(30)
        val ninetyMinutesSinceCheckInTime = checkInTime.plusMinutes(90)

        val checkInEventQueue = TestEventQueue()
        assertFalse(parking.inPark())
        parking.checkIn(checkInEventQueue,CheckInCommand(plate,checkInTime))
        assertTrue(parking.inPark())
        assertEquals(1,checkInEventQueue.queue().size)
        assertTrue(checkInEventQueue.queue().get(0) is CheckInSucceedEvent)
        //入场30分钟查询费用应当是一块钱
        val firstFee = parking.calculateFee(thirtyMinutesSinceCheckInTime)
        assertEquals(1,firstFee)
        //付钱
        val firstPayFeeEventQueue = TestEventQueue()
        parking.payFee(firstPayFeeEventQueue, PayFeeCommand(plate, fee = firstFee, payTime = thirtyMinutesSinceCheckInTime))

        //超时，并且重新计费时有未结清费用，离场失败
        val firstCheckOutEventQueue = TestEventQueue()
        assertFalse(parking.checkOut(firstCheckOutEventQueue, CheckOutCommand(plate,ninetyMinutesSinceCheckInTime)))

        //再计算费用
        val secondFee = parking.calculateFee(ninetyMinutesSinceCheckInTime)
        assertEquals(1,secondFee)

        //再次付费
        val secondPayFeeEventQueue = TestEventQueue()
        parking.payFee(secondPayFeeEventQueue, PayFeeCommand(plate, fee = secondFee,payTime = ninetyMinutesSinceCheckInTime))

        val secondCheckOutEventQueue = TestEventQueue()
        assertTrue(parking.checkOut(secondCheckOutEventQueue, CheckOutCommand(plate,ninetyMinutesSinceCheckInTime)))
    }

    @Test
    fun `checkIn then checkOut then checkIn`(){
        val now = OffsetDateTime.now()
        val checkInTime = now.minusMinutes(10)
        val thirtyMinutesSinceCheckInTime = checkInTime.plusMinutes(30)
        val checkInEventQueue = TestEventQueue()
        assertTrue(parking.checkIn(checkInEventQueue,CheckInCommand(plate,checkInTime)))
        //入场30分钟查询费用应当是一块钱
        val firstFee = parking.calculateFee(thirtyMinutesSinceCheckInTime)
        assertEquals(1,firstFee)
        //付钱
        val firstPayFeeEventQueue = TestEventQueue()
        parking.payFee(firstPayFeeEventQueue, PayFeeCommand(plate, fee = firstFee, payTime = thirtyMinutesSinceCheckInTime))
        val firstCheckOutEventQueue = TestEventQueue()
        assertTrue(parking.checkOut(firstCheckOutEventQueue, CheckOutCommand(plate,thirtyMinutesSinceCheckInTime)))
        val secondCheckInQueue = TestEventQueue()
        assertTrue(parking.checkIn(secondCheckInQueue,CheckInCommand(plate,thirtyMinutesSinceCheckInTime.plusMinutes(1))))
    }
}
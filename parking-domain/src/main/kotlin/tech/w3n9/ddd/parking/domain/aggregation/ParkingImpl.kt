package tech.w3n9.ddd.parking.domain.aggregation

import tech.w3n9.ddd.parking.domain.Plate
import tech.w3n9.ddd.parking.domain.command.CalculateFeeCommand
import tech.w3n9.ddd.parking.domain.command.CheckInCommand
import tech.w3n9.ddd.parking.domain.command.CheckOutCommand
import tech.w3n9.ddd.parking.domain.command.PayFeeCommand
import tech.w3n9.ddd.parking.domain.event.*
import tech.w3n9.ddd.parking.domain.exception.DomainException
import tech.w3n9.ddd.parking.domain.queue.EventQueue
import java.time.Duration
import java.time.OffsetDateTime

class ParkingImpl(
    val id: Plate,
    var checkInTime: OffsetDateTime? = null,
    var lastPaidTime: OffsetDateTime? = null,
    var totalPaid: Int = 0
) : Parking {
    override fun checkIn(eventQueue: EventQueue, command: CheckInCommand): Boolean {
        if (inPark()) {
            eventQueue.enqueue(CheckInFailedEvent(id, OffsetDateTime.now()))
            return false
        }
        this.checkInTime = command.checkInTime
        eventQueue.enqueue(CheckInSucceedEvent(id, command.checkInTime))
        return true
    }

    override fun inPark(): Boolean {
        return checkInTime != null
    }

    override fun calculateFee(calculateTime:OffsetDateTime): Int {
        this.checkInTime ?: throw DomainException("没有入场信息")
        if (lastPaidTime == null) {
            //未付过款
            return hoursBetween(this.checkInTime!!, calculateTime)
        }
        if (this.lastPaidTime!!.plusMinutes(15).isBefore(calculateTime)) {
            return hoursBetween(this.checkInTime!!, calculateTime) - totalPaid
        }
        return 0
    }

    override fun payFee(eventQueue: EventQueue, command: PayFeeCommand) {
        if (!inPark()) {
            throw DomainException("没有入场信息")
        }
        lastPaidTime = command.payTime
        totalPaid += command.fee
        eventQueue.enqueue(PaidEvent(id, command.fee, command.payTime))
    }


    override fun checkOut(eventQueue: EventQueue, command: CheckOutCommand): Boolean {
        if (!inPark()) {
            eventQueue.enqueue(CheckOutFailedEvent(id, command.checkoutTime, "没有入场信息"))
            return false
        }
        val fee = calculateFee(command.checkoutTime)
        if(fee == 0){
            clear()
            eventQueue.enqueue(CheckOutSucceedEvent(id, command.checkoutTime))
            return true
        }
        return false
    }

    private fun clear(){
        this.checkInTime = null
        this.lastPaidTime = null
        this.totalPaid = 0
    }

    fun hoursBetween(startTime: OffsetDateTime, endTime: OffsetDateTime): Int {
        val between = Duration.between(startTime, endTime)
        val minutes = between.toMinutes()
        var result = minutes / 60
        if (minutes % 60L != 0L) {
            result += 1
        }
        return result.toInt()
    }
}
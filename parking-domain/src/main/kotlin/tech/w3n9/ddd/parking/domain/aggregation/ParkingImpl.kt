package tech.w3n9.ddd.parking.domain.aggregation

import tech.w3n9.ddd.parking.domain.Plate
import tech.w3n9.ddd.parking.domain.command.CheckInCommand
import tech.w3n9.ddd.parking.domain.event.CheckInFailedEvent
import tech.w3n9.ddd.parking.domain.event.CheckInSucceedEvent
import tech.w3n9.ddd.parking.domain.queue.EventQueue
import java.time.OffsetDateTime

class ParkingImpl(
    val id: Plate,
    var checkInTime: OffsetDateTime? = null,
    var lastPaidTime: OffsetDateTime? = null,
    var totalPaid: Int = 0
) : Parking {
    override fun checkIn(eventQueue: EventQueue, command: CheckInCommand): Boolean {
        if(inPark()){
            eventQueue.enqueue(CheckInFailedEvent(id, OffsetDateTime.now()))
            return false
        }
        this.checkInTime = command.checkInTime
        eventQueue.enqueue(CheckInSucceedEvent(id,command.checkInTime))
        return true
    }

    private fun inPark(): Boolean {
        return checkInTime != null
    }

}
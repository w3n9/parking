package tech.w3n9.ddd.parking.domain.aggregation

import tech.w3n9.ddd.parking.domain.command.CheckInCommand
import tech.w3n9.ddd.parking.domain.queue.EventQueue

interface Parking {
    fun checkIn(eventQueue: EventQueue, command: CheckInCommand): Boolean
}


package tech.w3n9.ddd.parking.domain.aggregation

import tech.w3n9.ddd.parking.domain.command.CalculateFeeCommand
import tech.w3n9.ddd.parking.domain.command.CheckInCommand
import tech.w3n9.ddd.parking.domain.command.CheckOutCommand
import tech.w3n9.ddd.parking.domain.command.PayFeeCommand
import tech.w3n9.ddd.parking.domain.queue.EventQueue
import java.time.OffsetDateTime

interface Parking {
    fun checkIn(eventQueue: EventQueue, command: CheckInCommand): Boolean
    fun calculateFee(calculateTime:OffsetDateTime): Int
    fun payFee(eventQueue: EventQueue, command: PayFeeCommand)
    fun checkOut(eventQueue: EventQueue, command: CheckOutCommand): Boolean
    fun inPark(): Boolean
}


package tech.w3n9.ddd.parking.domain.command.handler

import org.springframework.stereotype.Component
import tech.w3n9.ddd.parking.domain.aggregation.ParkingImpl
import tech.w3n9.ddd.parking.domain.command.CalculateFeeCommand
import tech.w3n9.ddd.parking.domain.command.CheckInCommand
import tech.w3n9.ddd.parking.domain.queue.EventQueue
import tech.w3n9.ddd.parking.domain.repository.ParkingRepository
import java.time.OffsetDateTime

@Component
class CalculateFeeCommandHandler(
    private val parkingRepository: ParkingRepository
) {

    fun handle(eventQueue: EventQueue,command: CalculateFeeCommand): Int {
        var parking = parkingRepository.findById(command.plate)
        if (parking == null) {
            parking = ParkingImpl(command.plate)
        }
        return parking.calculateFee(command.calculateTime)
    }
}
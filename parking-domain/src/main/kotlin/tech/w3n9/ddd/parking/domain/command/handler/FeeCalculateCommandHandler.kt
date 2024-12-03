package tech.w3n9.ddd.parking.domain.command.handler

import org.springframework.stereotype.Component
import tech.w3n9.ddd.parking.domain.command.CheckInCommand
import tech.w3n9.ddd.parking.domain.repository.ParkingRepository

@Component
class FeeCalculateCommandHandler(
    private val parkingRepository: ParkingRepository
) {

    fun handle(command: CheckInCommand): Int {
        var parking = parkingRepository.findById(command.plate)

        return 0
    }
}
package tech.w3n9.ddd.parking.domain.command.handler

import org.springframework.stereotype.Component
import tech.w3n9.ddd.parking.domain.aggregation.ParkingImpl
import tech.w3n9.ddd.parking.domain.command.CheckInCommand
import tech.w3n9.ddd.parking.domain.queue.EventQueue
import tech.w3n9.ddd.parking.domain.repository.ParkingRepository

@Component
class CheckInCommandHandler(
    private val parkingRepository: ParkingRepository
) {

    fun handle(eventQueue:EventQueue,command: CheckInCommand): Boolean {
        var parking = parkingRepository.findById(command.plate)
        if(parking == null){
            parking = ParkingImpl(command.plate)
        }
        val result = parking.checkIn(eventQueue,command)
        parkingRepository.save(parking)
        return result
    }
}
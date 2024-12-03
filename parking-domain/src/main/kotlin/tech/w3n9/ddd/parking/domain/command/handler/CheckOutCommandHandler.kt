package tech.w3n9.ddd.parking.domain.command.handler

import org.springframework.stereotype.Component
import tech.w3n9.ddd.parking.domain.aggregation.ParkingImpl
import tech.w3n9.ddd.parking.domain.command.CheckOutCommand
import tech.w3n9.ddd.parking.domain.queue.EventQueue
import tech.w3n9.ddd.parking.domain.repository.ParkingRepository


@Component
class CheckOutCommandHandler(
    private val parkingRepository: ParkingRepository
){

    fun handle(eventQueue: EventQueue,command: CheckOutCommand){
        var parking = parkingRepository.findById(command.plate)
        if(parking == null){
            parking = ParkingImpl(command.plate)
        }
        parking.checkOut(eventQueue,command)
        parkingRepository.save(parking)
    }
}

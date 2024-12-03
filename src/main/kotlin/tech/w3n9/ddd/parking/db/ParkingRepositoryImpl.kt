package tech.w3n9.ddd.parking.db

import org.springframework.stereotype.Repository
import tech.w3n9.ddd.parking.domain.Plate
import tech.w3n9.ddd.parking.domain.aggregation.Parking
import tech.w3n9.ddd.parking.domain.repository.ParkingRepository

@Repository
class ParkingRepositoryImpl : ParkingRepository {
    override fun findById(plate: Plate): Parking? {
        TODO("Not yet implemented")
    }

    override fun save(parking: Parking) {
        TODO("Not yet implemented")
    }
}

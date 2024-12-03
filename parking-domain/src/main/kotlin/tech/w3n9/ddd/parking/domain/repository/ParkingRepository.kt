package tech.w3n9.ddd.parking.domain.repository

import tech.w3n9.ddd.parking.domain.Plate
import tech.w3n9.ddd.parking.domain.aggregation.Parking

interface ParkingRepository {
    fun findById(plate: Plate): Parking?
    fun save(parking: Parking)
}
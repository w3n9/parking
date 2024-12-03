package tech.w3n9.ddd.parking.domain.command

import tech.w3n9.ddd.parking.domain.Plate
import java.time.OffsetDateTime

data class CheckInCommand(
    val plate: Plate,
    val checkInTime: OffsetDateTime
)

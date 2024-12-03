package tech.w3n9.ddd.parking.domain.command

import tech.w3n9.ddd.parking.domain.Plate
import java.time.OffsetDateTime

data class CalculateFeeCommand(
    val plate: Plate,
    val calculateTime: OffsetDateTime
)
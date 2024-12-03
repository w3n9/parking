package tech.w3n9.ddd.parking.domain.command

import tech.w3n9.ddd.parking.domain.Plate
import java.time.OffsetDateTime

class CheckOutCommand(
    val plate: Plate,
    val checkoutTime: OffsetDateTime
)
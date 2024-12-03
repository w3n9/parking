package tech.w3n9.ddd.parking.domain.command

import tech.w3n9.ddd.parking.domain.Plate
import java.time.OffsetDateTime

class PayFeeCommand(
    val plate: Plate,
    val fee:Int,
    val payTime: OffsetDateTime
)
package tech.w3n9.ddd.parking.domain.event

import tech.w3n9.ddd.parking.domain.Plate
import java.time.OffsetDateTime

data class PaidEvent(
    val plate: Plate,
    val fee:Int,
    val payTime: OffsetDateTime
): DomainEvent
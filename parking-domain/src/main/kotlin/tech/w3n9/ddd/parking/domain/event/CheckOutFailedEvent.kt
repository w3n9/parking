package tech.w3n9.ddd.parking.domain.event

import tech.w3n9.ddd.parking.domain.Plate
import java.time.OffsetDateTime

data class CheckOutFailedEvent(
    val plate:Plate,
    val checkoutTime: OffsetDateTime,
    val message: String
):DomainEvent
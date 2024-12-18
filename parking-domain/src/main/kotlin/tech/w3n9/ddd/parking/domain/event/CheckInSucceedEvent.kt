package tech.w3n9.ddd.parking.domain.event

import tech.w3n9.ddd.parking.domain.Plate
import java.time.OffsetDateTime

data class CheckInSucceedEvent(
    val plate: Plate,
    val checkInTime: OffsetDateTime
):DomainEvent

package tech.w3n9.ddd.parking.domain.event

import tech.w3n9.ddd.parking.domain.Plate
import java.time.OffsetDateTime

class CheckInFailedEvent(
    val plate: Plate,
    val checkInTime: OffsetDateTime
) : DomainEvent
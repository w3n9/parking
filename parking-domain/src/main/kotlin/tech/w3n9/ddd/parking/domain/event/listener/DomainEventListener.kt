package tech.w3n9.ddd.parking.domain.event.listener

import tech.w3n9.ddd.parking.domain.event.DomainEvent

interface DomainEventListener {
    fun onEvent(event: DomainEvent)
}
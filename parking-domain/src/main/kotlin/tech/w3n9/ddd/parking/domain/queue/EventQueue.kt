package tech.w3n9.ddd.parking.domain.queue

import tech.w3n9.ddd.parking.domain.event.DomainEvent

interface EventQueue {

    fun enqueue(event: DomainEvent)

    fun queue(): List<DomainEvent>
}
package tech.w3n9.ddd.parking.domain.aggregation

import tech.w3n9.ddd.parking.domain.event.DomainEvent
import tech.w3n9.ddd.parking.domain.queue.EventQueue
import java.util.Deque

class TestEventQueue : EventQueue {
    private val queue = ArrayDeque<DomainEvent>()
    override fun enqueue(event: DomainEvent) {
        queue.addLast(event)
    }

    override fun queue(): List<DomainEvent> {
        return queue.toList()
    }
}
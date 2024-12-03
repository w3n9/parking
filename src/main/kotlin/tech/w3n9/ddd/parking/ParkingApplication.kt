package tech.w3n9.ddd.parking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParkingApplication

fun main(args: Array<String>) {
    runApplication<ParkingApplication>(*args)
}

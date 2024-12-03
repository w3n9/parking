package tech.w3n9.ddd.parking.controller

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import tech.w3n9.ddd.parking.controller.req.CheckInReq
import tech.w3n9.ddd.parking.controller.req.NowReq
import tech.w3n9.ddd.parking.domain.command.handler.CheckInCommandHandler
import java.time.OffsetDateTime
import java.time.ZoneOffset

@RestController
class CheckInController(
    private val checkInCommandHandler: CheckInCommandHandler
){

    @QueryMapping
    fun now():OffsetDateTime{
        return OffsetDateTime.now()
    }

    @MutationMapping
    fun checkIn(@Argument req:CheckInReq):Boolean{
        return true
    }

}
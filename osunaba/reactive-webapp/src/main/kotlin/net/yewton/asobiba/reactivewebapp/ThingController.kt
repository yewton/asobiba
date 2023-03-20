package net.yewton.asobiba.reactivewebapp

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.core.getAndAwait
import org.springframework.data.redis.core.setAndAwait
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Tag(name = "モノ")
@RestController
@RequestMapping("/things")
class ThingController(
    private val thingRepository: ThingRepository,
    private val reactiveStringRedisTemplate: ReactiveStringRedisTemplate
) {
    @Operation(
        summary = "識別子を指定してモノを取得します",
        description = "この操作は DB 問い合わせを伴います"
    )
    @GetMapping("/{id}")
    suspend fun get(
        @Parameter(description = "モノの識別子")
        @PathVariable
        id: UUID
    ): Thing = thingRepository.findById(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @Operation(
        summary = "識別子を指定してモノを取得します",
        description = "この操作は Redis 問い合わせを伴います"
    )
    @GetMapping("/fromRedis/{id}")
    suspend fun getFromRedis(
        @Parameter(description = "モノの識別子")
        @PathVariable
        id: UUID
    ): String =
        reactiveStringRedisTemplate.opsForValue().getAndAwait(id.toString())
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @Operation(
        summary = "モノを作成します",
        description = "DBとRedisにそれぞれ作成されます"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun add(
        @RequestBody
        thing: Thing
    ): Thing {
        val newThing = thingRepository.save(thing)
        reactiveStringRedisTemplate.opsForValue().setAndAwait(newThing.id.toString(), newThing.name)
        return newThing
    }
}

package net.yewton.asobiba.reactivewebapp

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
import java.util.UUID

@RestController
@RequestMapping("/things")
class ThingController(
    private val thingRepository: ThingRepository,
    private val reactiveStringRedisTemplate: ReactiveStringRedisTemplate
) {
    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: UUID): Thing = thingRepository.findById(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @GetMapping("/fromRedis/{id}")
    suspend fun getFromRedis(@PathVariable id: UUID): String =
        reactiveStringRedisTemplate.opsForValue().getAndAwait(id.toString())
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun add(@RequestBody thing: Thing): Thing {
        val newThing = thingRepository.save(thing)
        reactiveStringRedisTemplate.opsForValue().setAndAwait(newThing.id.toString(), newThing.name)
        return newThing
    }
}

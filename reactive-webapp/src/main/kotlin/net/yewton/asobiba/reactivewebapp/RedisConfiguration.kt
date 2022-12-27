package net.yewton.asobiba.reactivewebapp

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveStringRedisTemplate

@Configuration
internal class RedisConfiguration {
    /**
     * @param factory
     * @return
     */
    @Bean
    fun reactiveRedisTemplate(factory: ReactiveRedisConnectionFactory):
    ReactiveStringRedisTemplate = ReactiveStringRedisTemplate(factory)
}

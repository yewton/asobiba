package net.yewton.asobiba.nanka.web

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfig : CachingConfigurer {

    @Bean
    override fun cacheManager(): CacheManager {
        return CaffeineCacheManager().apply {
            setAsyncCacheMode(true)
            registerCustomCache("permanent", Caffeine.newBuilder().buildAsync())
            registerCustomCache("request", Caffeine.newBuilder().buildAsync())
        }
    }
}

package net.yewton.asobiba.reactivewebapp

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(initializers = [ConfigDataApplicationContextInitializer::class])
@ActiveProfiles("production")
class ProductionConfigTest(env: ConfigurableEnvironment) : FunSpec({
    test("R2DBC設定") {
        env.getProperty("spring.r2dbc.url") shouldContain "prod.yewton.net:5432"
    }
    test("Redis設定") {
        env.getProperty("spring.data.redis.host") shouldContain "prod.yewton.net"
    }
})

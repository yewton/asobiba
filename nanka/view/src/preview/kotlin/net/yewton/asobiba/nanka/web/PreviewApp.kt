package net.yewton.asobiba.nanka.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.env.ConfigurableEnvironment

// https://dev.to/zakhi/logging-in-kotlin-made-easy-546h
inline val <reified T> T.logger: Logger get() = LoggerFactory.getLogger(T::class.java)

@SpringBootApplication
class PreviewApp(val environment: ConfigurableEnvironment) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        // プロパティが意図した値に設定されているかな？を
        // 簡易に確認出来るようにしたい
        listOf("spring.thymeleaf.prefix").forEach {
            logger.info("$it: {}", environment.getProperty(it))
        }
    }
}

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<PreviewApp>(*args)
}

package net.yewton.asobiba.nanka.web

import net.yewton.asobiba.spring.boot.common.Logging
import net.yewton.asobiba.spring.boot.common.Logging.Extensions.logger
import net.yewton.asobiba.spring.boot.common.PropertyChecker
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.runApplication
import org.springframework.core.env.ConfigurableEnvironment

@SpringBootApplication
class App(val environment: ConfigurableEnvironment, val buildProperties: BuildProperties) : ApplicationRunner, Logging {

    override fun run(args: ApplicationArguments?) {
        logger.info("{}", buildProperties.name)
        PropertyChecker(environment).perform(
            listOf(
                "spring.security.oauth2.client.registration.github.clientId",
                "spring.security.oauth2.client.registration.github.clientSecret"
            )
        )
    }
}

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<App>(*args)
}

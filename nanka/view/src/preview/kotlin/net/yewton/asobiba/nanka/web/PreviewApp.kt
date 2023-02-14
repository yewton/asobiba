package net.yewton.asobiba.nanka.web

import net.yewton.asobiba.spring.boot.common.Logging
import net.yewton.asobiba.spring.boot.common.PropertyChecker
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.env.ConfigurableEnvironment

@SpringBootApplication
class PreviewApp(val environment: ConfigurableEnvironment) : ApplicationRunner, Logging {

    override fun run(args: ApplicationArguments?) {
        PropertyChecker(environment).perform(
            listOf(
                "spring.devtools.restart.additional-paths",
                "spring.web.resources.static-locations"
            )
        )
    }
}

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<PreviewApp>(*args)
}

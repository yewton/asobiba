package net.yewton.asobiba.nanka.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PreviewApp

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<PreviewApp>(*args)
}

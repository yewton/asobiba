package net.yewton.asobiba.reactivewebapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveWebappApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<ReactiveWebappApplication>(*args)
}

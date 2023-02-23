package net.yewton.asobiba.batista

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * 複数データソースを RoutingDataSource によって使い分ける
 */
@SpringBootApplication
@MapperScan(basePackages = ["net.yewton.asobiba.batista.mapper"])
class App

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<App>(*args)
}

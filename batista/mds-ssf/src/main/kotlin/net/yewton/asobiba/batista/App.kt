package net.yewton.asobiba.batista

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * 複数データソースを SqlSessionFactory によって使い分ける
 */
@SpringBootApplication
@MapperScan(
    basePackages = ["net.yewton.asobiba.batista.mapper.first"],
    sqlSessionFactoryRef = "firstSqlSessionFactory"
)
@MapperScan(
    basePackages = ["net.yewton.asobiba.batista.mapper.second"],
    sqlSessionFactoryRef = "secondSqlSessionFactory"
)
class App

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<App>(*args)
}

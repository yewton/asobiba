package net.yewton.asobiba.batista

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
class RoutingDataSourceConfig(
    @FirstDataSource val firstDataSource: DataSource,
    @SecondDataSource val secondDataSource: DataSource
) {

    @Bean
    @Primary
    fun dataSource() = RoutingDataSource().apply {
        setTargetDataSources(
            mapOf(
                MyDataSource.FIRST to firstDataSource,
                MyDataSource.SECOND to secondDataSource
            )
        )
        setDefaultTargetDataSource(firstDataSource)
    }
}

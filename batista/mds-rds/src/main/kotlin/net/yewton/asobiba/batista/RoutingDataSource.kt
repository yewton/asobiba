package net.yewton.asobiba.batista

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

class RoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey() = DataSourceContextHolder.get()
}

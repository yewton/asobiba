package net.yewton.asobiba.batista

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jmx.export.MBeanExporter

// https://docs.spring.io/spring-boot/docs/3.0.2/reference/html/howto.html#howto.data-access.configure-two-datasources
@Configuration(proxyBeanMethods = false)
class DataSourcesConfig(val mBeanExporter: ObjectProvider<MBeanExporter>) {

    companion object {
        const val FIRST_DATASOURCE_NAME = "firstDataSource"
        const val SECOND_DATASOURCE_NAME = "secondDataSource"
    }

    @Bean
    @ConfigurationProperties("app.datasource.first")
    @FirstDataSource
    fun firstDataSourceProperties() = DataSourceProperties()

    @Bean(FIRST_DATASOURCE_NAME)
    @ConfigurationProperties("app.datasource.first.configuration")
    @FirstDataSource
    fun firstDataSource(@FirstDataSource firstDataSourceProperties: DataSourceProperties): HikariDataSource =
        firstDataSourceProperties.initializeDataSourceBuilder()
            .type(HikariDataSource::class.java).build()
            .also { enableMbeansRegistration(it, FIRST_DATASOURCE_NAME) }

    @Bean
    @ConfigurationProperties("app.datasource.second")
    @SecondDataSource
    fun secondDataSourceProperties() = DataSourceProperties()

    @Bean(SECOND_DATASOURCE_NAME)
    @ConfigurationProperties("app.datasource.second.configuration")
    @SecondDataSource
    fun secondDataSource(@SecondDataSource dataSourceProperties: DataSourceProperties): HikariDataSource =
        dataSourceProperties.initializeDataSourceBuilder()
            .type(HikariDataSource::class.java).build()
            .also { enableMbeansRegistration(it, SECOND_DATASOURCE_NAME) }

    // https://github.com/spring-projects/spring-boot/blob/v3.0.2/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jdbc/DataSourceJmxConfiguration.java#L51-L74
    fun enableMbeansRegistration(dataSource: HikariDataSource, beanName: String) {
        dataSource.isRegisterMbeans = true
        mBeanExporter.ifUnique { it.addExcludedBean(beanName) }
    }
}

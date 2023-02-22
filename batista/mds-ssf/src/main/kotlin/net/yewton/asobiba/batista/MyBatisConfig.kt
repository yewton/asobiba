package net.yewton.asobiba.batista

import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

@Configuration
class MyBatisConfig {
    @Bean
    @FirstDataSource
    fun firstSqlSessionFactory(@FirstDataSource dataSource: DataSource) = SqlSessionFactoryBean().apply {
        setDataSource(dataSource)
        setTypeAliasesPackage("net.yewton.asobiba.batista.model")
    }.`object`!!

    @Bean
    fun firstSqlSessionTemplate(@FirstDataSource sqlSessionFactory: SqlSessionFactory) =
        SqlSessionTemplate(sqlSessionFactory)

    @Bean(FirstTransactional.NAME)
    fun firstTransactionManager(@FirstDataSource dataSource: DataSource) =
        DataSourceTransactionManager(dataSource)

    @Bean
    @SecondDataSource
    fun secondSqlSessionFactory(@SecondDataSource dataSource: DataSource) = SqlSessionFactoryBean().apply {
        setDataSource(dataSource)
        setTypeAliasesPackage("net.yewton.asobiba.batista.model")
    }.`object`!!

    @Bean
    fun secondSqlSessionTemplate(@SecondDataSource sqlSessionFactory: SqlSessionFactory) =
        SqlSessionTemplate(sqlSessionFactory)

    @Bean(SecondTransactional.NAME)
    fun secondTransactionManager(@SecondDataSource dataSource: DataSource) =
        DataSourceTransactionManager(dataSource)
}

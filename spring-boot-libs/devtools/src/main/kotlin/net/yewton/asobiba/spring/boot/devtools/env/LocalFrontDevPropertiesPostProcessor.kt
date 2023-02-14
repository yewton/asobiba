package net.yewton.asobiba.spring.boot.devtools.env

import net.yewton.asobiba.spring.boot.devtools.buildinfo.BuildInfo
import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import org.springframework.util.StringUtils

@Order(Ordered.LOWEST_PRECEDENCE)
class LocalFrontDevPropertiesPostProcessor : EnvironmentPostProcessor {

    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {
        val buildInfo = BuildInfo.sharedInstance
        environment.propertySources.addLast(
            MapPropertySource(
                "local front dev",
                mapOf(
                    "spring.devtools.restart.additional-paths" to buildInfo.resourcesProp,
                    // デフォルト TemplateResolver の優先度を下げる
                    // https://github.com/spring-projects/spring-boot/blob/v3.0.2/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/thymeleaf/ThymeleafAutoConfiguration.java#L108-L125
                    "spring.thymeleaf.template-resolver-order" to "999",
                    "spring.web.resources.static-locations" to
                        webProp(buildInfo.resources)
                )
            )
        )
    }

    private fun webProp(resourceDirs: List<String>) = StringUtils.collectionToCommaDelimitedString(
        listOf("static", "public")
            .flatMap { suffix -> resourceDirs.map { "file:$it/$suffix" } } +
            // https://github.com/spring-projects/spring-boot/blob/v3.0.2/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/WebProperties.java#L88
            listOf("/META-INF/resources/", "/resources/", "/static/", "/public/")
                .map { "classpath:$it" }
    )
}

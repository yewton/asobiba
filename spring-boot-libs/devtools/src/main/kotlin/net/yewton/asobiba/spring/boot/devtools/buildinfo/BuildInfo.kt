package net.yewton.asobiba.spring.boot.devtools.buildinfo

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertiesLoaderUtils
import org.springframework.util.StringUtils
import java.nio.charset.StandardCharsets

class BuildInfo {
    // https://github.com/spring-projects/spring-boot/blob/v3.0.2/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/info/ProjectInfoProperties.java#L55
    private val buildInfoResource: Resource = ClassPathResource("META-INF/build-info.properties")
    val resourcesProp: String
    val resources: List<String>

    init {
        if (!buildInfoResource.exists()) {
            resourcesProp = ""
            resources = emptyList()
        } else {
            // https://github.com/spring-projects/spring-boot/blob/v3.0.2/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/info/ProjectInfoAutoConfiguration.java#L88
            val props = PropertiesLoaderUtils.loadProperties(EncodedResource(buildInfoResource, StandardCharsets.UTF_8))
            resourcesProp = props.getProperty("build.project.resources") ?: ""
            resources = StringUtils.commaDelimitedListToStringArray(resourcesProp).toList()
        }
    }

    companion object {
        val sharedInstance by lazy { BuildInfo() }
    }
}

package net.yewton.asobiba.spring.boot.devtools.thymeleaf

import net.yewton.asobiba.spring.boot.devtools.buildinfo.BuildInfo
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.FileTemplateResolver

// 参考 https://github.com/spring-projects/spring-boot/blob/v3.0.2/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/thymeleaf/ThymeleafAutoConfiguration.java
@AutoConfiguration
@ConditionalOnClass(TemplateMode::class, SpringTemplateEngine::class)
class LocalFrontDevThymeleafAutoConfiguration {

    /**
     * テンプレートディレクトリを直接参照する TemplateResolver を Bean 定義に登録します。
     */
    @Bean
    fun additionalTemplateResolverRegistryProcessor() = object : BeanDefinitionRegistryPostProcessor {
        override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
            // no op
        }

        override fun postProcessBeanDefinitionRegistry(registry: BeanDefinitionRegistry) {
            val resourceDirs = BuildInfo.sharedInstance.resources
            val beanNameSuffixOf: (String) -> String = if (1 < resourceDirs.size) {
                // https://blog.jdriven.com/2022/12/kotlin-kandy-find-common-prefix-or-suffix-in-strings/
                val commonPrefix = resourceDirs.zipWithNext()
                    .map { (a, b) -> a.commonPrefixWith(b) }
                    .minBy { it.length }
                ({ it.removePrefix(commonPrefix).split("/").first().replaceFirstChar(Char::uppercase) })
            } else {
                ({ _ -> "Main" })
            }
            resourceDirs.forEachIndexed { index, resourceDir ->
                val nameSuffix = beanNameSuffixOf(resourceDir)
                val bd = BeanDefinitionBuilder.genericBeanDefinition(
                    FileTemplateResolver::class.java
                ) {
                    FileTemplateResolver().apply {
                        prefix = "$resourceDir/templates/"
                        suffix = ".html"
                        templateMode = TemplateMode.HTML
                        characterEncoding = "UTF-8"
                        order = index
                        isCacheable = false
                        checkExistence = true
                    }
                }.beanDefinition
                registry.registerBeanDefinition("fileTemplateResolver$nameSuffix", bd)
            }
        }
    }
}

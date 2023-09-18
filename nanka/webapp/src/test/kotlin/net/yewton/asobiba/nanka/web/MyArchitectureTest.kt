package net.yewton.asobiba.nanka.web

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller

@Suppress("NonAsciiCharacters")
@AnalyzeClasses(packagesOf = [MyArchitectureTest::class])
class MyArchitectureTest {

    @ArchTest
    fun `@Controller のクラス名`(importedClasses: JavaClasses) =
        ArchRuleDefinition.classes().that().areAnnotatedWith(Controller::class.java)
            .should().haveSimpleNameEndingWith("Controller")
            .run { check(importedClasses) }

    @ArchTest
    fun `@Configuration のクラス名`(importedClasses: JavaClasses) =
        ArchRuleDefinition.classes().that().areAnnotatedWith(
            Configuration::class.java
        )
            .should().haveSimpleNameEndingWith("Config")
            .run { check(importedClasses) }
}

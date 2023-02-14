plugins {
    id("net.yewton.asobiba.kotlin-spring-boot-library")
    id("org.springframework.boot")
    id("java")
}

springBoot {
    buildInfo {
        // ビルド情報をアプリケーションから使えるようにするため、
        // buildInfo に欲しい情報を書き込むようにする。
        // ( 基本的にローカルでの開発用途 )
        // https://docs.spring.io/spring-boot/docs/3.0.2/gradle-plugin/reference/htmlsingle/#integrating-with-actuator.build-info
        excludes.set(setOf("time"))
        properties {
            additional.set(mapOf(
                    "project.dir" to projectDir,
                    "project.resources" to sourceSets.filterNot { it.name.contains("test") }
                            .flatMap { it.resources.srcDirs.map { dir -> dir.path } }
                            .distinct()
                            .joinToString(",")
            ))
        }
    }
}

tasks.bootJar {
    launchScript()
    exclude("application-local.yml", "/secrets")
}

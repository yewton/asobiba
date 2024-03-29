group = "$group.osunaba"

plugins {
    id("net.yewton.asobiba.common")
}

dependencies {
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation("org.openjdk.jmh:jmh-core:1.37")
    testAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
}

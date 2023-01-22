plugins {
    id("java-platform")
}

group = "net.yewton.asobiba.platform"

dependencies {
    constraints {
        api(libs.detekt.formatting)
    }
}

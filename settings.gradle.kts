// This is an empty umbrella build including all the component builds.
// This build is not necessarily needed. The component builds work independently.

includeBuild("platforms")
includeBuild("build-logic-lint")
includeBuild("build-logic")

includeBuild("osunaba")
includeBuild("example-app")
includeBuild("nanka")
includeBuild("spring-boot-libs")
includeBuild("batista")
includeBuild("aggregation")
includeBuild("btrace")

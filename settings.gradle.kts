pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Tinkoff_school"
include(":app")

include(":core")
include(":core:data")
include(":core:feature")
include(":core:data:network")
include(":core:data:storage")

include(":feature")
include(":feature:films")


include(":data:storage")
include(":data:network")
include(":data")
include(":data:films")
include(":data:films:network")
include(":data:films:storage")

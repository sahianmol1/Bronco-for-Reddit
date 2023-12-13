pluginManagement {
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

rootProject.name = "Bronco for reddit"
include(":app")
include(":benchmark")
include(":baselineprofile")
include(":core:common")
include(":core:common-ui")
include(":core:design-system")
include(":home")
include(":search")
include(":subreddits")
include(":about")

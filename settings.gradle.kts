pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Bronco for Reddit"
include(":app")
include(":performance:benchmark")
include(":performance:baselineprofile")
include(":core:common")
include(":core:common-ui")
include(":core:data")
include(":core:domain")
include(":core:navigation")
include(":core:design-system")
include(":features:about:about-presentation")
include(":features:home:home-presentation")
include(":features:home:home-domain")
include(":features:home:home-data")
include(":features:search:search-presentation")
include(":features:search:search-domain")
include(":features:search:search-data")
include(":features:savedposts:saved-presentation")
include(":features:savedposts:saved-domain")
include(":features:savedposts:saved-data")
include(":features:postdetails:postdetails-presentation")
include(":features:postdetails:postdetails-domain")
include(":features:postdetails:postdetails-data")

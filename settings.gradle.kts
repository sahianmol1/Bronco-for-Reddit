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
include(":performance:benchmark")
include(":performance:baselineprofile")
include(":core:common")
include(":core:design-system")
include(":core:data")
include(":core:domain")
include(":features:about:about-presentation")
include(":features:about:about-domain")
include(":features:about:about-data")
include(":features:home:home-presentation")
include(":features:home:home-domain")
include(":features:home:home-data")
include(":features:search:search-presentation")
include(":features:search:search-domain")
include(":features:search:search-data")
include(":features:subreddits:subreddit-presentation")
include(":features:subreddits:subreddit-domain")
include(":features:subreddits:subreddit-data")
include(":core:navigation")
include(":features:home:models")

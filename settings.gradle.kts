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
include(":core:design-system")
include(":core:data")
include(":core:domain")
include(":about:about-presentation")
include(":about:about-domain")
include(":about:about-data")
include(":home:home-presentation")
include(":home:home-domain")
include(":home:home-data")
include(":search:search-presentation")
include(":search:search-domain")
include(":search:search-data")
include(":subreddits:subreddit-presentation")
include(":subreddits:subreddit-domain")
include(":subreddits:subreddit-data")
include(":core:navigation")

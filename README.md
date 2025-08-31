<div align="center">
  <a href=""><img width="200" src="https://github.com/user-attachments/assets/16a7927f-188d-4d70-bda1-09401d0cfba2"></a>
  <h1>Bronco for Reddit</h1>
</div>

<p align="center">
  <a href="https://www.android.com"><img src="https://forthebadge.com/images/badges/built-for-android.svg"></a>
  <a href="https://www.github.com/sahianmol1"><img src="https://forthebadge.com/images/badges/built-with-love.svg"/></a>
</p>

<p align = "center">
  <img src="https://github.com/sahianmol1/Bronco-for-Reddit/actions/workflows/run-tests.yml/badge.svg" />
  <img src="https://img.shields.io/badge/License-MIT-blue.svg" />
  <img src="https://img.shields.io/github/stars/sahianmol1/Bronco-for-Reddit?logo=star" />
</p>

 ## Table of Contents
 - [Introduction](#introduction)
 - [Screenshots](#Screenshots)
 - [Download](#Download)
 - [Architecture](#Architecture)
 - [Tools](#Tools)
 - [Tests](#Tests)
 - [TODOs](#TODOs)
 - [License](#license)

## Introduction
**Bronco for Reddit** is a free, open-source Reddit client for Android built to demonstarte the use of **multi-module, clean code MVVM architecture** in Android projects. It is an offline-first app built using Jetpack Compose and adheres to Material 3 guidelines and supports the dynamic colors.

🚧 Currently, the app is work in progress, and I plan to add more features in future updates. These updates will further demonstrate the scalability, testability, and flexibility of this architecture. 🚧

## Screenshots

|Working Demo|
|------------|
| <video src="https://github.com/user-attachments/assets/a9643509-9ea9-40b2-b0d7-1c8f7bb84b3c" /> |

|Light|Dark|
|---------------------|-----------------------|
| <img height="600" alt="home_screen_light" src="https://github.com/user-attachments/assets/69e217bf-16c0-47dd-8bc6-664c3020804a" /> | <img height="600" alt="home_page_n" src="https://github.com/user-attachments/assets/690c8d0c-5b0e-43e4-84d2-5125e2208acc" /> |
| <img height="600" alt="search_screen_light" src="https://github.com/user-attachments/assets/7091442c-a596-48d5-9398-a2b81833220b" /> | <img height="600" alt="search_screen" src="https://github.com/user-attachments/assets/89b170ac-f813-453b-94cd-a8fd46b538b7" /> |
| <img height="600" alt="search_variant_light" src="https://github.com/user-attachments/assets/89088f81-9f99-49f2-96c2-c4ebafea12b3" /> | <img height="600" alt="search_Screen_variant" src="https://github.com/user-attachments/assets/912fc80b-0d12-4f22-a3a8-e1d405a68560" /> |
| <img height="600" alt="post_details_light" src="https://github.com/user-attachments/assets/05f06b4b-bff2-4d1e-bcaf-e2f3d88aa624" /> | <img height="600" alt="post_details" src="https://github.com/user-attachments/assets/0a63c7c3-588b-4c60-a815-808d62130cbc" /> |

## Download

## Architecture
![Dependency graph](/docs/images/coremodulegraph/high_level_module_graph_2.png)

This is a high level multi module dependency graph. For detailed dependencies checkout [module wise dependency graph](/docs/images/graphs).

#### Modularization Strategy

This project follows modularization **by feature & layer** as described in [this video](https://youtu.be/16SwTvzDO0A?si=qHSqHXnW8jSHjitB&t=249). This approach ensures that modules are both highly cohesive and loosely coupled, while also reducing the redundant code.

This architecture leverages the [Dependency Inversion principle](https://en.wikipedia.org/wiki/Dependency_inversion_principle) (DIP). High-level modules (e.g., presentation) should not depend on low-level modules (e.g., data). Instead, both should depend on abstractions (e.g., domain). [<u>Learn more</u>](https://developer.android.com/topic/modularization/patterns#dependency_inversion)

**Delegates** are used for [module to module communication](https://developer.android.com/topic/modularization/patterns#communication). Delegates are powerful because they enable class-level runtime polymorphism, promoting the use of [composition](https://en.wikipedia.org/wiki/Composition_over_inheritance) for code reuse. Checkout [/app/delegates](app/src/main/java/com/anmolsahi/broncoforreddit/delegates).

<sup>*</sup> There are many features. Showing only 3 in the diagram for the sake of simplicity.

## Tools
- [Material3](https://developer.android.com/jetpack/androidx/releases/compose-material3) - M3 opens up new possibilities for both brand colors and individual color preferences to converge in one-of-a-kind experiences. The color system embraces the need for color to reflect an app’s design sensibility, while also honoring the settings that individuals choose for themselves. 
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Kotlin solves Asynchronous or non-blocking programming problem in a flexible way by providing coroutine support at the language level and delegating most of the functionality to libraries.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
- [Room](https://developer.android.com/jetpack/androidx/releases/room) - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
- [Ktor-client](https://ktor.io/docs/create-client.html) - Ktor is a framework to easily build connected applications
- [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) - Kotlin serialization consists of a compiler plugin, that generates visitor code for serializable classes, runtime library with core serialization API and support libraries with various serialization formats.
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) - Navigation Compose enables you to navigate between composables while taking advantage of the Navigation component’s infrastructure and features.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
- [SplashScreen](https://developer.android.com/guide/topics/ui/splash-screen) - The SplashScreen API enables a new app launch animation for all apps when running on a device with Android 12 or higher. This includes an into-app motion at launch, a splash screen showing your app icon, and a transition to your app itself.
- [Material Icons](https://developer.android.com/reference/kotlin/androidx/compose/material/icons/Icons) - Material Design system icons as seen on Google Fonts.
- [Media3 ExoPlayer](https://developer.android.com/media/media3/exoplayer) - Media3 ExoPlayer is used to play the reddit videos.

## Tests
I have added unit tests for the `:search` module. checkout the unit test here - [search module unit tests](/features/search/search-presentation/src/test/java/com/anmolsahi/presentation/ui).
<br>🚧 Unit test for the other modules are work in progress 🚧.

## TODOs
- **Code Cleanup** - While this project is great for understanding industry-standard architecture, some files still contain duplicate code and need cleanup. My plan is to refactor the code using Design Patterns and SOLID principles. Along the way, I’ll also be writing articles to demonstrate how I approached the cleanup process.
- **More Features** - I will be adding more features like Subreddit page, user profile page and login.
- **M3 Expressive** - The design will be kept up to date by adding the M3 expressive components.
- **Adaptive Design** - Make the app support multiple devices like foldables, tablets and the Android XR.
- **Kotlin Multiplatform** - The goal is to take the app multiplatform by integration the kotlin multiplatform technology.

If you find this project valuable or if you like the app, please give it a star ⭐️! Your support means a lot and motivates me to continue improving the project. 🙏

## License
```
MIT License

Copyright (c) 2024 Anmol Singh Sahi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

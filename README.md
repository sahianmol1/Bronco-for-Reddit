# Bronco for Reddit

**Bronco for Reddit** (MVP) is a free, open-source Reddit client for Android that showcases the use of a **multi-module, clean code MVVM architecture**. It is an offline-first app built using Jetpack Compose and adheres to Material 3 guidelines.

My motivation for developing this project was to gain hands-on experience with multi-module architecture in Android. Many tutorials and sample apps I found were either too simplistic (lacking real-world complexity) or overly complicated. Bronco for Reddit strikes a balance, making it an ideal project for learning about clean code architecture.

🚧 Currently, the app is in its MVP stage, and I plan to add more features in future updates. These updates will further demonstrate the scalability, testability, and flexibility of this architecture. 🚧

## Architecture
![Dependency graph](/docs/images/coremodulegraph/high_level_module_graph_2.png)

This is a high level multi module dependency graph. For detailed dependencies checkout [module wise dependency graph](/docs/images/graphs).

### Modularization Strategy

This project follows modularization **by feature & layer** as described in [this video](https://youtu.be/16SwTvzDO0A?si=qHSqHXnW8jSHjitB&t=249). This approach ensures that modules are both highly cohesive and loosely coupled, while also reducing the redundant code.

This architecture leverages the [Dependency Inversion principle](https://en.wikipedia.org/wiki/Dependency_inversion_principle) (DIP). High-level modules (e.g., presentation) should not depend on low-level modules (e.g., data). Instead, both should depend on abstractions (e.g., domain). [<u>Learn more</u>](https://developer.android.com/topic/modularization/patterns#dependency_inversion)

**Delegates** are used for [module to module communication](https://developer.android.com/topic/modularization/patterns#communication). Delegates are powerful because they enable class-level runtime polymorphism, promoting the use of [composition](https://en.wikipedia.org/wiki/Composition_over_inheritance) for code reuse. Checkout [/app/delegates](app/src/main/java/com/anmolsahi/broncoforreddit/delegates).

<sup>*</sup> There are many features. Currently showing only 3 for the sake of simplicity.

## ✅ Unit tests
I have added unit tests for the `:search` module. checkout the unit test here.
<br>Unit test for the other modules are work in progress 🚧.

If you find this project valuable or if you like the app, please give it a star ⭐️! Your support means a lot and motivates me to continue improving the project. 🙏

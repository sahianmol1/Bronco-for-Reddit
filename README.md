# Bronco for Reddit

**Bronco for Reddit** is a free, open-source Reddit client for Android that showcases the use of a **multi-module, clean code MVVM architecture**. It is an offline-first app built using Jetpack Compose and adheres to Material 3 guidelines.

Reddit has an undocumented feature that provides data from their servers as JSON feed. Bronco uses this public JSON feed instead of the official APIs, which makes it suitable only for accessing public data from Reddit's servers.

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
<br>🚧 Unit test for the other modules are work in progress 🚧.

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

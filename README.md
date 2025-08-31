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
 - [TODOs](#TODOs)
 - [Tests](#Tests)
 - [License](#license)

## Introduction
**Bronco for Reddit** is a free, open-source Reddit client for Android built to demonstarte the use of **multi-module, clean code MVVM architecture** in Android projects. It is an offline-first app built using Jetpack Compose and adheres to Material 3 guidelines.

🚧 Currently, the app is in its MVP stage, and I plan to add more features in future updates. These updates will further demonstrate the scalability, testability, and flexibility of this architecture. 🚧

## Screenshots

#### Working Demo
https://github.com/user-attachments/assets/a9643509-9ea9-40b2-b0d7-1c8f7bb84b3c

<img height="600" alt="home_page" src="https://github.com/user-attachments/assets/72207a33-5ac3-4678-8145-2d3430527d5c" /> <img height="600" alt="search_Screen_variant" src="https://github.com/user-attachments/assets/912fc80b-0d12-4f22-a3a8-e1d405a68560" /> <img height="600" alt="post_details" src="https://github.com/user-attachments/assets/0a63c7c3-588b-4c60-a815-808d62130cbc" /> <img height="600" alt="search_screen" src="https://github.com/user-attachments/assets/89b170ac-f813-453b-94cd-a8fd46b538b7" />


## Download

## Architecture
![Dependency graph](/docs/images/coremodulegraph/high_level_module_graph_2.png)

This is a high level multi module dependency graph. For detailed dependencies checkout [module wise dependency graph](/docs/images/graphs).

### Modularization Strategy

This project follows modularization **by feature & layer** as described in [this video](https://youtu.be/16SwTvzDO0A?si=qHSqHXnW8jSHjitB&t=249). This approach ensures that modules are both highly cohesive and loosely coupled, while also reducing the redundant code.

This architecture leverages the [Dependency Inversion principle](https://en.wikipedia.org/wiki/Dependency_inversion_principle) (DIP). High-level modules (e.g., presentation) should not depend on low-level modules (e.g., data). Instead, both should depend on abstractions (e.g., domain). [<u>Learn more</u>](https://developer.android.com/topic/modularization/patterns#dependency_inversion)

**Delegates** are used for [module to module communication](https://developer.android.com/topic/modularization/patterns#communication). Delegates are powerful because they enable class-level runtime polymorphism, promoting the use of [composition](https://en.wikipedia.org/wiki/Composition_over_inheritance) for code reuse. Checkout [/app/delegates](app/src/main/java/com/anmolsahi/broncoforreddit/delegates).

<sup>*</sup> There are many features. Showing only 3 in the diagram for the sake of simplicity.

## Tools

## TODOs

## ✅ Tests
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

## High level multi module architecture
![Dependency graph](/docs/images/coremodulegraph/high_level_module_graph.png)

<sup>*</sup> There are many features. Currently showing only 3 for the sake of simplicity.

This is a high level multi module dependency graph. For detailed dependencies checkout [module wise dependency graph](/docs/images/graphs).

This architecture leverages the [Dependency Inversion principle](https://en.wikipedia.org/wiki/Dependency_inversion_principle) (DIP). High-level modules (e.g., presentation) should not depend on low-level modules (e.g., data). Instead, both should depend on abstractions (e.g., domain).

Another example of DIP in this project is the use of delegates. Delegates are powerful because they enable class-level runtime polymorphism, thus promoting the use of [composition](https://en.wikipedia.org/wiki/Composition_over_inheritance) for code reuse. For example usage, checkout [/app/delegates](app/src/main/java/com/anmolsahi/broncoforreddit/delegates).

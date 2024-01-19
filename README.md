# Recipe Code Challenge: Android Project with Jetpack Compose, Kotlin, Adaptive Layouts and mockable.io API Simulation 🚀

Welcome to Recipe Code Challenge repository! This project showcases a robust recipe app developed for Android OS using the power of Kotlin and Jetpack Compose. With a focus on modern technologies and best practices, we've implemented a modular architecture and utilized Dagger Hilt for efficient dependency injection, ensuring maintainability, scalability, and testability of the codebase. 💻

## Technologies Used:

- **Kotlin 😎:** The entire project is crafted in Kotlin, offering concise, expressive, and readable codebase. 
  
- **Jetpack Compose 🎨:** The user interface is built using Jetpack Compose, a modern Android UI toolkit known for its declarative syntax and interactive UI components.


# Key Features:


- **Testing::** Tests for modules :domain:, :data:, :presentation: following AAA Pattern, and FIRST Principle. Unit test/Android instrumented tests

- **Adaptive Layout::** The app is designed to provide a seamless user experience across form factors (Compat, Medium, Expanded) and Foldable devices.
- 
- **Modular Architecture:** I organized the project into well-defined modules, by feature, enhancing clean and maintainable structure

**Modularization by layer**:

:app:
:core:common:

:recipe:data:
:recipe:domain:
:recipe:presentation:

I also planned to have a module called :core:navigation: that will have the responsibility for managing navigation logic/navigation graph avoiding handling navigation logic on the module app but i don't implement yet ;c

- **Dependency Injection:** Dagger Hilt is implemented for dependency injection, ensuring loose coupling between components, making the codebase more modular and testable.

- **Repository Pattern:** Data access is abstracted through the repository pattern, promoting separation of concerns and making it easy to switch between data sources.

- **Triple A (AAA) Pattern:** Follows the principles of Arrange, Act, and Assert in testing, ensuring clear and effective unit tests for each feature.

- **FIRST Principle:** Tests adhere to the FIRST principle (Fast, Isolated, Repeatable, Self-Validating, Timely), promoting a robust and reliable testing suite.

- **Kotlin DSL + Version Catalog::** Utilizes Kotlin DSL for build scripts and version catalogs for dependency management, ensuring consistency and ease of maintenance.

**API Simulation 🌐:**

To simulate REST API services , we are utilizing **mockable.io**, a service that mimics API endpoint. This enables realistic testing and demonstration of API interactions within the Android application. 

# Presentation Layer Architecture 🎨:

- **Single ViewState Pattern:** Represent the state of the screen at any given moment. This pattern ensures a unidirectional flow of data, simplifying state management and making it predictable. Each screen has a corresponding ScreenState.kt that encapsulates its state.
  
- **Key Components:**
1. *ScreenState.kt*
   Represents the state of the screen at any given moment.
   Holds the necessary information required for rendering the UI.
2. *Screen.kt*
   Utilizes Jetpack Compose to render the UI based on the current ScreenState.
   Embraces a declarative approach to UI development.
3. *ScreenEvent.kt*
   Represents events originating from the UI.
   Passed to ViewModels to handle user interactions.
4. *ScreenAction.kt*
   Represents actions originating from the ViewModel.
   Typically used for navigation or other one-time UI actions.
5. *ViewModel.kt*
   Produces and manages the ScreenState to be consumed by the corresponding Screen.kt.
   Responds to ScreenEvent entries, updating the state accordingly.
   Utilizes StateFlow to store the current state reactively.

- **Testing**:
  - UI Testing, ViewModel Testing, also adaptive layout testing 🎉

# Domain Layer 🧠:

- Have repository contract, entities, and use-cases (all test's passed ✅)

# Data Layer 📊

- Have remote implementation of repository contract, and test with mock-web-server simulating 200, 500 responses with .json mocks

**How to Use:**

1. Open the project in Android Studio or your preferred IDE.

2. Build and run the application on an resizable emulator choose between  phone, tablet, and foldable devices to experience adaptive render.

# Screenshots:

- Inside /screenshot/ folder

# News App


### Using Technologies ###

* Kotlin
* Dagger Hilt for Dpendendency Injection
* Junit
* Kotlin coroutines
* MVVM
* Glide
* TDD (Unit test)
* Repository pattern 
* Jetpack Navigation component
* Kotlin DSL (Gradle files)
* Retrofit 
* Gson
* ViewBinding
* Clean Architecture
* SOLID Principles 

### Demonstration of Base, Repository layer, with some Custom widgets OR components ###

## Project contains three basic layers of Clean Architecture ##
* Data Layer
* Domain Layer
* Presentation Layer

Data Layer contains remote and local data repositories. In this project there is no Local scope that's why only remote repository exist.

Domain layer contains Data Repository which handle the logic get the data from local or repository according to certain business rules. In this project no loacal repository involved so it handles only remote data. Here you can add Domain DTO's and mappers. But the current scope I don’t feel this to add separate domain DTO's to just mainatin the layer that's Why i'm using only initial DTO's coming from Data layer. 

Basically in Domain Layer we Add USE CASES according to Clean Architecture Documentation but I'm using Repository pattern so that's why I don’t feel to introduce USE CASES in Domain Layer. 

Presentation layer contains the UI and ViewModels. 

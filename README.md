# eden
In this project, an Android JAVA application is developed using the MVVM (Model-View-ViewModel) pattern to manage vegetable garden cultivation. The app supports crop management as well as exchanges between users for sharing excess harvest.

## Project structure 

### Contents

```
┣ 📂 eden                    # Root directory.
┣ ┣ 📄 gitlab-ci.yml            # CI/CD configuration
┣ ┣ 📄 build.gradle             # Gradle file at the project level
┣ 📄 README.md                  # Project README file
┣ 📂 gradle/wrapper                             
┣ 📂 app
┣ ┃ ┣ 📄 build.gradle           # build.gradle file for the app module
┣ ┃ ┣ 📄 google-services.json   # Firebase dependencies 
┣ ┃ ┣ 📂 javadoc                # Javadoc documentation
┣ ┃ ┣ 📂 src\                         
┣ ┃ ┃ ┣ 📂 androidTest\java\com\unimib\eden\  # Instrumented tests
┣ ┃ ┃ ┣ 📂 test\java\com\unimib\eden\         # Unit tests
┣ ┃ ┃ ┣ 📂 main  
┣ ┃ ┃ ┃ ┣ 📂 java\com\unimib\eden\            # Implemented classes
┣ ┃ ┃ ┃ ┣ 📂 res                              # XML resources for styles, themes, layouts, etc.
┣ ┃ ┃ ┃ ┣ 📄 manifest.xml                     # System information manifest file
```

### Folder structure of implemented classes 

```
┣ 📂 adapter                  # [UI] Classes for list views
┣ ┣ 📄 CropAdapter.java         # RecyclerView for crops
┣ ┣ 📄 ForecastDayAdapter.java  # For displaying weather banners
┣ ┣ 📄 PhaseAdapter.java        # RecyclerView for phases
┣ ┣ 📄 PlantAdapter.java        # RecyclerView for plants
┣ ┣ 📄 ProductAdapter.java      # RecyclerView for products
┣ 📂 database                 # [MODEL] Interactions with Room Firestore
┣ ┣ 📄 ...
┣ 📂 model                    # [MODEL] Classes for modeling entities
┣ ┣ 📄 Crop.java                # Crop modeling
┣ ┣ 📄 FirebaseResponse.java    # Database response modeling
┣ ┣ 📄 Offer.java               # Modeling offers [features]
┣ ┣ 📄 Phase.java               # Modeling growth phases
┣ ┣ 📄 Plant.java               # Modeling plants
┣ ┣ 📄 Product.java             # Modeling products
┣ ┣ 📄 User.java                # Modeling registered users
┣ ┣ 📂 weather                  # Modeling API response for weather
┣ ┣ 📄 ...
┣ 📂 repository              # [MODEL] Classes for database interactions
┣ 📂 service                 
┣ ┣ 📄 WeatherService.java   # Class for direct interaction with the weather API
┣ 📂 ui                      # [UI] Java classes for UI (and ViewModel)
┣ ┣ 📂 Authentication           # Classes for login and logout
┣ ┃ ┣ 📄 AuthenticationActivity.java  # User authentication management
┣ ┃ ┣ 📄 EntryActivity.java           # Class for user session check
┣ ┃ ┣ 📄 LoginFragment.java           # Class for user login
┣ ┃ ┣ 📄 RegisterFragment.java        # Class for user registration
┣ ┃ ┣ 📄 UserViewModel.java           # [VIEWMODEL] Class for user operations interaction
┣ ┣ 📂 cropDetails              
┣ ┃ ┣ 📄 CropDetailsActivity.java      # Class for crop details
┣ ┃ ┣ 📄 CropDetailsViewModel.java     # ViewModel for crop details
┣ ┣ 📂 filterSearch             
┣ ┃ ┣ 📄 FilterSearchActivity.java     # Class for searching available plants
┣ ┃ ┣ 📄 FilterSearchViewModel.java    # Corresponding ViewModel
┣ ┣ 📂 home                     
┣ ┃ ┣ 📄 HomeFragment.java             # Initial fragment in MainActivity with garden view
┣ ┃ ┣ 📄 HomeViewModel.java            # Corresponding ViewModel
┣ ┣ 📂 insertCrop               
┣ ┃ ┣ 📄 InsertCropActivity.java       # Activity for adding a crop to the garden
┣ ┃ ┣ 📄 InsertCropViewModel.java      # Corresponding ViewModel
┣ ┣ 📂 insertProduct
┣ ┃ ┣ 📄 InsertProductActivity.java    # Activity for adding a product
┣ ┃ ┣ 📄 InsertProductViewModel.java   # Corresponding ViewModel
┣ ┣ 📂 main
┣ ┃ ┣ 📄 MainActivity.java             # Main app activity
┣ ┃ ┣ 📄 WeatherViewModel.java         # ViewModel for getting weather history
┣ ┣ 📂 plantDetails
┣ ┃ ┣ 📄 PlantDetailsActivity.java     # Activity for plant details
┣ ┃ ┣ 📄 PlantDetailsViewModel.java    # Corresponding ViewModel
┣ ┣ 📂 productDetails
┣ ┃ ┣ 📄 ProductDetailsActivity.java   # Activity for product details
┣ ┃ ┣ 📄 ProductDetailsViewModel.java  # Corresponding ViewModel
┣ ┣ 📂 searchPlant
┣ ┃ ┣ 📄 SearchPlantActivity.java      # Activity for searching plants
┣ ┃ ┣ 📄 SearchPlantViewModel.java     # Corresponding ViewModel
┣ ┣ 📂 stand
┣ ┃ ┣ 📄 StandFragment.java            # Fragment in MainActivity displaying products
┣ ┃ ┣ 📄 StandViewModel.java           # Corresponding ViewModel
┣ 📂 watering
┣ ┃ ┣ 📄 WateringFragment.java         # Fragment in MainActivity for viewing plants to be watered
┣ ┃ ┣ 📄 WateringViewModel.java        # ViewModel with getForecast for weather
```

## Architettura MVVM

Our project uses the MVVM (Model-View-ViewModel) architecture to ensure a clear separation of responsibilities and efficient management of data and user interface.

### Componenti dell'architettura MVVM:

- **Model**: This layer is responsible for data abstraction and the business logic of the application. It manages data and provides an interface for data access and manipulation. Packages that are part of this layer include the adapter, repository, database, and model packages.
- **View**: This layer represents the user interface of the application and is responsible for displaying data to the user and handling user interactions. It includes all the Fragment/Activity classes found within the sub-packages of the UI package, along with the corresponding .XML files located in the res folder.
- **ViewModel**: This layer serves as a bridge between the Model and the View. The ViewModels associated with the Fragments/Activities retrieve data from the Model and make it available to the View in a user-friendly format. Additionally, it updates the Model based on user interactions with the View. In this case, the ViewModels are contained within the same sub-packages as the Fragment/Activity to which they are associated.

Using the MVVM architecture promotes greater modularity, easier testing and code maintenance, and a better separation of concerns. This approach is particularly well-suited for applications with complex user interfaces.

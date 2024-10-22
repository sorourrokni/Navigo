# Routing and Map Service App

This repository contains a digital map and routing service application built using **Jetpack Compose** for Android, developed in **Kotlin**. The app offers both **single-destination** and **multi-destination** routing services, utilizing various **Neshan API** features. It provides users with an enhanced experience for urban and intercity navigation by displaying optimal routes considering real-time traffic conditions.

## Features

1. **Access to Maps and User Location Display**  
   Users can view their current location on the map in real time.
   
2. **Location Updates**  
   The app regularly updates the user's location at specific intervals.

3. **Long Press to Add Markers**  
   Users can long-press on the map to add a marker.

4. **Location Information and Route Optimization**  
   Retrieve location data from the map and generate optimized routes to destinations.

5. **Single and Multi-destination Routing**  
   The app supports both single and multi-destination route planning.

6. **Search by Text and Voice**  
   Users can search for destinations manually via text or using voice commands.

7. **Search Results Based on Proximity**  
   Search results are displayed based on their proximity to the user’s current location.

8. **Route Display on Map with Traffic Consideration**  
   The app displays the optimal route on the map, taking traffic conditions into account.

## Project Architecture

The project is designed with the **MVVM (Model-View-ViewModel)** architecture, ensuring separation of concerns and better code maintainability. This architecture allows for better management of data and UI updates, reducing unnecessary data reloading and improving performance.

### Layers in MVVM:

- **Model:**  
  Manages the data and business logic. It processes and stores the data, which can come from local databases, web services, or external sources.

- **View:**  
  Represents the user interface. It displays user interactions and handles input, including buttons, forms, and maps.

- **ViewModel:**  
  Acts as a mediator between the Model and the View. It retrieves data from the Model and converts it into a format suitable for display in the View, maintaining the app’s state independent of the UI.

### Folder Structure

- **activity:**  
  Contains the main activities of the application, such as `MainActivity`, which serves as the entry point and manages the overall UI.

- **component:**  
  Contains reusable UI components like buttons, forms, and other widgets.

- **data/model:**  
  Defines the data classes used in the application.

- **network:**  
  Contains the network-related classes responsible for communicating with the Neshan API and other web services.

- **repository:**  
  Manages data handling, acting as an intermediary between the ViewModel and different data sources (local or remote).

- **navigation:**  
  Manages the app's navigation and transitions between different screens.

- **theme:**  
  Contains the design resources such as colors, fonts, and other style elements that create a consistent look for the app.

- **ui:**  
  Defines the screens and layouts that structure how the information is displayed.

- **viewModel:**  
  Includes classes that manage the data required by the UI and handle communication with the Model layer.

## APIs Used

The app integrates with **Neshan's APIs**, which offer features such as:

- Car and motorcycle routing
- Street name search
- Reverse geocoding (address to location and vice versa)
- Route mapping
- Distance matrix services

## Installation

To run this project, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/routing-map-app.git
   
2. Open the project in Android Studio.
3. Build the project and run it on an emulator or Android device.

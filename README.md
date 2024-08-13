# Navigo - Your Ultimate GPS Navigation App

Navigo is a modern GPS navigation app designed to help you find the best routes, avoid traffic jams, and cut commute times efficiently. Built with Android Jetpack Compose, Navigo offers a sleek and responsive user experience, making it an essential tool for both travelers and city inhabitants.

## Features

- **Real-Time Traffic Updates:** Stay informed with live traffic conditions to avoid congestion.
- **Multiple Route Options:** Choose from the fastest, shortest, or most scenic routes.
- **Turn-by-Turn Navigation:** Get step-by-step instructions with voice guidance.
- **Search and Navigate:** Quickly find destinations and start navigating with ease.
- **Community Reporting:** Report and view incidents such as accidents and road closures.
- **Offline Maps:** Download maps for offline use to navigate without an internet connection.

## Installation

To run Navigo on your local machine, follow these steps:

1. **Clone the Repository:**
    ```bash
    git clone https://github.com/sorourrokni/Navigo.git
    cd navigo
    ```

2. **Open the Project in Android Studio:**
    - Ensure you have the latest version of Android Studio installed.
    - Open the project from the cloned repository directory.

3. **Set Up Dependencies:**
    - Sync the project with Gradle to download necessary dependencies.

4. **Run the App:**
    - Connect an Android device or use an emulator.
    - Click on the "Run" button in Android Studio.

## Technologies Used

- **Android Jetpack Compose:** For building a responsive and modern UI.
- **Kotlin:** As the primary programming language.
- **Google Maps SDK:** For map functionalities and real-time traffic data.
- **Firebase Authentication:** For user account management.

## Project Structure

- **MainActivity.kt:** Entry point of the app.
- **ui/**: Contains all UI components and screens.
  - **HomeScreen.kt:** The home screen with map view and search bar.
  - **SearchScreen.kt:** Search functionality and displaying search results.
  - **NavigationScreen.kt:** Displaying routes and navigation instructions.
- **data/**: Data models and repository classes.
- **network/**: API service classes for fetching real-time traffic data.
- **utils/**: Utility classes and extension functions.

## Contributing

We welcome contributions to improve Navify. To contribute:

1. **Fork the Repository:**
    - Click on the "Fork" button on the repository's GitHub page.

2. **Create a Branch:**
    ```bash
    git checkout -b feature/your-feature-name
    ```

3. **Make Changes and Commit:**
    - Implement your feature or bug fix.
    - Commit your changes with a descriptive message.

4. **Push to Your Fork:**
    ```bash
    git push origin feature/your-feature-name
    ```

5. **Create a Pull Request:**
    - Go to the original repository and create a pull request from your forked branch.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contact

For any questions or feedback, please contact us at support@navigoapp.com.

---

We hope Navigo enhances your travel and daily commutes by providing accurate and efficient navigation. Happy driving!

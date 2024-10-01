# Sakina - Android App

**Sakina** is a health and wellness Android app designed to help users manage their medical conditions and seek peace of mind by diagnosing their symptoms through AI models. It also provides a medicine management system and integrates multiple health-related AI features.

## Features

- **User Authentication:** Secure login, registration, and account management system.
- **AI-Powered Chatbot:** Provides symptom-based health advice using generative AI models.
- **Heart Sound Diagnosis:** Allows users to record their heartbeats and receive a potential diagnosis.
- **Skin Condition Diagnosis:** Lets users upload photos of their skin to detect any anomalies.
- **Medicine Tracker:** Users can log their medications, set reminders, and track dosages.
- **Language Support:** Fully supports both Arabic and English interfaces.
- **Onboarding:** Guides users through the app’s key features and usage on first-time login.

---

## Project Structure

The Sakina app follows **Clean Architecture** with a feature-based modular approach, ensuring separation of concerns and ease of maintenance.

### Core Packages

- **Main API**: Handles the communication between the app and the backend server.
- **AI Models API**: Manages the connection and data handling with the AI models for diagnosis.

### Feature Packages

1. **Domain Layer**:  
   - `Model`: Contains the core data models for each feature.
   - `Repository`: Interface for the data repository that the app uses for data storage and retrieval.
   - `UseCases`: Business logic of the application (e.g., adding medical information, managing prescriptions).

2. **Data Layer**:  
   - `Local`: Manages local data storage using **Room**.
   - `Remote`: Manages API requests using **Retrofit**.
   - `Repository`: Implements the repositories to access data from local and remote sources.

3. **Presentation Layer**:  
   - `ViewModel`: Responsible for handling data preparation and lifecycle management for the UI.
   - `UI`: Contains the Android UI components like Activities, Fragments, and XML layout files.

---

## Technologies and Libraries

- **Kotlin**: Main programming language.
- **Coroutines**: For handling asynchronous tasks and background operations.
- **Retrofit**: For API communication.
- **Room**: Local database for storing user data such as medical history and medications.
- **Navigation Component**: For managing app navigation using the navigation graph.
- **TensorFlow Lite**: For integrating AI models (heart sound and skin diagnosis).
- **Glide**: Image loading and caching library for managing photo uploads.
- **ViewModel & LiveData**: Ensures UI-related data survives configuration changes.

---

## AI Models

### 1. **Chatbot AI**
- Utilizes generative AI models to provide intelligent and conversational health advice based on user symptoms.

### 2. **Heart Sound Diagnosis**
- Users can upload their heartbeat sound recordings, which are analyzed by the AI model to provide insights into potential health conditions.

### 3. **Skin Condition Diagnosis**
- Users can take or upload photos of their skin, and the AI model analyzes the image for abnormalities.

---

## Setup and Installation

To get the app running locally, follow these steps:

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/sakina-android.git
    ```

2. **Open the project**:
    - Use **Android Studio** to open the project directory.

3. **Build and run**:
    - Sync the project with Gradle.
    - Connect an Android device or use an emulator.
    - Click "Run" to build and install the app on the device.

---

## API and Backend Setup

The app communicates with a backend server for data storage and processing. Ensure the backend API is running before testing features like authentication and medical history management.

For AI models integration, you will need the appropriate API keys and model endpoints. Make sure to add these configurations in the `remote` package of the data layer.

---

## Future Enhancements

- **Advanced Diagnosis**: Expanding AI models to include more complex diagnoses.
- **Telemedicine Integration**: Adding live doctor consultations via video or chat.
- **Wearable Device Support**: Integrating data from wearable health devices to track user health metrics.
- **Social Sharing**: Allowing users to share health updates with friends and family.
- **Offline Mode**: Enabling users to use the app in areas with limited connectivity.

---

## Contributing

Contributions are welcome! If you'd like to collaborate, please follow the guidelines below:

1. Fork the repository.
2. Create a new branch (`feature/my-new-feature`).
3. Commit your changes.
4. Push to the branch.
5. Open a pull request.

---

## License

This project is licensed under the MIT License.

---

## Contact

For any inquiries or support, feel free to contact the project maintainer at [devahmedfkri@gmail.com].

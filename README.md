# Termin — Android Booking App

Termin is a native Android application for managing appointments. It is built with Kotlin and Jetpack Compose and is designed as the foundation for a cloud-connected booking product.

## Current features

- A responsive daily appointment dashboard
- Appointment cards with time, customer, service, duration, and status
- Confirmed and pending appointment summaries
- A small, reusable Material 3 design system
- A clean separation between UI, domain models, and data access

The app currently uses demo data. The repository layer is intentionally isolated so it can be replaced with a cloud API without changing the user interface.

## Technology

- Kotlin
- Jetpack Compose and Material 3
- Android SDK 36
- Gradle 8.11.1

## Getting started

1. Open this folder in Android Studio.
2. Make sure JDK 17 and Android SDK Platform 36 are installed.
3. Select an Android emulator or a physical device.
4. Run the `app` configuration.

To build a debug APK from the command line on Windows:

```powershell
$env:ANDROID_HOME = "C:\\Users\\<your-user>\\AppData\\Local\\Android\\Sdk"
.\gradlew.bat assembleDebug
```

The generated APK is located at `app/build/outputs/apk/debug/app-debug.apk`.

## Project structure

- `app/ui` — Compose screens and the visual theme
- `app/domain` — appointment models and business concepts
- `app/data` — data sources and repository contracts

## Next steps

- Connect a cloud database and backend API
- Add authentication for service providers
- Create a public appointment booking flow for customers
- Add calendar management, notifications, and availability settings

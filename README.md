# B-Shop — Android Booking Prototype

B-Shop is a native Android booking prototype for a barbershop. The project produces two installable applications from the same codebase:

- **B-Shop Client** (`sk.termin.booking`) for selecting a day and requesting an appointment.
- **B-Shop Owner** (`sk.termin.booking.owner`) for the barbershop owner. It has its own launcher name and calendar icon.

## Current functionality

- The displayed month, day count, and leap years follow the phone calendar.
- Days before today are unavailable and are visually muted.
- A future day opens the available hourly slots from 08:00 through 19:00.
- The client can enter a name and submit a booking request.
- A submitted time is immediately marked as unavailable for the active app session.
- The owner variant uses the heading **Booked appointments**.

## Current limitation

Bookings are still stored only in the running application. Cloud synchronisation, persistent bookings, owner approval, and cross-device availability are the next implementation stage.

## Technology

- Kotlin
- Jetpack Compose and Material 3
- Android SDK 36
- Gradle 8.11.1

## Build on Windows

Set the Android SDK path first:

```powershell
$env:ANDROID_HOME = "C:\\Users\\<your-user>\\AppData\\Local\\Android\\Sdk"
```

Build the client application:

```powershell
.\gradlew.bat assembleClientDebug
```

The client APK is created at `app/build/outputs/apk/client/debug/app-client-debug.apk`.

Build the owner application:

```powershell
.\gradlew.bat assembleOwnerDebug
```

The owner APK is created at `app/build/outputs/apk/owner/debug/app-owner-debug.apk`.

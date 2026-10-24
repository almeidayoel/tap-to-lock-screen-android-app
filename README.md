# Lock Screen

A small Android app that locks the device as soon as you open it. Add it to
your home screen or app shortcuts for quick access.

## How it works

Android requires device-admin access for an app to lock the screen. The first
time you open Lock Screen, Android asks you to enable that access. Once enabled,
opening the app locks the screen immediately. If permission is declined, the app
explains that device-admin access is needed.

To remove the app, first disable its device-admin access in Android Settings.
The setting is usually under **Security** or **Security & privacy**, then **Device
admin apps**; its exact location varies by Android version and device.

## Requirements

- Android 7.0 (API 24) or later
- Android Studio with the Android SDK and a compatible JDK

## Build

Clone the repository and open it in Android Studio, or build from a terminal
with the Gradle wrapper:

```shell
./gradlew assembleDebug
```

On Windows:

```powershell
.\gradlew.bat assembleDebug
```

The debug APK is created at
`app/build/outputs/apk/debug/app-debug.apk`. Install it on a connected device
with Android Studio, or use:

```shell
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Tests

Run the local unit tests with:

```shell
./gradlew test
```

Run instrumented tests on a connected device or emulator with:

```shell
./gradlew connectedAndroidTest
```

## Project structure

- `app/src/main/java/` - app activity and device-admin receiver
- `app/src/main/res/` - launcher icons, strings, themes, and device-admin
  configuration
- `app/src/test/` - local JVM tests
- `app/src/androidTest/` - Android instrumented tests
- `gradle/` - Gradle wrapper and dependency version catalog

## Configuration

The app uses application ID `com.example.tap_to_lock_screen`, has a minimum SDK
of 24, and targets SDK 37. Gradle's `local.properties` is machine-specific and
is intentionally not tracked; Android Studio or the local Android SDK setup
provides it.

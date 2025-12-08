# ANTIGRAV (CyberShield)

Production-ready Android app skeleton with Kotlin, MVVM, Firebase Phone Auth, and placeholders for Awareness, Alerts, AI Monitor, and Settings screens.

## Project Structure
- `app/` Android application module (only module kept)
- `app/src/main/java/com/cybershield` Kotlin sources (MVVM)
- `app/src/main/res` XML layouts, themes, strings
- `app/google-services.json` Firebase config placeholder

## Prerequisites
- Android Studio Giraffe/Koala or newer
- JDK 17
- Android SDK 34 (compileSdk)
- Firebase project with Phone Auth enabled

## Setup
1. Clone the repo:
   ```bash
   git clone <your-repo-url> cybershield
   cd cybershield
   ```
2. Open in Android Studio, let it sync Gradle.
3. Replace `app/google-services.json` with the real file from Firebase console.
4. In Firebase console enable **Phone Authentication** and add your SHA-1/SHa-256 fingerprints.
5. Optional: Set up test phone numbers in Firebase Authentication for rapid QA.

## Firebase Integration Steps
1. Create Firebase project → Add Android app → package `com.cybershield`.
2. Download `google-services.json` and place it under `app/` (overwrite placeholder).
3. Add SHA-1/256 from Gradle signing report (`./gradlew signingReport`) or Android Studio.
4. Enable Phone Auth and, if needed, set test numbers.
5. Run the app; OTP flow: Login → OTP → Dashboard.

## Modules & Navigation
- `LoginActivity` → `OTPActivity` → `DashboardActivity`
- Dashboard launches `AwarenessActivity`, `AlertsActivity`, `MonitorActivity`, `SettingsActivity` via card buttons.

## Future ML Fraud Detection
- Extend `MonitorActivity` with API client & ViewModel to pull fraud scores.
- Add Retrofit/OkHttp + Kotlinx serialization for REST integration.
- Use WorkManager for periodic checks, show alerts in `AlertsActivity`.

## Contribution Guide
- Branch from `main` → feature branches (`feat/`, `fix/`).
- Keep Kotlin, MVVM, AndroidX, Material Components.
- Add/update tests when touching logic.
- Use pull requests with concise description and screenshots for UI changes.

## Build & Run
```bash
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```
Or simply **Run** from Android Studio.

## Git Remote & Push
```bash
git init
git remote add origin <git@github.com:youruser/antigrav-cybershield.git>
git add .
git commit -m "feat: initial android app skeleton"
git push -u origin main
```



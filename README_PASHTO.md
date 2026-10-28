# Simple Life — Android Studio Project

Simple Life is a small everyday-utility Android app. Its main data is stored locally on the phone, and the app does not need internet for tasks, notes, expenses, language, or theme settings.

## Features in this source
- Pashto, Dari/Persian, and English interface with RTL for Pashto/Persian and LTR for English.
- Saved light/dark preference.
- Tasks: add, complete/incomplete, edit, delete.
- Notes: add, edit, delete, search, and Android share sheet.
- Expenses: add, edit, delete, AFN totals, category and date fields.
- Creator profile, profile image, email action, phone dial actions, and share action.
- Core records/preferences use localStorage inside a local Android WebView asset. No remote website is required for core screens.
- Android intents are only opened after the user taps an action. Calls use the dialer, not direct calling.

## Project structure
- `app/src/main/java/com/samim/simplelife/MainActivity.java` — Android Activity, local WebView, and Android intent bridge.
- `app/src/main/assets/index.html` — local UI, translations, and app logic.
- `app/src/main/assets/profile.png` and `app/src/main/res/drawable/creator_profile.png` — creator photo assets.
- `.github/workflows/build-apk.yml` — cloud build workflow.

## Build requirements
- Android Studio with Android SDK Platform 35.
- JDK 17 and Gradle 8.7 (Android Gradle Plugin 8.6.1).
- Minimum Android API: 23 (Android 6.0); target API: 35.

## Build an APK from an Android phone using GitHub Actions
1. Create a repository on GitHub in the browser or GitHub app.
2. Upload the contents of this ZIP (the `SimpleLife_Final` folder contents, not the outer folder) to the repository's root. Keep `.github/workflows/build-apk.yml` in place.
3. Open the repository's **Actions** tab and select **Build Android APK**. If asked, enable workflows, then tap **Run workflow**.
4. Wait for the workflow to finish. Open the completed run and download the `simple-life-debug-apk` artifact. Extract the downloaded artifact ZIP and install `app-debug.apk` on the phone. Android may ask you to allow installation from that browser/file manager.

The workflow builds a **debug APK**, not a signed release APK. No GitHub upload has been performed by this project authoring environment.

## Validation status and limitations
- JavaScript syntax check (`node --check` on the extracted script): passed in the available environment.
- Source ZIP integrity check: passed.
- Actual Android Gradle compilation: **not run** because Android SDK and Gradle are not installed in the current environment. Therefore an APK has not been built here and compilation success is not claimed.
- Emulator/physical-device installation, UI layout, intent behavior, and persistence after force-stop/restart: **not verified here**.
- The project uses a local WebView interface rather than fully native Android screens. The Android email/dial/share actions are native intents.
- The interface is translated for its main screens, but some system-generated messages and expense category option labels remain basic; a full professional localization/QA pass still requires device testing.
- Data is stored in WebView localStorage. It is local to the app, but it is not an encrypted database and may be lost if the app's data is cleared or the app is uninstalled.
- Bluetooth/Wi-Fi Direct transfer is not implemented. No ads, analytics, or tracking SDKs are included.

## لنډه پښتو لارښود
دا د Simple Life د Android Studio سورس پروژه ده. د APK جوړولو لپاره یې GitHub ته پورته کړه، بیا **Actions → Build Android APK → Run workflow** وټاکه. د build له بریا وروسته `simple-life-debug-apk` ښکته کړه او له ZIP څخه `app-debug.apk` نصب کړه. په دې چاپېریال کې APK نه دی جوړ شوی؛ د GitHub build باید لومړی بریالی شي.

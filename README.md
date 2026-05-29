# Corporate IMAP Messenger Android Fork

This repository is the Android fork for Corporate IMAP Messenger, based on Delta Chat Android.

## Internal Android APK

Latest internal smoke build:

- [GitHub Release: Android Internal Smoke APK 0.1.1](https://github.com/Kwentin3/messenger-imap-android/releases/tag/android-internal-smoke-0.1.1)
- Direct APK download: [messenger-imap-android-foss-debug-2.50.0.apk](https://github.com/Kwentin3/messenger-imap-android/releases/download/android-internal-smoke-0.1.1/messenger-imap-android-foss-debug-2.50.0.apk)
- Recommended APK: `messenger-imap-android-foss-debug-2.50.0.apk`
- SHA-256: `FB7FA4913A4E8161472B2C2A94D68F84927538D9A92782A336E2A5346F361110`
- Status: internal debug build, runtime smoke pending
- ABI coverage: `arm64-v8a`
- Build commit: `61f9c4a8d1f6fc1de2fec8189ac4b16b996ef6a3`

This is an internal debug build for smoke testing only. It is not a production release and must not be distributed externally.

Previous release [0.1.0](https://github.com/Kwentin3/messenger-imap-android/releases/tag/android-internal-smoke-0.1.0) is broken and rejected for runtime smoke. It installs but crashes on launch on a Huawei device.

## Build Safety

APK builds require the native Delta Chat core step before Gradle:

```bash
scripts/ndk-make.sh
./gradlew assembleDebug
```

Gradle now fails APK packaging when `libs/<abi>/libnative-utils.so` is missing. This prevents publishing installable APKs that crash on launch because the native core was not built.

The current internal smoke APK was built as an `arm64-v8a` artifact:

```bash
scripts/ndk-make.sh arm64-v8a
./gradlew assembleFossDebug -PABI_FILTER=arm64-v8a
```

## Related repositories

- [Main project / documentation repo](https://github.com/Kwentin3/messenger-imap)
- [Android fork repo](https://github.com/Kwentin3/messenger-imap-android)
- [Internal smoke release](https://github.com/Kwentin3/messenger-imap-android/releases/tag/android-internal-smoke-0.1.1)
- [Direct FOSS debug APK download](https://github.com/Kwentin3/messenger-imap-android/releases/download/android-internal-smoke-0.1.1/messenger-imap-android-foss-debug-2.50.0.apk)

---

## Delta Chat Android Client

This is the Android client for [Delta Chat](https://delta.chat/).

[<img src="https://delta.chat/assets/badges/get-it-on-gplay.png" alt="Get it on Google Play" height="48">](https://play.google.com/store/apps/details?id=chat.delta)
[<img src="https://delta.chat/assets/badges/get-it-on-fdroid.png" alt="Get it on F-Droid" height="48">](https://f-droid.org/app/com.b44t.messenger)

Other download options and downloads for other platforms can be
found at [get.delta.chat](https://get.delta.chat).

For the core library and other common info, please refer to the
[Chatmail Core Library](https://github.com/chatmail/core).

For general contribution hints, please refer to [CONTRIBUTING.md](./CONTRIBUTING.md).
For building the app, refer to  [BUILDING.md](./BUILDING.md).

<img alt="Screenshot Chat List" src="fastlane/metadata/android/en-US/images/phoneScreenshots/1.png" width="298" /> <img alt="Screenshot Chat View" src="fastlane/metadata/android/en-US/images/phoneScreenshots/2.png" width="298" />


# Translations

Android metadata and changelogs are translated using [Weblate](https://hosted.weblate.org/projects/deltachat/android-metadata/).

<a href="https://hosted.weblate.org/engage/deltachat/">
<img src="https://hosted.weblate.org/widget/deltachat/android-metadata/svg-badge.svg" alt="Translation status" />
</a>

App strings and website are translated using [Transifex](https://app.transifex.com/delta-chat/).

# Credits

Many of the user interface classes were based on the Android Signal messenger when we ported it from the former Telegram-UI base in 2019. 
Meanwhile, development has diverged in many areas. 


# License

Licensed GPLv3+, see the LICENSE file for details.

Copyright © Delta Chat contributors.

# Giphy

![screen_record](screenRecord/giphy_sreen.gif)

<br>

### Application Description

This application is used to download a list of GIF images from the [Giphy](https://developers.giphy.com/) web API and show them to the user. 
If desired, the user can click on any GIF and it will open on the next screen in full-screen mode.

**Images are uploaded in two different ways:**
1. [Trending GIF images](https://developers.giphy.com/docs/api/endpoint/#trending) that are loaded every time you enter the application either from the database or from 
the Internet if the database is empty. If you perform a “swipe to refresh” action on the screen with 
a list of GIFs, a new list with trending GIFs will be downloaded directly from the Internet, saved to the database, 
and displayed on the screen.
2. [Searched GIF images](https://developers.giphy.com/docs/api/endpoint/#search) that are relevant to the query entered in the search field (there is a check for entering certain 
characters). These images are downloaded from the Internet and immediately displayed on the search screen 
without saving them to the database

When opening the application, the user is greeted with a splash screen on which the Internet connection is checked. 
If there is no connection, the corresponding screen is displayed, which informs about this and offers to go 
to the device settings and turn on the Internet

<br>

---

<br>

### Libraries and features that were used in the project


- [Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- [Material 3 Design](https://developer.android.com/jetpack/androidx/releases/compose-material3)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Multi-module project](https://developer.android.com/topic/modularization)
- MVI architecture
- [Hilt for DI](https://dagger.dev/hilt/)
- [Nested navigation](https://developer.android.com/jetpack/compose/navigation#nested-nav)
- Animated screen transitions
- [Lottie animations for compose](https://github.com/airbnb/lottie/blob/master/android-compose.md)
- The Internet connectivity check
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Retrofit2](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Swipe Refresh](https://google.github.io/accompanist/swiperefresh/)
- [Coil](https://coil-kt.github.io/coil/)
- Input text validation
- Landscape/Portrait screen orientation
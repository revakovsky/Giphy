# Giphy

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

- Kotlin DSL
- Material 3 Design
- Multi-module project
- Jetpack Compose
- MVVM architecture
- Hilt for DI
- Nested navigation
- Animated screen transitions
- Lottie animations for compose
- The Internet connectivity check
- Coroutines
- Retrofit2 + OkHttp
- Room
- Swipe Refresh
- Coil
- Input text validation
- Landscape/Portrait screen orientation
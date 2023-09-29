package com.revakovskyi.giphy.navigation

import com.revakovskyi.giphy.navigation.Screens.GifInfoScreen.route

internal const val GIF_URL = "url"

sealed class Screens(
    val route: String,
    val arguments: (String) -> String = { "" },
) {
    object SPLASH_NAVIGATION_ROUTE : Screens("splash_navigation")
    object GIFS_NAVIGATION_ROUTE : Screens("gifs_navigation")


    object SplashScreen : Screens("splash_screen")
    object GifsScreen : Screens("gifs_screen")
    object GifInfoScreen : Screens(
        route = "gif_info_screen/{$GIF_URL}",
        arguments = { url -> route.replace(oldValue = "{$GIF_URL}", newValue = url) }
    )

}

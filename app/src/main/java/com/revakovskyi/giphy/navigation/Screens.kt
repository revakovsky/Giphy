package com.revakovskyi.giphy.navigation

import com.revakovskyi.giphy.navigation.Screens.GifInfoScreen.route

internal const val GIF_ID = "id"

internal sealed class Screens(
    val route: String,
    val arguments: (String) -> String = { "" },
) {
    object GIFS_NAVIGATION_ROUTE : Screens("gifs_navigation")
    object SPLASH_NAVIGATION_ROUTE : Screens("splash_navigation")

    object SplashScreen : Screens("splash_screen")
    object GifsScreen : Screens("gifs_screen")
    object GifInfoScreen : Screens(
        route = "gif_info_screen/{$GIF_ID}",
        arguments = { id -> route.replace(oldValue = "{$GIF_ID}", newValue = id) }
    )

}

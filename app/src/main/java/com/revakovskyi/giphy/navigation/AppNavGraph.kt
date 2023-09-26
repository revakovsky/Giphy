package com.revakovskyi.giphy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.revakovskyi.giphy.core.sharedViewModel
import com.revakovskyi.giphy.presentation.screens.gif_info.GifInfoScreen
import com.revakovskyi.giphy.presentation.screens.gifs.GifsScreen
import com.revakovskyi.giphy.presentation.screens.gifs.GifsViewModel
import com.revakovskyi.giphy.presentation.screens.splash.SplashScreen
import com.revakovskyi.giphy.presentation.screens.splash.SplashViewModel

@Composable
internal fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = Screens.SPLASH_NAVIGATION_ROUTE.route) {

        navigation(
            route = Screens.SPLASH_NAVIGATION_ROUTE.route,
            startDestination = Screens.SplashScreen.route
        ) {
            composable(route = Screens.SplashScreen.route) {
                val viewModel: SplashViewModel = it.sharedViewModel(navController)

                SplashScreen(
                    onOpenGifsScreen = {
                        navController.navigate(Screens.GIFS_NAVIGATION_ROUTE.route) {
                            popUpTo(Screens.SPLASH_NAVIGATION_ROUTE.route) { inclusive = false }
                        }
                    },
                    onEvent = { event -> viewModel.onEvent(event) },
                    state = viewModel.state
                )
            }
        }

        navigation(
            route = Screens.GIFS_NAVIGATION_ROUTE.route,
            startDestination = Screens.GifsScreen.route
        ) {

            composableWithAnimatedTransition(route = Screens.GifsScreen.route) { _, navBackStackEntry ->
                val viewModel: GifsViewModel = navBackStackEntry.sharedViewModel(navController)

                GifsScreen()
            }

            composableWithAnimatedTransition(route = Screens.GifInfoScreen.route) { _, navBackStackEntry ->
                val viewModel: GifsViewModel = navBackStackEntry.sharedViewModel(navController)

                GifInfoScreen()
            }

        }

    }

}
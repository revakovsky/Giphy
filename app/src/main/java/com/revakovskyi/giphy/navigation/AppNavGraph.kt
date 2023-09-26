package com.revakovskyi.giphy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.revakovskyi.giphy.core.sharedViewModel
import com.revakovskyi.giphy.presentation.screens.gifs.GifsScreen
import com.revakovskyi.giphy.presentation.screens.gifs.GifsViewModel
import com.revakovskyi.giphy.presentation.screens.splash.SplashScreen
import com.revakovskyi.giphy.presentation.screens.splash.SplashViewModel

@Composable
internal fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = Screens.SplashScreen.route) {

        navigation(
            route = Screens.SPLASH_NAVIGATION_ROUTE.route,
            startDestination = Screens.GifsScreen.route
        ) {
            composable(route = Screens.SplashScreen.route) {
                val viewModel: SplashViewModel = it.sharedViewModel(navController)

                SplashScreen()
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

            composableWithAnimatedTransition(route = Screens.GifsScreen.route) { _, navBackStackEntry ->
                val viewModel: GifsViewModel = navBackStackEntry.sharedViewModel(navController)

                GifsScreen()
            }

        }

    }

}
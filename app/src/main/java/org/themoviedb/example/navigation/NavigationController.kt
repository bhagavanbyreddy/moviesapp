package org.themoviedb.example.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.themoviedb.example.details.ui.DetailsScreen
import org.themoviedb.example.details.viewModel.DetailsViewModel
import org.themoviedb.example.home.ui.HomeScreen
import org.themoviedb.example.home.viewModel.HomeViewModel
import org.themoviedb.example.splashscreen.SplashScreen
import org.themoviedb.example.splashscreen.SplashViewModel
import org.themoviedb.example.util.NavigationConstants
import kotlin.system.exitProcess

@Composable
fun NavigationController(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationConstants.HOME
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {

        composable(NavigationConstants.SPLASH) {
            BackHandler {
                // finish the application
                exitProcess(0)
            }
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(navController = navController,viewModel = viewModel)
        }
        composable(NavigationConstants.HOME) {

            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController = navController,viewModel = viewModel)
        }

        composable(NavigationConstants.DETAILS+"/{id}") {
                backStackEntry ->
            BackHandler(enabled = true) {
                navController.popBackStack()
            }
                backStackEntry.arguments?.getString("id","0")?.let { _ ->
                val viewModel = hiltViewModel<DetailsViewModel>()
                DetailsScreen(navController = navController,viewModel = viewModel)
            }

        }

    }
}
package uk.ac.tees.mad.travelr.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.travelr.ui.screens.HomeScreen
import uk.ac.tees.mad.travelr.ui.screens.SignInScreen
import uk.ac.tees.mad.travelr.ui.screens.SignUpScreen
import uk.ac.tees.mad.travelr.ui.screens.SplashScreen
import uk.ac.tees.mad.travelr.viewmodels.AuthScreenViewModel
import uk.ac.tees.mad.travelr.viewmodels.HomeScreenViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val authViewModel = hiltViewModel<AuthScreenViewModel>()

    val homeViewModel=hiltViewModel<HomeScreenViewModel>()

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {

        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController, viewModel = authViewModel)
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen(viewModel = homeViewModel)
        }

        composable(Screen.SignInScreen.route) {
            SignInScreen(
                viewModel = authViewModel,
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUpScreen.route) {
                        popUpTo(Screen.SignInScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigationToHome = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.SignInScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(
                viewModel=authViewModel,
                onNavigationToSignIn = {
                    navController.navigate(Screen.SignInScreen.route) {
                        popUpTo(Screen.SignUpScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigationToHome = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.SignUpScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

    }

}
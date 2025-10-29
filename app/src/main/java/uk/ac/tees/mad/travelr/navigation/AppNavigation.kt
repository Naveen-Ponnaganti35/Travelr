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

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val viewModel = hiltViewModel<AuthScreenViewModel>()

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {

        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen()
        }

        composable(Screen.SignInScreen.route) {
            SignInScreen(
                viewModel = viewModel,
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
                viewModel=viewModel,
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
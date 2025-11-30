package uk.ac.tees.mad.travelr.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.travelr.data.models.user.toEntity
import uk.ac.tees.mad.travelr.ui.screens.MainScreen
import uk.ac.tees.mad.travelr.ui.screens.SignInScreen
import uk.ac.tees.mad.travelr.ui.screens.SignUpScreen
import uk.ac.tees.mad.travelr.ui.screens.SplashScreen
import uk.ac.tees.mad.travelr.viewmodels.AuthScreenViewModel
import uk.ac.tees.mad.travelr.viewmodels.HomeScreenViewModel
import uk.ac.tees.mad.travelr.viewmodels.ProfileScreenViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val authViewModel = hiltViewModel<AuthScreenViewModel>()

    val homeViewModel = hiltViewModel<HomeScreenViewModel>()

    val profileViewModel = hiltViewModel<ProfileScreenViewModel>()

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {

        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController, viewModel = authViewModel)
        }

        composable(Screen.HomeScreen.route) {
            MainScreen(viewModel = homeViewModel,profileViewModel)
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
                    profileViewModel.fetchCurrentUser()
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
                viewModel = authViewModel,
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
                },
                saveProfile = { user ->
                    Log.d("Hello", "AppNavigation: ${user.fullName} && ${user.email} ")
                    profileViewModel.insertUser(user)
                }
            )
        }

    }

}
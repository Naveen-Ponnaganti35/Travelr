package uk.ac.tees.mad.travelr.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.travelr.navigation.bottom_navigation.BottomNavScreen
import uk.ac.tees.mad.travelr.navigation.bottom_navigation.bottomNavScreens
import uk.ac.tees.mad.travelr.ui.screens.bottom_screens.AttractionScreen
import uk.ac.tees.mad.travelr.ui.screens.bottom_screens.HomeScreen
import uk.ac.tees.mad.travelr.ui.screens.bottom_screens.ItineraryScreen
import uk.ac.tees.mad.travelr.ui.screens.bottom_screens.ProfileScreen
import uk.ac.tees.mad.travelr.viewmodels.HomeScreenViewModel
import uk.ac.tees.mad.travelr.viewmodels.ItineraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: HomeScreenViewModel) {
    val bottomNavController = rememberNavController()
    val homeViewModel=hiltViewModel<HomeScreenViewModel>()
    val itineraryViewModel: ItineraryViewModel=hiltViewModel()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry = bottomNavController.currentBackStackEntryAsState()
                val currentRoute =
                    navBackStackEntry.value?.destination?.route ?: BottomNavScreen.Home.route
                bottomNavScreens.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            bottomNavController.navigate(screen.route) {
                                popUpTo(bottomNavController.graph.startDestinationId) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        label = {
                            Text(screen.title)
                        },
                        icon = {
                            Icon(screen.icon, contentDescription = screen.title)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavScreen.Home.route,
            modifier = Modifier.padding(paddingValues)

        ) {
            composable(
                route = BottomNavScreen.Home.route
            ) {
                HomeScreen(
                    homeScreenViewModel = homeViewModel,
                    viewModel = itineraryViewModel
                )
            }
            composable(
                route = BottomNavScreen.Attractions.route
            ) {
                AttractionScreen()
            }
            composable(
                route = BottomNavScreen.Itinerary.route
            ) {
                ItineraryScreen(
                    viewModel=itineraryViewModel
                )
            }
            composable(
                route = BottomNavScreen.Profile.route
            ) {
                ProfileScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MainScreen(viewModel = hiltViewModel())
}


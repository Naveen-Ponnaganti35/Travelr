package uk.ac.tees.mad.travelr.navigation.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavScreen("home", "Home", Icons.Default.Home)
    object Attractions : BottomNavScreen("attractions", "Attractions", Icons.Default.Place)
    object Itinerary : BottomNavScreen("itinerary", "Itinerary", Icons.Default.List)
    object Profile : BottomNavScreen("profile", "Profile", Icons.Default.Person)
}

val bottomNavScreens = listOf(
    BottomNavScreen.Home,
    BottomNavScreen.Attractions,
    BottomNavScreen.Itinerary,
    BottomNavScreen.Profile
)
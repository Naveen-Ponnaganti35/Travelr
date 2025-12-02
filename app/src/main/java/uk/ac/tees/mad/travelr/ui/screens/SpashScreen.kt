package uk.ac.tees.mad.travelr.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.travelr.R
import uk.ac.tees.mad.travelr.navigation.Screen
import uk.ac.tees.mad.travelr.viewmodels.AuthScreenViewModel


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    fetchProfile:()->Unit,
    viewModel: AuthScreenViewModel = hiltViewModel()
) {
    val isLoggedIn = viewModel.isLoggedIn.collectAsStateWithLifecycle()
    val showSplashScreen = viewModel.showSplashScreen.collectAsStateWithLifecycle()


    // will trigger the login check
    LaunchedEffect(Unit) {
        viewModel.authenticateUser()
    }


    LaunchedEffect(isLoggedIn.value, showSplashScreen.value) {

        // splash screen has turned false and logged in it not null
        if (!showSplashScreen.value && isLoggedIn.value != null) {
            // logged in
            if (isLoggedIn.value == true) {
                fetchProfile()
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.SplashScreen.route) {
                        inclusive = true
                    }
                }
            } else {
                navController.navigate(Screen.SignInScreen.route) {
                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.travel_app),
            contentDescription = "travelApp",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier=Modifier.height(16.dp))
        Text("Discover Your Next Adventure", fontSize = 24.sp, color = MaterialTheme.colorScheme.onSurface)
    }

}


@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {

    SplashScreen(
        navController = rememberNavController(),
        fetchProfile = {}
    )

}
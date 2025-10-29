package uk.ac.tees.mad.travelr.navigation

sealed class Screen(val route:String){

    object SignUpScreen:Screen("sign_up")
    object SignInScreen:Screen("sign_in")
    object HomeScreen:Screen("home")
    object SplashScreen:Screen("splash")
}
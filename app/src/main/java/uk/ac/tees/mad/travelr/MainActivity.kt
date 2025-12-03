package uk.ac.tees.mad.travelr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import uk.ac.tees.mad.travelr.navigation.AppNavigation
import uk.ac.tees.mad.travelr.ui.theme.TravelrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelrTheme {
                AppNavigation()
            }
        }
    }
}

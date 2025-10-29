package uk.ac.tees.mad.travelr.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    Scaffold {
        Column(
            modifier = Modifier.padding(it).fillMaxSize()
        ) {
            Text("Home Screen ", fontSize = 34.sp)
        }
    }
    
}
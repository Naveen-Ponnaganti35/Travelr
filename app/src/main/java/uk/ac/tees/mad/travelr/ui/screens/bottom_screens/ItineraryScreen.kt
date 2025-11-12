package uk.ac.tees.mad.travelr.ui.screens.bottom_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uk.ac.tees.mad.travelr.viewmodels.ItineraryViewModel

@Composable
fun ItineraryScreen(
    modifier: Modifier = Modifier,
    viewModel: ItineraryViewModel
) {

    val itineraries = viewModel.itineraries.collectAsStateWithLifecycle()

//
//    Scaffold(
//    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding()
                .fillMaxSize()
        ) {

            when{
                itineraries.value.isEmpty()->{
                    EmptyItineraryBoxCmp()
                }
                else->{
//                    Text(
//                        text = "Your Itinerary",
//                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Normal),
//                        color = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 16.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
//                        textAlign = TextAlign.Center
//                    )
                    ItineraryHeader()

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(itineraries.value) { item ->
                            ActivityItem(
                                activity = item,
                                viewModel = viewModel,
//                                onAddToItinerary = {}
                            )
                        }
                    }
                }
            }

//        }
    }

}
@Composable
fun ItineraryHeader(
    title: String = "Your Itinerary",
    subTitle: String = "All your saved destinations in one place"
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.BookmarkAdd,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = subTitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 40.dp) // aligns with text, not icon
        )

        Spacer(modifier = Modifier.height(8.dp))

        Divider(
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            thickness = 1.dp
        )
    }
}


@Composable
fun EmptyItineraryBoxCmp(
    message: String = "No destinations in your itinerary yet.",
    subText: String = "Start exploring and add your favorite places!"
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.BookmarkAdd,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = subText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


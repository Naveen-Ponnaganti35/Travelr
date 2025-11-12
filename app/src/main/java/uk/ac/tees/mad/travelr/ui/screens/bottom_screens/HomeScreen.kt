package uk.ac.tees.mad.travelr.ui.screens.bottom_screens

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import uk.ac.tees.mad.travelr.data.models.destinations.DestinationData
import uk.ac.tees.mad.travelr.ui.screens.home.HomeUiState
import uk.ac.tees.mad.travelr.viewmodels.HomeScreenViewModel
import uk.ac.tees.mad.travelr.viewmodels.ItineraryViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel,
    viewModel: ItineraryViewModel

) {
    val searchQuery =
        homeScreenViewModel.searchInput.collectAsStateWithLifecycle()

    val homeUiSnapshotState = homeScreenViewModel.homeUiState.collectAsStateWithLifecycle()
    val homeUiState = homeUiSnapshotState.value




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)

    ) {

        SearchBarCmp(
            searchQuery = searchQuery.value,
            onSearchQueryChange = { homeScreenViewModel.updateSearchInput(it) },
            getData = { homeScreenViewModel.getData() }
        )


        when {
            homeUiState.isLoading -> {
                // progress bar
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                }

            }

            homeUiState.error != null -> {

                ErrorCmp(
                    homeUiState = homeUiState,
                    retryLogic = {
                        val city =
                            if (searchQuery.value.isNotEmpty()) searchQuery.value else "paris"
                        homeScreenViewModel.retryClick(city)
                    }
                )
            }

            homeUiState.destinationData.isNotEmpty() -> {
                // there are result
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(homeUiState.destinationData) { destination ->
                        ActivityItem(
                            destination,
                            viewModel,
//                            onAddToItinerary = {
//                                Log.d(
//                                    "HomeScreen",
//                                    "HomeScreen: ${it.description?.get(10).toString()} "
//                                )
//                                showDialog.value = true
//                                selectedDestination.value = it
//                            }
                        )
                    }
                }
            }

            else -> {
                EmptyResultCmp()
                // there is no result
                // api call success but empty or null data
            }

        }

//        if (showDialog.value && selectedDestination.value != null) {
//            AddToItineraryDialog(
//                title = "Add to Itinerary",
//                activity = selectedDestination.value!!,
//                onDismiss = {
//                    showDialog.value = false
//                },
//                onSave = { destination ->
//                    viewModel.saveItineraries(destination)
//                    showDialog.value = false
//                }
//            )
//
//        }
    }


}


@Composable
fun EmptyResultCmp(
    modifier: Modifier = Modifier,
    message: String = "No results found"
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "No results",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(48.dp)
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
fun ErrorCmp(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    retryLogic: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.size(48.dp)
                )

                Text(
                    text = homeUiState.error ?: "Something went wrong",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )

//                Text(
//                    text = homeUiState.error ?: "Unknown error occurred. Please try again.",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onErrorContainer,
//                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
//                )

                Button(
                    onClick = { retryLogic() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Try Again")
                }
            }
        }
    }

}


@Composable
fun AddToItineraryDialog(
    title: String,
    activity: DestinationData,
    onDismiss: () -> Unit,
    onSave: (DestinationData) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(title)
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(activity)
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cancel")
            }
        },
    )
}

@Composable
fun ActivityItem(
    activity: DestinationData,
    viewModel: ItineraryViewModel,
//    onDeleteClick:(String)->Unit
//    onAddToItinerary: (DestinationData) -> Unit,
) {

    val isSaved = rememberSaveable { mutableStateOf(false) }


    val showRemoveDialog = rememberSaveable { mutableStateOf(false) }
    val itemId = rememberSaveable { mutableStateOf<String?>(null) }

    if (showRemoveDialog.value && itemId.value != null) {
        AlertDialog(
            onDismissRequest = { showRemoveDialog.value = false },
            title = { Text("Remove from Itinerary?") },
            text = { Text("Are you sure you want to remove '${activity.name}' from your itinerary?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteItinerary(itemId.value!!)
                    isSaved.value = false
                    showRemoveDialog.value = false
                }) {
                    Text("Remove")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRemoveDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    LaunchedEffect(activity.id) {
        activity.id?.let { id ->
            viewModel.isInItinerary(id) {
                isSaved.value = it
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 4.dp)
            .clip(RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Image section
            if (!activity.pictures.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(activity.pictures[0]),
                        contentDescription = activity.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.15f)
                                    )
                                )
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Title
            Text(
                text = activity.name ?: "Unknown Destination",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Use shortDescription first, fallback to description
            val displayText = activity.description?.replace(Regex("<.*?>"), "")?.trim()
                ?: activity.description?.replace(Regex("<.*?>"), "")?.trim()
                ?: ""

            if (displayText.isNotEmpty()) {
                Text(
                    text = if (displayText.length > 150) displayText.take(150) + "..." else displayText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.4f,
                    maxLines = 3
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // onsave or remove button
            OutlinedButton(
                onClick = {
                    activity.id?.let { id ->
                        // if the data is saved ->alert ->unsave the data
                        if (isSaved.value) {
//                            onDeleteClick(id)
                            showRemoveDialog.value = true
                            itemId.value = id
//                            viewModel.deleteItinerary(id)
//                            isSaved.value = false
                        } else {
                            // is the data to be saved save it directly
                            viewModel.saveItineraries(activity)
                            isSaved.value = true
                        }
                    }
//                    onAddToItinerary(activity)

                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.BookmarkAdd,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isSaved.value) "Remove from Itinerary" else "Add to Itinerary",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


@Composable
fun SearchBarCmp(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    getData: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { onSearchQueryChange(it) },
        placeholder = { Text("Enter location") },
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = {
                    getData()
                    focusManager.clearFocus()
                    hideKeyboard(context)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                getData()
                focusManager.clearFocus()
                hideKeyboard(context)
            },
            onDone = {
                getData()
                focusManager.clearFocus()
                hideKeyboard(context)

            }
        )
    )
}


private fun hideKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(null, 0)
}
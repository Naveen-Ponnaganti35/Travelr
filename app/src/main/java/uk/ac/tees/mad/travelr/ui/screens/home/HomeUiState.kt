package uk.ac.tees.mad.travelr.ui.screens.home

import uk.ac.tees.mad.travelr.data.models.cities.CitiesResponse
import uk.ac.tees.mad.travelr.data.models.destinations.DestinationData

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val citiesResponse: CitiesResponse? = null,
    val destinationData: List<DestinationData> = emptyList()
)
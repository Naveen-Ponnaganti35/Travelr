package uk.ac.tees.mad.travelr.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.travelr.data.local.ItineraryEntity
import uk.ac.tees.mad.travelr.data.local.ItineraryRepository
import uk.ac.tees.mad.travelr.data.models.destinations.DestinationData

class ItineraryViewModel(
    private val repository: ItineraryRepository
) : ViewModel() {

    private val _itineraries = MutableStateFlow<List<DestinationData>>(emptyList())

    val itineraries = _itineraries.asStateFlow()

    private val _latestItinerary = MutableStateFlow<ItineraryEntity?>(null)
    val latestItinerary = _latestItinerary.asStateFlow()

    init {
        fetchItineraries()
    }

    fun fetchItineraries() {
        viewModelScope.launch {
            _itineraries.value = repository.getAllItineraries().reversed()
        }
    }

    fun saveItineraries(destination: DestinationData) {
        viewModelScope.launch {
            repository.insertItinerary(destination)
            fetchItineraries()
        }
    }

    fun isInItinerary(id: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val exists = repository.isInItinerary(id)
            callback(exists)
        }
    }


    fun deleteItinerary(destinationId: String) {
        viewModelScope.launch {
            repository.deleteItinerary(destinationId)
            fetchItineraries()
        }
    }

    fun deleteAllItineraries() {
        viewModelScope.launch {
            repository.deleteAllItineraries()
            fetchItineraries()

        }
    }

    fun getLatestItinerary() {
        viewModelScope.launch {
            _latestItinerary.value = repository.getLatestItinerary()
        }
    }

}
package uk.ac.tees.mad.travelr.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.ac.tees.mad.travelr.data.AmadeusRepository
import uk.ac.tees.mad.travelr.ui.screens.home.HomeUiState
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: AmadeusRepository
) : ViewModel() {


    private val _searchInput = MutableStateFlow("")
    val searchInput = _searchInput.asStateFlow()

//        private val _activitiesState =
//            MutableStateFlow<NetworkResult<List<Data>>?>(null)
//        val activitiesState = _activitiesState.asStateFlow()

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

//        private val _destinationState =
//            MutableStateFlow<NetworkResult<List<DestinationData>>?>(null)

//        val destinationState = _destinationState.asStateFlow()

    init {
        //        fetchDestination(41.397158,2.160873)
//        fetchActivities("london")
        _homeUiState.update { it.copy(isLoading = true) }
        fetchActivities("london")
    }

    fun updateSearchInput(newValue: String) {
        _searchInput.value = newValue

    }

    fun getData() {
        if (_searchInput.value.isBlank()) {
            return
        } else {
            // Reset states
            _homeUiState.update {
                it.copy(isLoading = true)
            }
//                _activitiesState.value = NetworkResult.Loading()
            //            _destinationState.value = NetworkResult.Loading()
            //            _activities.value = emptyList()
            //            _destinations.value = emptyList()
            fetchActivities(_searchInput.value.lowercase(Locale.ENGLISH))
        }
    }

    fun retryClick(city: String) {
        _homeUiState.update {
            it.copy(isLoading = true)
        }
//                _activitiesState.value = NetworkResult.Loading()
        //            _destinationState.value = NetworkResult.Loading()
        //            _activities.value = emptyList()
        //            _destinations.value = emptyList()
        fetchActivities(city.lowercase(Locale.ENGLISH))

    }

    fun fetchActivities(cityCode: String) {
        viewModelScope.launch {
            try{


//                _activitiesState.value = NetworkResult.Loading()

                val response = repository.getActivities(cityCode)

                if (response.isSuccessful) {

                    val citiesResponse = response.body()

                    // null response show error
                    if (citiesResponse == null) {
                        _homeUiState.update { uiState ->
                            uiState.copy(
                                isLoading = false,
                                error = "api call returned null response",
                                destinationData = emptyList()
                            )
                        }
                        return@launch
                    } else if (citiesResponse.warnings != null && citiesResponse.warnings.isNotEmpty()) {
                        // api return the warning can be due to no result
                        _homeUiState.update { uiState ->
                            uiState.copy(
                                isLoading = false,
                                error = "${citiesResponse.warnings[0]?.code} ${citiesResponse.warnings[0]?.detail}",
                                destinationData = emptyList()
                            )
                        }
                        return@launch
                    } else if (citiesResponse.data == null || citiesResponse.data.isEmpty()) {
                        // no data from the api
                        _homeUiState.update { uiState ->
                            uiState.copy(
                                isLoading = false,
                                error = "api call returned empty response",
                                destinationData = emptyList()
                            )
                        }
                        return@launch
                    }

                    _homeUiState.update {
                        // storing the result and clearing the previous response
                        it.copy(citiesResponse = citiesResponse, destinationData = emptyList())
                    }

                    //                if (citiesResponse.data.isEmpty()) {
                    //                    // no activities found
                    //                    _activitiesState.value =
                    //                        NetworkResult.Error("No activities found for '$cityCode'")
                    //                    _destinationState.value =
                    //                        NetworkResult.Error("No destinations found for '$cityCode'")
                    //                    return@launch
                    //                }
                    //                _activitiesState.value = NetworkResult.Success(citiesResponse)

                    // from top 3 result take the one that is not null
                    val firstValidGeoCode =
                        response.body()?.data?.take(3)?.filterNotNull()?.firstOrNull {
                            it.geoCode != null
                        }?.geoCode


                    // is no coordinates are there return error
                    if (firstValidGeoCode == null) {
                        _homeUiState.update { uiState ->
                            uiState.copy(
                                isLoading = false,
                                error = "no valid coordinates found",
                                destinationData = emptyList()
                            )
                        }
                        return@launch
                    }

                    Log.d("lat and long", "fetchActivities: ${firstValidGeoCode.latitude} and ${firstValidGeoCode.longitude} ")
                    // second api call
                    fetchDestination(firstValidGeoCode.latitude, firstValidGeoCode.longitude)

                    //                firstValidGeoCode.forEach {
                    //
                    //                    val lat = it.geoCode?.latitude
                    //                    val long = it.geoCode?.longitude
                    //
                    //                    // should never happen
                    //                    if (lat == null || long == null) {
                    //                        _homeUiState.update { uiState ->
                    //                            uiState.copy(isLoading = false, error = "lat or long is null")
                    //                        }
                    //                    } else {
                    //                        fetchDestination(lat, long)
                    //                    }
                    //                }
                } else {
                    // error clause
                    _homeUiState.update {
                        it.copy(
                            isLoading = false,
                            error = response.errorBody()?.string(),
                            destinationData = emptyList()
                        )
                    }
//                    _activitiesState.value = NetworkResult.Error(response.errorBody()?.string())
                }
            }catch (e: Exception){
                val errorMessage = when (e) {
                    is java.net.UnknownHostException -> "No internet connection"
                    is java.net.SocketTimeoutException -> "Connection timeout"
                    is java.io.IOException -> "Network error occurred"
                    else -> "Something went wrong"
                }

                _homeUiState.update {
                    it.copy(
                        isLoading = false,
                        error = errorMessage,
                        destinationData = emptyList()
                    )
                }
            }
        }
    }

    fun fetchDestination(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try{


                //            _destinationState.value=NetworkResult.Loading()
                val response = repository.getDestinations(latitude, longitude)
                if (response.isSuccessful) {
                    val newList = response.body()?.data?.filterNotNull() ?: emptyList()

                    _homeUiState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            destinationData = it.destinationData + newList
                        )
                    }
                    //                val updatedList = when (val current = _destinationState.value) {
                    //                    is NetworkResult.Success -> {
                    //                        _homeUiState.update {
                    //                            it.copy(newList + current.response)
                    //                        }
                    //                        (current.response ?: listOf()) + newList
                    //                    }
                    //
                    //                    else -> {
                    //                        newList
                    //                    }
                    //                }
                    //                _destinationState.value = NetworkResult.Success(updatedList)
                } else {
                    _homeUiState.update {
                        it.copy(isLoading = false, error = response.errorBody()?.string())
                    }
                    //                _destinationState.value = NetworkResult.Error(response.errorBody()?.string())
                }
            }catch (e: Exception){
                val errorMessage = when (e) {
                    is java.net.UnknownHostException -> "No internet connection"
                    is java.net.SocketTimeoutException -> "Connection timeout"
                    is java.io.IOException -> "Network error occurred"
                    else -> "Something went wrong"
                }

                _homeUiState.update {
                    it.copy(
                        isLoading = false,
                        error = errorMessage,
                        destinationData = emptyList()
                    )
                }
            }
        }
    }
    //
    //    private val _activities = MutableStateFlow<List<Data>>(emptyList())
    //    val activities = _activities.asStateFlow()
    //
    //    fun fetchActivities(cityCode: String) {
    //        viewModelScope.launch {
    //            val response = repository.getActivities(cityCode)
    //            if (response.isSuccessful) {
    //                _activities.value = response.body()?.data?.filterNotNull() ?: emptyList()
    //                response.body()?.data?.take(3)?.forEach {
    //                    val lat = it?.geoCode?.latitude
    //                    val long = it?.geoCode?.longitude
    //                    Log.d("Api Call", "fetchActivities: ")
    //                    fetchDestination(lat ?: 41.397158, long ?: 2.160873)
    //                }
    //            } else {
    //                val errorMsg = response.errorBody()?.string()
    //                Log.e(
    //                    "HomeViewModel",
    //                    "Error fetching activities: ${response.errorBody()?.string()}"
    //                )
    //            }
    //        }
    //    }
    //
    //    private val _destinations = MutableStateFlow<List<DestinationData>>(emptyList())
    //    val destinations = _destinations.asStateFlow()
    //
    //
    //    fun fetchDestination(longitude: Double, latitude: Double) {
    //        viewModelScope.launch {
    //            val response = repository.getDestinations(longitude, latitude)
    //            if (response.isSuccessful) {
    //                val newList: List<DestinationData> =
    //                    response.body()?.data?.filterNotNull() ?: emptyList()
    //                _destinations.update { oldList -> oldList + newList }
    ////                _destinations.value=response.body()
    ////                response.body()?.data?.forEach {
    ////                    Log.d("TAG", "fetchDestination:  ${it?.pictures}")
    ////                }
    //            } else {
    //                val errorMsg = response.errorBody()?.string()
    //            }
    //        }
    //    }


    //    private val _activities = MutableStateFlow<CitiesResponse?>(null)
    //    val activities = _activities.asStateFlow()
    //
    //    fun fetchActivities(cityCode: String) {
    //        viewModelScope.launch {
    //            val response = repository.getActivities(cityCode) // io dispatcher
    //            if (response.isSuccessful) { // 200..299
    //                Log.d(TAG, "fetchActivities: " + response.body())
    //                _activities.value = response.body()
    //                Log.d(TAG, "fetchActivities: ")
    //            } else { // 400..500
    //                val errorJson = response.errorBody()?.string()
    //                Log.d(TAG, "fetchActivities: $errorJson")
    //                response.message()
    //                response.code() // 401, 500
    //            }
    //        }
    //    }
    //
    //    companion object {
    //        const val TAG = "HomeScreenViewModel"
    //    }


    fun resetToDefault() {
        _searchInput.value = ""
        _homeUiState.update {
            it.copy(isLoading = true)
        }
        fetchActivities("london")
    }

}
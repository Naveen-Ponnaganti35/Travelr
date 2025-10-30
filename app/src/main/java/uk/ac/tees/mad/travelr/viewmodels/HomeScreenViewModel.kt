package uk.ac.tees.mad.travelr.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.ac.tees.mad.travelr.data.AmadeusRepository
import uk.ac.tees.mad.travelr.data.models.cities.Data
import uk.ac.tees.mad.travelr.data.models.destinations.DestinationData
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: AmadeusRepository
) : ViewModel() {


    private val _searchInput = MutableStateFlow("")
    val searchInput = _searchInput.asStateFlow()


    init {
        fetchDestination(41.397158,2.160873)
    }

    fun updateSearchInput(newValue: String) {
        _searchInput.value = newValue

    }

    fun getData() {
//        if(_searchInput.value.isBlank()){
//            return
//        }else{
//            _activities.value = emptyList()
//            _destinations.value = emptyList()
//           fetchActivities(_searchInput.value)
//        }
    }


    private val _activities = MutableStateFlow<List<Data> >(emptyList())
    val activities = _activities.asStateFlow()

    fun fetchActivities(cityCode: String) {
        viewModelScope.launch {
            val response = repository.getActivities(cityCode)
            if (response.isSuccessful) {
                _activities.value =response.body()?.data?.filterNotNull() ?: emptyList()
                response.body()?.data?.take(3)?.forEach {
                    val lat = it?.geoCode?.latitude
                    val long = it?.geoCode?.longitude
                    fetchDestination(lat ?: 41.397158, long ?: 2.160873)
                }
            } else {
                val errorMsg = response.errorBody()?.string()
                Log.e(
                    "HomeViewModel",
                    "Error fetching activities: ${response.errorBody()?.string()}"
                )
            }
        }
    }

    private val _destinations = MutableStateFlow<List<DestinationData>>(emptyList())
    val destinations = _destinations.asStateFlow()


    fun fetchDestination(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            val response = repository.getDestinations(longitude, latitude)
            if (response.isSuccessful) {
                val newList: List<DestinationData> = response.body()?.data?.filterNotNull() ?: emptyList()
                _destinations.update { oldList -> oldList + newList }
//                _destinations.value=response.body()
//                response.body()?.data?.forEach {
//                    Log.d("TAG", "fetchDestination:  ${it?.pictures}")
//                }
            } else {
                val errorMsg = response.errorBody()?.string()
            }
        }
    }


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

}
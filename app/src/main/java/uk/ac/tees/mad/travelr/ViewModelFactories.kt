package uk.ac.tees.mad.travelr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uk.ac.tees.mad.travelr.viewmodels.*

class AppViewModelFactory(
    private val app: MyApp
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val c = app.container

        return when {
            modelClass.isAssignableFrom(HomeScreenViewModel::class.java) ->
                HomeScreenViewModel(c.amadeusRepository)

            modelClass.isAssignableFrom(ItineraryViewModel::class.java) ->
                ItineraryViewModel(c.itineraryRepository)

            modelClass.isAssignableFrom(ProfileScreenViewModel::class.java) ->
                ProfileScreenViewModel(c.profileRepository)

            modelClass.isAssignableFrom(AuthScreenViewModel::class.java) ->
                AuthScreenViewModel()

            else -> error("Unknown ViewModel: $modelClass")
        } as T
    }
}

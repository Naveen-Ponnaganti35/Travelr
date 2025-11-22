package uk.ac.tees.mad.travelr.viewmodels

import android.R.attr.password
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.travelr.data.models.user.ProfileRepository
import uk.ac.tees.mad.travelr.data.models.user.UserProfile
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val userRepository: ProfileRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserProfile>(UserProfile())
    val currentUser: StateFlow<UserProfile> = _currentUser
//
//    init {
////        // Fetch the current user when the ViewModel is created
//        fetchCurrentUser()
//    }

    fun fetchCurrentUser() {
        viewModelScope.launch {
            val user = userRepository.getCurrentUser()
            _currentUser.value = user
            Log.d("Hello", "fetchCurrentUser: ${user} ")
        }
    }

    fun deleteUserAccountPermanently(
        password:String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                userRepository.deleteUserAccountPermanently(password = password)
//                _currentUser.value = UserProfile()
                onSuccess()
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error deleting account", e)
                onError(e.message ?: "Failed to delete account")
            }
        }
    }

    fun insertUser(user: UserProfile) {
        viewModelScope.launch {
            Log.d("Hello viewmodel", "insertUser: ${user.fullName} && ${user.email}")
            userRepository.insertUser(user)
            // Optionally, refresh the current user after insertion
            fetchCurrentUser()
        }
    }

    fun updateUser(user: UserProfile) {
        viewModelScope.launch {
            _currentUser.value = user
            userRepository.updateUser(user)
            // Optionally, refresh the current user after update
            fetchCurrentUser()
        }
    }

    fun deleteLocalUser(){
        viewModelScope.launch {
            userRepository.deleteLocalUser()
        }
    }

//    fun deleteUserAccount(){
//        viewModelScope.launch {
//            userRepository.deleteUserAccountPermantly()
//        }
//    }

}

package uk.ac.tees.mad.travelr.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.ac.tees.mad.travelr.ui.states.SignInUiState
import uk.ac.tees.mad.travelr.ui.states.SignUpUiState
import javax.inject.Inject


@HiltViewModel
class AuthScreenViewModel
@Inject constructor() : ViewModel() {

    private val _signInUiState = MutableStateFlow(SignInUiState())
    val signInUiState = _signInUiState.asStateFlow()

    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState = _signUpUiState.asStateFlow()


    fun updateFullNameSignUp(newValue: String) {
        _signUpUiState.update {
            it.copy(
                fullName = newValue
            )
        }
    }

    fun updateEmailSignUp(newValue: String) {
        _signUpUiState.update {
            it.copy(
                email = newValue
            )
        }
    }

    fun updatePasswordSignUp(newValue: String) {
        _signUpUiState.update {
            it.copy(
                password = newValue
            )
        }
    }

    fun updateEmailSignIn(newValue: String) {
        _signInUiState.update {
            it.copy(
                email = newValue
            )
        }
    }

    fun updatePasswordSignIn(newValue: String) {
        _signInUiState.update {
            it.copy(
                password = newValue
            )
        }
    }


    // create a new user
    fun signUpUser(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val fullName = _signUpUiState.value.fullName
        val email = _signUpUiState.value.email
        val password = _signUpUiState.value.password

        if (email.isBlank() || fullName.isBlank() || password.isBlank()) {
            onError("Please Enter all the details")
            return
        }

        viewModelScope.launch {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onError(task.exception?.message ?: "Sign up Failed")
                    }
                }
        }
    }

    fun signInUser(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val email = _signInUiState.value.email
        val password = _signInUiState.value.password
        if (email.isBlank() || password.isBlank()) {
            onError("Please Enter all the details")
            return
        }
        viewModelScope.launch {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onError(task.exception?.message ?: "Sign in Failed")
                    }
                }
        }
    }

    fun forgotPassword(
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ){
        val email=_signInUiState.value.email
        if(email.isBlank()){
            onError("Please enter email")
            return
        }
        viewModelScope.launch {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener{task->
                    if(task.isSuccessful ){
                        Log.d("Auth", "Reset email sent to: $email")
                        onSuccess()
                    }else{
                        onError(task.exception?.message ?: "Failed to send reset email")
                    }
            }
        }
    }


    private val _showSplashScreen = MutableStateFlow(true)
    val showSplashScreen = _showSplashScreen.asStateFlow()

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn = _isLoggedIn.asStateFlow()


    fun authenticateUser() {
        viewModelScope.launch {
            delay(2000)

            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                // user is logged in
                _isLoggedIn.value = true
            } else {
                // user is  not logged in
                _isLoggedIn.value = false
            }
            _showSplashScreen.value = false
        }
    }


}
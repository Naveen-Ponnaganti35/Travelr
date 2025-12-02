package uk.ac.tees.mad.travelr.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uk.ac.tees.mad.travelr.data.models.user.UserProfile
import uk.ac.tees.mad.travelr.data.models.user.UserProfileEntity
import uk.ac.tees.mad.travelr.ui.components.OutlinedTextFieldCmp
import uk.ac.tees.mad.travelr.ui.components.TitleHeader
import uk.ac.tees.mad.travelr.viewmodels.AuthScreenViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onNavigationToSignIn: () -> Unit,
    onNavigationToHome: () -> Unit,
    saveProfile:(UserProfile)->Unit={},
    viewModel: AuthScreenViewModel= hiltViewModel(),
) {
    val scrollState = rememberScrollState()

    val context= LocalContext.current
    val signUpUiState = viewModel.signUpUiState.collectAsStateWithLifecycle()
    Scaffold {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            TitleHeader(
                title = "Sign Up",
            )
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )


                    Text(
                        text = "Sign up to get started with amazing deals",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextFieldCmp(
                        titleText = "Full Name",
                        placeHolderText = "Enter your full name",
                        value = signUpUiState.value.fullName,
                        image = Icons.Default.Person,
                        onValueChange = {
                            viewModel.updateFullNameSignUp(it)
                        }
                    )

                    OutlinedTextFieldCmp(
                        titleText = "Email",
                        placeHolderText = "Enter your email",
                        value = signUpUiState.value.email,
                        image = Icons.Default.Email,
                        onValueChange = {
                            viewModel.updateEmailSignUp(it)
                        }
                    )
                    OutlinedTextFieldCmp(
                        titleText = "Password",
                        placeHolderText = "Enter your password",
                        value = signUpUiState.value.password,
                        image = Icons.Default.Lock,
                        onValueChange = {
                            viewModel.updatePasswordSignUp(it)
                        }
                    )
                }

                Button(
                    onClick = {
                        viewModel.signUpUser(
                            onSuccess = {
                                saveProfile(
                                    UserProfile(
                                        fullName = signUpUiState.value.fullName,
                                        email = signUpUiState.value.email
                                    )
                                )
                                onNavigationToHome()
                            },
                            onError = {error->
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF22C55E)
                    ),
                ) {
                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // OR divider
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "OR",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))


                Spacer(modifier = Modifier.height(24.dp))

                // Sign up link
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Already have an account? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Sign in",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF22C55E),
                        modifier = Modifier.clickable { onNavigationToSignIn() }
                    )
                }
                Spacer(Modifier.height(32.dp))
            }


        }
    }

}


@Preview
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen(
        onNavigationToSignIn = {},
        onNavigationToHome = {}
    )
}
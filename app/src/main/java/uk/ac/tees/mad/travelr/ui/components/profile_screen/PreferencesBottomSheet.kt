package uk.ac.tees.mad.travelr.ui.components.profile_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.travelr.data.models.user.UserPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesBottomSheet(
    preferences: UserPreferences,
    onDismiss: () -> Unit,
    onSave: (UserPreferences) -> Unit
) {
    var destinationType by remember { mutableStateOf(preferences.favoriteDestinationType) }
    var currency by remember { mutableStateOf(preferences.currency) }
    var notificationsEnabled by remember { mutableStateOf(preferences.notificationEnabled) }
    var emailUpdatesEnabled by remember { mutableStateOf(preferences.emailUpdatesEnabled) }

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Edit Preferences",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Destination Type Dropdown
            var expandedDestination by remember { mutableStateOf(false) }
            val destinationTypes = listOf("Beach", "Mountain", "Historical", "City", "Adventure")

            Text(
                "Favorite Destination Type",
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = expandedDestination,
                onExpandedChange = { expandedDestination = it }
            ) {
                OutlinedTextField(
                    value = destinationType,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDestination)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedDestination,
                    onDismissRequest = { expandedDestination = false }
                ) {
                    destinationTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                destinationType = type
                                expandedDestination = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Currency Dropdown
            var expandedCurrency by remember { mutableStateOf(false) }
            val currencies = listOf("USD", "EUR", "GBP", "INR", "JPY", "AUD")

            Text(
                "Currency",
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = expandedCurrency,
                onExpandedChange = { expandedCurrency = it }
            ) {
                OutlinedTextField(
                    value = currency,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCurrency)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedCurrency,
                    onDismissRequest = { expandedCurrency = false }
                ) {
                    currencies.forEach { curr ->
                        DropdownMenuItem(
                            text = { Text(curr) },
                            onClick = {
                                currency = curr
                                expandedCurrency = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save Button
            Button(
                onClick = {
                    onSave(
                        UserPreferences(
                            favoriteDestinationType = destinationType,
                            currency = currency,
                            notificationEnabled = notificationsEnabled,
                            emailUpdatesEnabled = emailUpdatesEnabled
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Changes", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}






//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PreferencesBottomSheet(
//    preferences: UserPreferences,
//    onDismiss: () -> Unit,
//    onSave: (UserPreferences) -> Unit
//) {
//    var destinationType by remember { mutableStateOf(preferences.favoriteDestinationType) }
//    var currency by remember { mutableStateOf(preferences.currency) }
//    var notificationsEnabled by remember { mutableStateOf(preferences.notificationEnabled) }
//    var emailUpdatesEnabled by remember { mutableStateOf(preferences.emailUpdatesEnabled) }
//
//    val sheetState = rememberModalBottomSheetState()
//
//    ModalBottomSheet(
//        onDismissRequest = onDismiss,
//        sheetState = sheetState
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(24.dp)
//        ) {
//            Text(
//                text = "Edit Preferences",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Destination Type Dropdown
//            var expandedDestination by remember { mutableStateOf(false) }
//            val destinationTypes =
//                listOf("Beach", "Mountain", "Historical", "City", "Adventure")
//
//            Text("Favorite Destination Type", fontWeight = FontWeight.Medium)
//            Spacer(modifier = Modifier.height(8.dp))
//            ExposedDropdownMenuBox(
//                expanded = expandedDestination,
//                onExpandedChange = { expandedDestination = it }
//            ) {
//                OutlinedTextField(
//                    value = destinationType,
//                    onValueChange = {},
//                    readOnly = true,
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDestination) },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .menuAnchor()
//                )
//                ExposedDropdownMenu(
//                    expanded = expandedDestination,
//                    onDismissRequest = { expandedDestination = false }
//                ) {
//                    destinationTypes.forEach { type ->
//                        DropdownMenuItem(
//                            text = { Text(type) },
//                            onClick = {
//                                destinationType = type
//                                expandedDestination = false
//                            }
//                        )
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Currency Dropdown
//            var expandedCurrency by remember { mutableStateOf(false) }
//            val currencies = listOf("USD", "EUR", "GBP", "INR", "JPY", "AUD")
//
//            Text("Currency", fontWeight = FontWeight.Medium)
//            Spacer(modifier = Modifier.height(8.dp))
//            ExposedDropdownMenuBox(
//                expanded = expandedCurrency,
//                onExpandedChange = { expandedCurrency = it }
//            ) {
//                OutlinedTextField(
//                    value = currency,
//                    onValueChange = {},
//                    readOnly = true,
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCurrency) },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .menuAnchor()
//                )
//                ExposedDropdownMenu(
//                    expanded = expandedCurrency,
//                    onDismissRequest = { expandedCurrency = false }
//                ) {
//                    currencies.forEach { curr ->
//                        DropdownMenuItem(
//                            text = { Text(curr) },
//                            onClick = {
//                                currency = curr
//                                expandedCurrency = false
//                            }
//                        )
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Save Button
//            Button(
//                onClick = {
//                    onSave(
//                        UserPreferences(
//                            favoriteDestinationType = destinationType,
//                            currency = currency,
//                            notificationEnabled = notificationsEnabled,
//                            emailUpdatesEnabled = emailUpdatesEnabled
//                        )
//                    )
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp),
//                shape = RoundedCornerShape(12.dp)
//            ) {
//                Text("Save Changes", fontSize = 16.sp)
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//    }
//}
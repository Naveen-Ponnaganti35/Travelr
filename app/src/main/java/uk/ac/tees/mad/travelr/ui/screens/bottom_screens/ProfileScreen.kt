package uk.ac.tees.mad.travelr.ui.screens.bottom_screens

import android.Manifest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import uk.ac.tees.mad.travelr.data.models.user.UserPreferences
import uk.ac.tees.mad.travelr.notification.NotificationHelper
import uk.ac.tees.mad.travelr.notification.NotificationScheduler
import uk.ac.tees.mad.travelr.viewmodels.ProfileScreenViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    logOutUser: () -> Unit,
    deleteUser: () -> Unit,
) {
    val userProfile by viewModel.currentUser.collectAsStateWithLifecycle()
    var showEditDialog by remember { mutableStateOf(false) }
    var showPreferencesSheet by remember { mutableStateOf(false) }
    var editField by remember { mutableStateOf(EditField.NAME) }
    var password by remember { mutableStateOf("") }

    Log.d("Profile", "ProfileScreen: $userProfile")

    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val context = LocalContext.current


    var showLogOutConformation by remember { mutableStateOf(false) }
    val isUploading by viewModel.isUploading.collectAsStateWithLifecycle()
//    LaunchedEffect(Unit) {
//        NotificationHelper.createNotificationChannel(context)
//        if (userProfile.preferences.notificationEnabled) {
//            if (NotificationHelper.hasNotificationPermission(context)) {
//                NotificationScheduler.scheduleDailyReminder(
//                    title = "",
//                    message = "",
//                    context = context
//                )
//            }
//            Toast.makeText(context, "notification are enabled", Toast.LENGTH_SHORT).show()
//        }
//    }


    //    LaunchedEffect(Unit) {
    //        viewModel.fetchCurrentUser()
    //    }

    // Initialize notification channel once
    LaunchedEffect(Unit) {
        NotificationHelper.createNotificationChannel(context)
    }
    NotificationPermission()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .verticalScroll(rememberScrollState())
    ) {
        // Gradient Header with Profile
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF667EEA),
                            Color(0xFF764BA2)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Profile Avatar with Camera
                Box(
                    contentAlignment = Alignment.BottomEnd
                ) {
                    val context = LocalContext.current
//                    val coroutineScope = rememberCoroutineScope()

////                    Load saved image URL on start using shared prefs
//                    var uploadedImageUrl by remember {
//                        mutableStateOf(ImagePrefs.getImageUrl(context))
//                    }

                    val uploadedImageUrl = userProfile.profileImageUrl
//                    var isUploading by remember { mutableStateOf(false) }

                    // Create temp file for camera
                    val photoFile = remember {
                        File(context.cacheDir, "profile_${System.currentTimeMillis()}.jpg")
                    }
                    val photoUri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.provider",
                        photoFile
                    )


                    // Camera launcher
                    val cameraLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.TakePicture()
                    ) { success ->
                        if (success) {
                            viewModel.uploadProfileImage(
                                context,
                                photoUri
                            ) // ViewModel handles loading!
                        }
                    }

                    // Avatar Box
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(3.dp, Color(0xFF667EEA), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            isUploading -> {
                                CircularProgressIndicator(
                                    color = Color(0xFF667EEA),
                                    strokeWidth = 3.dp
                                )
                            }

                            uploadedImageUrl.isNotEmpty() -> {
//                                Image(
//                                    painter = rememberAsyncImagePainter(uploadedImageUrl),
//                                    contentDescription = "Profile Photo",
//                                    modifier = Modifier
//                                        .size(120.dp)
//                                        .clip(CircleShape),
//                                    contentScale = ContentScale.Crop
//                                )

                                SubcomposeAsyncImage(
                                    model = uploadedImageUrl,
                                    contentDescription = "Profile Photo",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop,
                                    loading = {
                                        // Show spinner while image loads from Cloudinary
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator(
                                                color = Color(0xFF667EEA),
                                                strokeWidth = 3.dp,
                                                modifier = Modifier.size(40.dp)
                                            )
                                        }
                                    },
                                    error = {
                                        // Show icon if image fails to load
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Error",
                                            modifier = Modifier.size(48.dp),
                                            tint = Color(0xFF667EEA)
                                        )
                                    }
                                )
                            }

                            else -> {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Add Photo",
                                    modifier = Modifier.size(48.dp),
                                    tint = Color(0xFF667EEA)
                                )
                            }
                        }
                    }

                    // Camera badge icon
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF10B981))
                            .clickable { cameraLauncher.launch(photoUri) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Change Photo",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }


                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = userProfile.fullName.ifEmpty { "No Name" },
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = userProfile.email.ifEmpty { "No Email" },
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(24.dp))

            // Personal Info Section
            Text(
                text = "Personal Information",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748)
            )

            Spacer(modifier = Modifier.height(12.dp))

            EditableInfoItem(
                icon = Icons.Default.Person,
                title = "Full Name",
                value = userProfile.fullName.ifEmpty { "Not set" },
                onClick = {
                    editField = EditField.NAME
                    showEditDialog = true
                }
            )

            //            EditableInfoItem(
            //                icon = Icons.Default.Email,
            //                title = "Email",
            //                value = userProfile.email.ifEmpty { "Not set" },
            //                onClick = {
            //                    editField = EditField.EMAIL
            //                    showEditDialog = true
            //                }
            //            )

            Spacer(modifier = Modifier.height(24.dp))

            // Preferences Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Travel Preferences",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D3748)
                )
                TextButton(onClick = { showPreferencesSheet = true }) {
                    Text("Edit All")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            PreferenceItem(
                icon = Icons.Default.LocationOn,
                title = "Favorite Destination",
                value = userProfile.preferences.favoriteDestinationType
            )

            PreferenceItem(
                icon = Icons.Default.AccountBalanceWallet,
                title = "Currency",
                value = userProfile.preferences.currency
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Settings Section
            Text(
                text = "Settings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748)
            )

            Spacer(modifier = Modifier.height(12.dp))

            SwitchSettingItem(
                icon = Icons.Default.Notifications,
                title = "Push Notifications",
                isEnabled = userProfile.preferences.notificationEnabled,
                onToggle = { enabled ->
                    //enabled will show the notification
                    if (enabled) {
                        // if permission are there
                        if (NotificationHelper.hasNotificationPermission(context)) {
                            // Schedule daily reminder
                            NotificationScheduler.scheduleDailyReminder(
                                title = "",
                                message = "",
                                context = context
                            )
                            Toast.makeText(context, "Notifications enabled", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            // no permission is there
                            Toast.makeText(
                                context,
                                "Please grant notification permission",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@SwitchSettingItem
                        }
                    } else {
                        // Cancel daily reminder
                        NotificationScheduler.cancelNotification(context, 1001)
                        Toast.makeText(context, "Notifications disabled", Toast.LENGTH_SHORT)
                            .show()
                    }
                    val updatedPreferences = userProfile.preferences.copy(
                        notificationEnabled = enabled
                    )
                    val updatedProfile = userProfile.copy(preferences = updatedPreferences)
                    viewModel.updateUser(updatedProfile)
                }
            )

            SwitchSettingItem(
                icon = Icons.Default.Email,
                title = "Email Updates",
                isEnabled = userProfile.preferences.emailUpdatesEnabled,
                onToggle = { enabled ->
                    val updatedPreferences = userProfile.preferences.copy(
                        emailUpdatesEnabled = enabled
                    )
                    val updatedProfile = userProfile.copy(preferences = updatedPreferences)
                    viewModel.updateUser(updatedProfile)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))



            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    if (NotificationHelper.hasNotificationPermission(context)) {
//                        // Show immediate notification
//                        NotificationScheduler.showImmediateNotification(
//                            context = context,
//
//                        )


//                         schedule for 10 sec or time
                        NotificationScheduler.scheduleNotification(
                            context = context,
                            title = "Travelr Reminder",
                            message = "Start your journey now",
                            delayInSeconds = 5, // 10 seconds delay
                            notificationId = 9999
                        )
                        Toast.makeText(context, "Notification sent!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Please enable notifications first",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Test Notification", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))


            // log out user Button ->show the dialog
            OutlinedButton(
                onClick = {
//                    // log out the user
//                    logOutUser()

                    showLogOutConformation = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFEF4444)
                ),
                border = BorderStroke(1.5.dp, Color(0xFFEF4444))
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(12.dp))


            Button(
                onClick = {
                    //                    deleteUser()
                    showDeleteConfirmation = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF991B1B)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Delete Account", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }


            Spacer(modifier = Modifier.height(16.dp))
        }
    }


// log out dialog for conformation

    if (showLogOutConformation) {
        LogOutDialog(
            onConfirm = {
                showLogOutConformation = false
                logOutUser()
                Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
            },
            onDismiss = {
                showLogOutConformation = false
            }
        )
    }

// delete account for conformation
    if (showDeleteConfirmation) {
        DeleteAccountDialog(
            onConfirm = {
                //                if(){
                viewModel.deleteUserAccountPermanently(
                    onSuccess = {
                        //                        logOutUser() // Navigate to sign-in screen
                        deleteUser()
                        Toast.makeText(
                            context,
                            "Account deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                    },
                    onError = { error ->
                        Toast.makeText(
                            context,
                            error,
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    password = password
                )
                password = ""
                showDeleteConfirmation = false
            },
            password = password,
            onValueChange = {
                password = it
            },
            onDismiss = {
                password = ""
                showDeleteConfirmation = false
            }
        )
    }

// Edit Dialog
    if (showEditDialog) {
        EditFieldDialog(
            field = editField,
            currentValue = when (editField) {
                EditField.NAME -> userProfile.fullName
                EditField.EMAIL -> userProfile.email
            },
            onDismiss = { showEditDialog = false },
            onSave = { newValue ->
                val updatedProfile = when (editField) {
                    EditField.NAME -> userProfile.copy(fullName = newValue)
                    EditField.EMAIL -> userProfile.copy(email = newValue)
                }
                viewModel.updateUser(updatedProfile)
                showEditDialog = false
            }
        )
    }

// Preferences Bottom Sheet
    if (showPreferencesSheet) {
        PreferencesBottomSheet(
            preferences = userProfile.preferences,
            onDismiss = { showPreferencesSheet = false },
            onSave = { updatedPreferences ->
                val updatedProfile = userProfile.copy(preferences = updatedPreferences)
                viewModel.updateUser(updatedProfile)
                showPreferencesSheet = false
            }
        )
    }
}


@Composable
fun LogOutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = null,
                tint = Color(0xFF3B82F6),
                modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text(
                text = "Log Out?",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E3A8A),
                fontSize = 20.sp
            )
        },
        text = {
            Text(
                text = "Are you sure you want to log out ?",
                color = Color(0xFF374151),
                fontSize = 15.sp
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3B82F6)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Yes, Log Out")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color(0xFF6B7280))
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun LogOutDialogPreview() {
    LogOutDialog(
        {}, {}
    )
}


@Composable
fun DeleteAccountDialog(
    password: String,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = Color(0xFF991B1B),
                modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text(
                text = "Delete Account?",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF991B1B)
            )
        },
        text = {
            Column {
                Text(
                    text = "This action cannot be undone. All your data will be permanently deleted:",
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("• Your profile information", fontSize = 14.sp)
                Text("• All saved itineraries", fontSize = 14.sp)
                Text("• Your account access", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Are you absolutely sure?",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF991B1B)
                )
                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = onValueChange,
                    label = { Text("Password") },
                    placeholder = { Text("Enter your password") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                )

            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF991B1B)
                )
            ) {
                Text("Yes, Delete My Account")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color(0xFF6B7280))
            }
        }
    )
}


@Composable
fun EditableInfoItem(
    icon: ImageVector,
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFEBF4FF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF667EEA),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    color = Color(0xFF718096)
                )
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2D3748)
                )
            }

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color(0xFF667EEA),
                modifier = Modifier.size(20.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun SwitchSettingItem(
    icon: ImageVector,
    title: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFEBF4FF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF667EEA),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2D3748),
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = isEnabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF10B981),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFE5E7EB)
                )
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFieldDialog(
    field: EditField,
    currentValue: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var textValue by remember { mutableStateOf(currentValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Edit ${field.displayName}",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            OutlinedTextField(
                value = textValue,
                onValueChange = { textValue = it },
                label = { Text(field.displayName) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(textValue) },
                enabled = textValue.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

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
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Edit Preferences",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Destination Type Dropdown
            var expandedDestination by remember { mutableStateOf(false) }
            val destinationTypes =
                listOf("Beach", "Mountain", "Historical", "City", "Adventure")

            Text("Favorite Destination Type", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = expandedDestination,
                onExpandedChange = { expandedDestination = it }
            ) {
                OutlinedTextField(
                    value = destinationType,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDestination) },
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

            Text("Currency", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = expandedCurrency,
                onExpandedChange = { expandedCurrency = it }
            ) {
                OutlinedTextField(
                    value = currency,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCurrency) },
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

@Composable
fun PreferenceItem(
    icon: ImageVector,
    title: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFEBF4FF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF667EEA),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    color = Color(0xFF718096)
                )
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2D3748)
                )
            }

//            Icon(
//                imageVector = Icons.Default.KeyboardArrowRight,
//                contentDescription = null,
//                tint = Color(0xFF718096)
//            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}


enum class EditField(val displayName: String) {
    NAME("Name"),
    EMAIL("Email")
}

@Composable
fun NotificationPermission(
    onPermissionGranted: () -> Unit = {}
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            onPermissionGranted()
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationHelper.hasNotificationPermission(context)) {
                showDialog = true
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Enable Notifications") },
            text = { Text("Get reminders about your trips and travel updates.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                ) {
                    Text("Allow")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Skip")
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(viewModel = hiltViewModel(), logOutUser = {}, deleteUser = {})
}
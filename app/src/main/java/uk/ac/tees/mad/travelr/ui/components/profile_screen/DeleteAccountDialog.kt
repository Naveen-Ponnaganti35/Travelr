package uk.ac.tees.mad.travelr.ui.components.profile_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text(
                text = "Delete Account?",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Column {
                Text(
                    text = "This action cannot be undone. All your data will be permanently deleted:",
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "• Your profile information",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "• All saved itineraries",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "• Your account access",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Are you absolutely sure?",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
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
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Yes, Delete My Account")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    )
}






//@Composable
//fun DeleteAccountDialog(
//    password: String,
//    onValueChange: (String) -> Unit,
//    onConfirm: () -> Unit,
//    onDismiss: () -> Unit
//) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        icon = {
//            Icon(
//                imageVector = Icons.Default.Warning,
//                contentDescription = null,
//                tint = Color(0xFF991B1B),
//                modifier = Modifier.size(48.dp)
//            )
//        },
//        title = {
//            Text(
//                text = "Delete Account?",
//                fontWeight = FontWeight.Bold,
//                color = Color(0xFF991B1B)
//            )
//        },
//        text = {
//            Column {
//                Text(
//                    text = "This action cannot be undone. All your data will be permanently deleted:",
//                    fontWeight = FontWeight.Medium
//                )
//                Spacer(modifier = Modifier.height(12.dp))
//                Text("• Your profile information", fontSize = 14.sp)
//                Text("• All saved itineraries", fontSize = 14.sp)
//                Text("• Your account access", fontSize = 14.sp)
//                Spacer(modifier = Modifier.height(12.dp))
//                Text(
//                    text = "Are you absolutely sure?",
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF991B1B)
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//
//                OutlinedTextField(
//                    value = password,
//                    onValueChange = onValueChange,
//                    label = { Text("Password") },
//                    placeholder = { Text("Enter your password") },
//                    singleLine = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                    shape = RoundedCornerShape(8.dp),
//                )
//
//            }
//        },
//        confirmButton = {
//            Button(
//                onClick = onConfirm,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFF991B1B)
//                )
//            ) {
//                Text("Yes, Delete My Account")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel", color = Color(0xFF6B7280))
//            }
//        }
//    )
//}
package uk.ac.tees.mad.travelr.ui.components.profile_screen

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text(
                text = "Log Out?",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp
            )
        },
        text = {
            Text(
                text = "Are you sure you want to log out?",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 15.sp
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Yes, Log Out")
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
//fun LogOutDialog(
//    onConfirm: () -> Unit,
//    onDismiss: () -> Unit
//) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        icon = {
//            Icon(
//                imageVector = Icons.Default.ExitToApp,
//                contentDescription = null,
//                tint = Color(0xFF3B82F6),
//                modifier = Modifier.size(48.dp)
//            )
//        },
//        title = {
//            Text(
//                text = "Log Out?",
//                fontWeight = FontWeight.Bold,
//                color = Color(0xFF1E3A8A),
//                fontSize = 20.sp
//            )
//        },
//        text = {
//            Text(
//                text = "Are you sure you want to log out ?",
//                color = Color(0xFF374151),
//                fontSize = 15.sp
//            )
//        },
//        confirmButton = {
//            Button(
//                onClick = onConfirm,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFF3B82F6)
//                ),
//                shape = RoundedCornerShape(10.dp)
//            ) {
//                Text("Yes, Log Out")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel", color = Color(0xFF6B7280))
//            }
//        }
//    )
//}
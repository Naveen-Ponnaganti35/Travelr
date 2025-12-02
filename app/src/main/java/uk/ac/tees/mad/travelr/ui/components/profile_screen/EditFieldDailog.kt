package uk.ac.tees.mad.travelr.ui.components.profile_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import uk.ac.tees.mad.travelr.ui.screens.bottom_screens.EditField

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
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
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




//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun EditFieldDialog(
//    field: EditField,
//    currentValue: String,
//    onDismiss: () -> Unit,
//    onSave: (String) -> Unit
//) {
//    var textValue by remember { mutableStateOf(currentValue) }
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = {
//            Text(
//                text = "Edit ${field.displayName}",
//                fontWeight = FontWeight.Bold
//            )
//        },
//        text = {
//            OutlinedTextField(
//                value = textValue,
//                onValueChange = { textValue = it },
//                label = { Text(field.displayName) },
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth()
//            )
//        },
//        confirmButton = {
//            TextButton(
//                onClick = { onSave(textValue) },
//                enabled = textValue.isNotBlank()
//            ) {
//                Text("Save")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel")
//            }
//        }
//    )
//}
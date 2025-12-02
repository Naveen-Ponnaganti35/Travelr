package uk.ac.tees.mad.travelr.ui.components.profile_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
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
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}


//@Composable
//fun EditableInfoItem(
//    icon: ImageVector,
//    title: String,
//    value: String,
//    onClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable(onClick = onClick),
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(40.dp)
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(Color(0xFFEBF4FF)),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = icon,
//                    contentDescription = null,
//                    tint = Color(0xFF667EEA),
//                    modifier = Modifier.size(20.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.width(16.dp))
//
//            Column(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = title,
//                    fontSize = 14.sp,
//                    color = Color(0xFF718096)
//                )
//                Text(
//                    text = value,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    color = Color(0xFF2D3748)
//                )
//            }
//
//            Icon(
//                imageVector = Icons.Default.Edit,
//                contentDescription = "Edit",
//                tint = Color(0xFF667EEA),
//                modifier = Modifier.size(20.dp)
//            )
//        }
//    }
//
//    Spacer(modifier = Modifier.height(8.dp))
//}
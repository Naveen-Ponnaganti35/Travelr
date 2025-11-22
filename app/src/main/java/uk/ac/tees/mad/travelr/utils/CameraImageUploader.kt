package uk.ac.tees.mad.travelr.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File


//@Composable
//fun CameraImageUploader(
//    modifier: Modifier = Modifier,
//    onImageUploaded: (String) -> Unit
//) {
//    val context = LocalContext.current
//    val imageUri = remember { mutableStateOf<Uri?>(null) }
//    var uploading by remember { mutableStateOf(false) }
//
//    // temporary file
//    val photoUri = remember {
//        val file = File(context.cacheDir, "camera_photo_${System.currentTimeMillis()}.jpg")
//        FileProvider.getUriForFile(
//            context,
//            "${context.packageName}.provider",
//            file
//        )
//    }
//
//    // open camera
//    val cameraLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicture()
//    ) { success ->
//        if (success) {
//            imageUri.value = photoUri
//            uploading = true
//            uploadImageToCloudinary(context, photoUri) { imageUrl ->
//                uploading = false
//                if (imageUrl != null) {
//                    onImageUploaded(imageUrl)
//                    Toast.makeText(context, "Uploaded!", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    // UI
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = modifier
//            .size(120.dp)
//            .clip(CircleShape)
//            .border(2.dp, Color.Gray, CircleShape)
//            .clickable { cameraLauncher.launch(photoUri) }
//    ) {
//        if (imageUri.value != null) {
//            Image(
//                painter = rememberAsyncImagePainter(imageUri.value),
//                contentDescription = null,
//                modifier = Modifier.size(120.dp)
//            )
//        } else {
//            Text("📸", style = MaterialTheme.typography.headlineMedium)
//        }
//
//        if (uploading) {
//            CircularProgressIndicator(modifier = Modifier.size(40.dp))
//        }
//    }
//}
//
//// --- Cloudinary upload helper ---
//fun uploadImageToCloudinary(
//    context: Context,
//    imageUri: Uri,
//    onResult: (String?) -> Unit
//) {
//    // ✅ Demo Cloudinary values (already public, works instantly)
//    val cloudName = "dzyliedn1"
//    val uploadPreset = "unsigned_preset"
//
//    val inputStream = context.contentResolver.openInputStream(imageUri) ?: return onResult(null)
//    val bytes = inputStream.readBytes()
//
//    val requestBody = MultipartBody.Builder()
//        .setType(MultipartBody.FORM)
//        .addFormDataPart(
//            "file", "photo.jpg",
//            RequestBody.create("image/*".toMediaTypeOrNull(), bytes)
//        )
//        .addFormDataPart("upload_preset", uploadPreset)
//        .build()
//
//    val request = Request.Builder()
//        .url("https://api.cloudinary.com/v1_1/$cloudName/image/upload")
//        .post(requestBody)
//        .build()
//
//    val client = OkHttpClient()
//
//    Thread {
//        try {
//            val response = client.newCall(request).execute()
//            val responseBody = response.body?.string()
//            val json = JSONObject(responseBody ?: "{}")
//            val imageUrl = json.optString("secure_url", null)
//            onResult(imageUrl)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            onResult(null)
//        }
//    }.start()
//}




// helps uploading image in cloudinary
object CloudinaryUploader {

    // ✅ Replace with YOUR credentials
    private const val CLOUD_NAME = "dzyliedn1"
    private const val UPLOAD_PRESET = "unsigned_preset"

    suspend fun uploadImage(context: Context, imageUri: Uri): String? = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(imageUri) ?: return@withContext null
            val bytes = inputStream.readBytes()
            inputStream.close()

            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file",
                    "profile.jpg",
                    RequestBody.create("image/*".toMediaTypeOrNull(), bytes)
                )
                .addFormDataPart("upload_preset", UPLOAD_PRESET)
                .build()

            val request = Request.Builder()
                .url("https://api.cloudinary.com/v1_1/$CLOUD_NAME/image/upload")
                .post(requestBody)
                .build()

            val response = OkHttpClient().newCall(request).execute()

            if (response.isSuccessful) {
                val json = JSONObject(response.body?.string() ?: "{}")
                json.optString("secure_url", null)
            } else {
                // upload fail
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}



object ImagePrefs {
    // storing data in shared prefs
    private const val PREFS_NAME = "travelr_prefs"
    private const val KEY_PROFILE_IMAGE = "profile_image_url"

    fun saveImageUrl(context: Context, url: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_PROFILE_IMAGE, url)
            .apply()
    }

    fun getImageUrl(context: Context): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_PROFILE_IMAGE, null)
    }
}


// user takes photo-> save it to cloudinary ->save it url locally and then ->we get the image after the app closer also


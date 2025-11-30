package uk.ac.tees.mad.travelr.utils

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject


// helps uploading image in cloudinary
object CloudinaryUploader {
    private const val CLOUD_NAME = "dzyliedn1"
    private const val UPLOAD_PRESET = "unsigned_preset"

    suspend fun uploadImage(context: Context, imageUri: Uri): String? =
        withContext(Dispatchers.IO) {
            try {

                // will read the image bytes first
                val inputStream =
                    context.contentResolver.openInputStream(imageUri) ?: return@withContext null
                val bytes = inputStream.readBytes()
                inputStream.close()

                // build the request
                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "file",
                        "profile.jpg",
                        RequestBody.create("image/*".toMediaTypeOrNull(), bytes)
                    )
                    .addFormDataPart("upload_preset", UPLOAD_PRESET)
                    .build()

                // send to cloudinary

                val request = Request.Builder()
                    .url("https://api.cloudinary.com/v1_1/$CLOUD_NAME/image/upload")
                    .post(requestBody)
                    .build()

                val response = OkHttpClient().newCall(request).execute()


//            extract url from the response
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






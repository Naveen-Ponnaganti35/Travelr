package uk.ac.tees.mad.travelr.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit


// helps uploading image in cloudinary
object CloudinaryUploader {

    private const val CLOUD_NAME = "di6bxyow6"
    private const val UPLOAD_PRESET = "unsigned_preset"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun uploadImage(
        context: Context,
        imageUri: Uri
    ): String = withContext(Dispatchers.IO) {

        ensureActive()

        val imageBytes = ImageCompressor.compress(context, imageUri)

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                "profile.jpg",
                imageBytes.toRequestBody("image/jpeg".toMediaType())
            )
            .addFormDataPart("upload_preset", UPLOAD_PRESET)
            .build()

        val request = Request.Builder()
            .url("https://api.cloudinary.com/v1_1/$CLOUD_NAME/image/upload")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            val body = response.body?.string()

            if (!response.isSuccessful || body == null) {
                throw IOException(
                    "Cloudinary upload failed: ${response.code} $body"
                )
            }

            val json = JSONObject(body)
            json.getString("secure_url")
        }
    }
}




object ImageCompressor {

    fun compress(
        context: Context,
        uri: Uri,
        maxSize: Int = 1024,
        quality: Int = 80
    ): ByteArray {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Cannot open image")

        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        val ratio = bitmap.width.toFloat() / bitmap.height.toFloat()

        val (width, height) = if (bitmap.width > bitmap.height) {
            maxSize to (maxSize / ratio).toInt()
        } else {
            (maxSize * ratio).toInt() to maxSize
        }

        val resizedBitmap =
            Bitmap.createScaledBitmap(bitmap, width, height, true)

        val outputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

        bitmap.recycle()
        resizedBitmap.recycle()

        return outputStream.toByteArray()
    }
}



package com.example.pms.viewmodel.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

object ImageHelper {

    fun uriToFile(context: Context, uri: Uri): File {
        val file = File(context.cacheDir, "temp.jpg")
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.use { input ->
            val outputStream = FileOutputStream(file)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // Adjust buffer size as needed
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                }
                output.flush()
            }
        }
        return file
    }

    //   @SuppressLint("Recycle")
//       fun uriToMultipart(context: Context, uri: Uri, partNameKey: String): MultipartBody.Part {
//        val inputStream = context.contentResolver.openInputStream(uri)
//        val requestBody = inputStream?.readBytes()?.toRequestBody(MultipartBody.FORM)
//        return MultipartBody.Part.createFormData(partNameKey, "image.jpg", requestBody!!)
//    }

    @SuppressLint("Recycle")
    fun uriToMultipart(context: Context, uri: Uri, partNameKey: String): MultipartBody.Part {
        val inputStream = context.contentResolver.openInputStream(uri)
        val displayName = getDisplayName(context, uri) ?: "image.jpg"
        val requestBody = inputStream?.readBytes()?.toRequestBody(MultipartBody.FORM)
        return MultipartBody.Part.createFormData(partNameKey, displayName, requestBody!!)
    }

    @SuppressLint("Range")
    private fun getDisplayName(context: Context, uri: Uri): String? {
        var displayName: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
        return displayName
    }



    fun saveBitmapAsPng(context: Context, bitmap: Bitmap, displayName: String): Uri? {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
        val externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val resolver = context.contentResolver
        var outputStream: OutputStream? = null
        var imageUri: Uri? = null
        try {
            val insertUri = resolver.insert(externalUri, values)
            if (insertUri != null) {
                outputStream = resolver.openOutputStream(insertUri)
                if (outputStream != null && bitmap.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        outputStream
                    )
                ) {
                    imageUri = insertUri
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return imageUri
    }


}
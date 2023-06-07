package com.example.pms.viewmodel.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

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


    @SuppressLint("Recycle")
    fun uriToMultipart(context: Context, uri: Uri, partNameKey: String): MultipartBody.Part {
            val inputStream = context.contentResolver.openInputStream(uri)
            val requestBody = inputStream?.readBytes()?.toRequestBody(MultipartBody.FORM)
            return MultipartBody.Part.createFormData(partNameKey, "image.jpg", requestBody!!)
    }

}
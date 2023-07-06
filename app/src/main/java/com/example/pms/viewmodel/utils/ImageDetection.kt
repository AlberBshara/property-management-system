package com.example.pms.viewmodel.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ImageDetection {

    /**
     * @param listOfImages [Uri]
     * @return the indices of the images from the given list that are not car.
     */
    suspend fun detectVehicleInImage(
        listOfImages: List<Uri>,
        context: Context,
        onResult: (List<Int>) -> Unit
    ) = withContext(Dispatchers.Default) {

        val indices: MutableList<Int> = mutableListOf()

        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

        var processedCount = 0

        listOfImages.forEachIndexed { indexOfImage, image ->

            try {
                val currentImage = InputImage.fromFilePath(context, image)
                val predicatedLabels: MutableList<String> = mutableListOf()
                var index = 0

                labeler.process(currentImage)
                    .addOnSuccessListener {
                        for (labels in it) {
                            val text = labels.text
                            //  val confidence = labels.confidence
                            predicatedLabels.add(index, text)
                            index++
                        }

                        var isVehicle = false
                        for (i in predicatedLabels) {
                            if (i.equals("car", ignoreCase = true)
                                || i.equals("vehicle", ignoreCase = true)
                            ) {
                                isVehicle = true
                                break
                            } else {
                                isVehicle = false
                            }
                        }
                        if (!isVehicle) {
                            indices.add(indexOfImage)
                        }
                        processedCount++
                        if (processedCount == listOfImages.size) {
                            onResult(indices)
                        }
                        Log.d(
                            "Image Detection", "detectVehicleInImage: $indexOfImage :" +
                                    "$predicatedLabels" +
                                    "counter $processedCount"
                        )
                    }
                    .addOnFailureListener {
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
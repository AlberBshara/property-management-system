package com.example.pms.viewmodel.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ImageDetectionForEstates {

    /**
     * @param listOfImages [Uri]
     * @return the indices of the images from the given list that are not car.
     */
    suspend fun detectEstateInImage(
        listOfImages: List<Uri>,
        context: Context,
        onResult: (List<Int>) -> Unit
    ) = withContext(Dispatchers.Default) {

        val indexesOfFakeImages: MutableList<Int> = mutableListOf()

        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
//  var to calculate the all indexes if equals to the sizeOfList
        var processedCount = 0

        listOfImages.forEachIndexed { indexOfImage, image ->

            try {
                val currentImage = InputImage.fromFilePath(context, image)

                data class Labels(
                    val text: String,
                    val confidence: Float
                )

                //list of the name that the IT will predicate
                val predicatedLabels: MutableList<Labels> = mutableListOf()




                labeler.process(currentImage)

                    .addOnSuccessListener {
                        for (labels in it) {
                            val text = labels.text
                            val confidence = labels.confidence
                            predicatedLabels.add(Labels(text = text, confidence = confidence))
                            Log.d("lplp", labels.toString() + "  ${labels.confidence}")

                        }

                        var isEstate = false
                        for (i in predicatedLabels) {
                            if (
                                (i.text.equals(
                                    "room",
                                    ignoreCase = true
                                ) || i.text.equals(
                                    "building",
                                    ignoreCase = true
                                ) || i.text.equals("plant", ignoreCase = true))
                                &&
                                (i.confidence > 0.7)
                            ) {
                                isEstate = true
                                break
                            }
                        }
                        if (!isEstate) {
                            indexesOfFakeImages.add(indexOfImage)
                        }
                        processedCount++

                        if (processedCount == listOfImages.size) {
                            onResult(indexesOfFakeImages)
                        }

                    }


                    .addOnFailureListener {

                    }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
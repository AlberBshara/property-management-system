package com.example.pms.viewmodel.presentation_vm.estates_vm.publish_estate_vm

import android.content.Context
import android.net.Uri


sealed class PublishEstateEvents {

    data class OnGovernorateTypeChanged(val governorate: String) : PublishEstateEvents()

    data class OnAddressChanged(val address: String) : PublishEstateEvents()

    data class OnEstateTypeChanged(val estateType: String) : PublishEstateEvents()

    data class OnOperationTypeChanged(val operationType: String) : PublishEstateEvents()

    data class OnPriceChanged(val price: String) : PublishEstateEvents()

    data class OnSpaceChanged(val space: Float) : PublishEstateEvents()

    data class OnStatusChanged(val status: String) : PublishEstateEvents()

    data class OnBedsChanged(val beds: String) : PublishEstateEvents()
    data class OnBathsChanged(val baths: String) : PublishEstateEvents()
    data class OnGarageChanged(val garage: String) : PublishEstateEvents()
    data class OnLevelsChanged(val levels: String) : PublishEstateEvents()

    data class OnDescriptionChanged(val description: String) : PublishEstateEvents()

    data class OnDone(val context: Context) : PublishEstateEvents()

    data class OnImageIndexChanged(val index: Int) : PublishEstateEvents()
    data class OnRemoveImage(val id: Int) : PublishEstateEvents()
    data class AddNewImage(
        val updatedImagesList: List<Uri>,
        val context: Context
    ) : PublishEstateEvents()


    data class ImageDetectionCautionOk(
        val indicesList: List<Int>
    ) : PublishEstateEvents()

    data class IsLoadingChanged(val isLoading: Boolean) : PublishEstateEvents()

    object HideSnackBar : PublishEstateEvents()

    object OnDoneSuccessSendDataClicked : PublishEstateEvents()

    sealed class WifiCase : PublishEstateEvents() {
        object Confirm : WifiCase()

        object Deny : WifiCase()
    }


    object ShowLocationPermission : PublishEstateEvents()

    data class OnGetLocation(
        val context: Context
    ) : PublishEstateEvents()

}

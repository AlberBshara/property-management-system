package com.example.pms.viewmodel.presentation_vm.vehicles_vm.publish_vehicle_vm

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.graphics.GraphicsLayerScope

sealed class PublishVehicleEvents {

    data class ImageIndexChanged(
        val id: Int
    ) : PublishVehicleEvents()

    data class ImageCollapsing(
        val lazyListState: LazyListState,
        val graphicsLayer: GraphicsLayerScope
    ) : PublishVehicleEvents()

    object ShowYearDialogPicker : PublishVehicleEvents()

    data class OnBrandChanged(
        val brand: String
    ) : PublishVehicleEvents()

    data class OnListingTypeChanged(
        val listingType: String
    ) : PublishVehicleEvents()

    data class OnTransmissionChanged(
        val transmissionType: String
    ) : PublishVehicleEvents()

    data class OnSecondaryBrandChanged(
        val secondaryBrand: String
    ) : PublishVehicleEvents()

    data class OnPriceChanged(
        val price: Float
    ) : PublishVehicleEvents()

    data class OnKiloMeterChanged(
        val kilometer: Float
    ) : PublishVehicleEvents()

    data class OnGovernorateChanged(
        val governorate: String
    ) : PublishVehicleEvents()

    data class OnLocationChanged(
        val location: String
    ) : PublishVehicleEvents()

    data class OnManufactureYearChanged(
        val manufactureYear: String
    ) : PublishVehicleEvents()

    data class OnConditionChanged(
        val condition: String
    ) : PublishVehicleEvents()

    data class OnDescriptionChanged(
        val description: String
    ) : PublishVehicleEvents()

    data class OnColorChanged(
        val color: String
    ) : PublishVehicleEvents()

    data class OnFuelTypeChanged(
        val fuelType: String
    ) : PublishVehicleEvents()

    data class AddNewImage(
        val updatedImagesList: List<Uri>,
        val context: Context
    ) : PublishVehicleEvents()

    data class OnRemoveImage(
        val id: Int
    ) : PublishVehicleEvents()

    data class ImageDetectionCautionOk(
        val indicesList: List<Int>
    ) : PublishVehicleEvents()

    data class GetLocation(
        val context: Context
    ) : PublishVehicleEvents()

    data class OnDerivingForceChanged(
        val derivingForce : String
    ) : PublishVehicleEvents()

    object ShowLocationPermission : PublishVehicleEvents()

    sealed class WifiCase : PublishVehicleEvents() {
        data class Confirm(
            val context: Context
        ) : WifiCase()

        object Deny : WifiCase()
    }

    data class Submit(
        val context: Context
    ) : PublishVehicleEvents()

    object HideSnackBar : PublishVehicleEvents()

    object OnDoneClicked : PublishVehicleEvents()


}

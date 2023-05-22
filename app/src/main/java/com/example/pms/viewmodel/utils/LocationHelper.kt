package com.example.pms.viewmodel.utils

import android.Manifest
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class LocationHelper {
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermission() {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION ,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

}
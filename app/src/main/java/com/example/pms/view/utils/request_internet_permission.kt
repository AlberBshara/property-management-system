package com.example.pms.view.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState



@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestInternetPermission (
    openRequestPermission : Boolean
) {

  if (openRequestPermission){
      val wifiPermissionState = rememberPermissionState(
          permission = android.Manifest.permission.CHANGE_WIFI_STATE
      )
      if (!wifiPermissionState.status.isGranted) {
          wifiPermissionState.launchPermissionRequest()
      }
  }
}
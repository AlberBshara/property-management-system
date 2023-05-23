package com.example.pms.viewmodel.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


object LocationHelper {

    suspend fun getCurrentLocation(
        context: Context,
        onLocationListener: (Address) -> Unit
    ) = withContext(Dispatchers.Default) {

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                context.applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken =
                        CancellationTokenSource().token

                    override fun isCancellationRequested(): Boolean = false
                }
            ).addOnSuccessListener { location: Location? ->
                when (location) {
                    null -> {
                    }
                    else -> {
                        Log.d("LocationHelper ->", "getCurrentLocation: ${location.latitude}")
                        decodeLocation(context, location, onResult = { address ->
                            onLocationListener(Address(location, address))
                        })

                    }
                }
            }
        }
    }

    fun isLocationEnabled(
        context: Context
    ): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return try {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun decodeLocation(
        context: Context,
        location: Location,
        onResult: (String) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(location.latitude, location.longitude, 1) { addressList ->
                    if (addressList.isNotEmpty()) {
                        val address = addressList[0]
                        val addressAsString = address.getAddressLine(0)
                        onResult(addressAsString)
                    }
                }
            } else {
                //VERSION.SDK_INT < TIRAMISU:
                when (val addressList =
                    geocoder.getFromLocation(location.latitude, location.longitude, 1)) {
                    null -> {}
                    else -> {
                        if (addressList.isNotEmpty()) {
                            val address = addressList[0]
                            val addressAsString = address.getAddressLine(0)
                            onResult(addressAsString)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}

data class Address(
    val location: Location,
    val address: String
)

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermission(
    onGrantedLocation: (LocationHelper) -> Unit
) {

    val context = LocalContext.current

    val moveToSettings = rememberSaveable {
        mutableStateOf(false)
    }

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val locationLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {
                if (LocationHelper.isLocationEnabled(context)) {
                    onGrantedLocation(LocationHelper)
                }
                moveToSettings.value = false
            }
        )

    if (!permissionState.allPermissionsGranted) {
        LaunchedEffect(Unit) {
            permissionState.launchMultiplePermissionRequest()
        }
    }
    if (permissionState.allPermissionsGranted) {
        moveToSettings.value = true
    }

    if (moveToSettings.value) {
        LaunchedEffect(Unit) {
            if (ActivityCompat.checkSelfPermission(
                    context.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(
                    context.applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (!LocationHelper.isLocationEnabled(context)) {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    locationLauncher.launch(intent)
                } else {
                    onGrantedLocation(LocationHelper)
                    moveToSettings.value = false
                }
            }
        }
    }
}
package com.example.pms.viewmodel.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.pms.R

object InternetConnection {

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnectedToInternet(
        context: Context
    ): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }



    @RequiresApi(Build.VERSION_CODES.M)
    fun run(
        context: Context,
        connected: () -> Unit,
        unconnected: () -> Unit
    ) {
        if (isConnectedToInternet(context)) {

            connected()
        } else {
            unconnected()
        }
    }

    fun turnOnWifi(
        context: Context
    ) {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //if the version of the current device is more than android 10 (API 29):
                Toast.makeText(context,
                    context.getString(R.string.wifi_manually),
                    Toast.LENGTH_LONG).show()
            }
        }

    }

}

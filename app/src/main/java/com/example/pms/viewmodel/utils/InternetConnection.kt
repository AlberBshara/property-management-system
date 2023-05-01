package com.example.pms.viewmodel.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi

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
        if (isConnectedToInternet(context))
            connected()
        else
            unconnected()
    }

    fun turnOnWifi(
        context: Context
    ) {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled){
            wifiManager.isWifiEnabled = true
        }

    }

}

package com.thetemz.pawnder.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import com.google.android.material.snackbar.Snackbar

object Global {



    fun showMessage(rootView: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(rootView, message, length).show()
    }


     fun hasInternet(context: Context):Boolean {
      val connectivityManager =
           context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             val activeNetworks = connectivityManager.activeNetwork ?: return false
             val activeNetworkCapabilities =
                 connectivityManager.getNetworkCapabilities(activeNetworks) ?: return false
             return when {
                     activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                     activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                     activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                     activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                 else -> false

             }
         }else{
             return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
         }

     }


    fun getImageUrl(source: String?): String {
        if (source == null) return ""
           return source
        /*return if (source.startsWith("http")) {
            source
        } else
            APIFactory.BASE_URL_IMAGE + source*/
    }


}
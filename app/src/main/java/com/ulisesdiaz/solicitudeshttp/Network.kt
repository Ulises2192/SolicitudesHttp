package com.ulisesdiaz.solicitudeshttp

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

class Network {

    /**
     * Funcion que se utiliza para validar si hay una conexion a internet
     */
    companion object{
        fun hayRed(activity:AppCompatActivity):Boolean{
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return  networkInfo != null && networkInfo.isConnected
        }
    }
}
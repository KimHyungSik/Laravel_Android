package com.example.haksaapp.Networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetWorkStatus {
    fun getNetWorkStatus(context: Context): Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        if(activeNetwork != null){
            return true
        }
        return false
    }
}
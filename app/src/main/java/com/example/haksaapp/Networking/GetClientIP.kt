package com.example.haksaapp.Networking

import android.util.Log
import com.example.haksaapp.CookieController
import com.example.haksaapp.Utils.HttpUtil.BASE_URL
import com.example.haksaapp.Utils.HttpUtil.GET_IP_ADDRESS_API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import okhttp3.*
import java.io.IOException

class GetClientIP {
    val TAG = "GetClientIP_로그"
    val cookieController = CookieController()

    fun getClientIP(){
        Log.d(TAG, "GetClientIP - getClientIP()")
        GetIPCoroutine()
    }

    fun GetIPCoroutine(){
        CoroutineScope(Dispatchers.Default).async {
            Log.d(TAG, "GetClientIP - GetIPCoroutine()")
            GetIP()
        }
    }

    fun GetIP(){
        //클라이언트 생성
        val client = OkHttpClient.Builder().build()
        //요청 준비
        val req = Request.Builder().url(GET_IP_ADDRESS_API).build()
        //요청 및 응답
        client.newCall(req).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "GetClientIP - onResponse()")
                cookieController.SetCookie_DeviecInformation(BASE_URL)
            }

        })
    }
}
package com.example.haksaapp

import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import android.webkit.CookieManager

class CookieController{
    private val TAG = "CookieController_로그"
    val cookieManager = CookieManager.getInstance()
    val preferencesManager = PreferencesManager()

    //자동 로그인용으로 쿠키를 저장소에 저장
    fun MainCooie_Controller(context : Context, url : String?){
        val cookies : String? = cookieManager.getCookie(url)
        var parsing_cookies : HashMap<String, String> = hashMapOf("" to "")
        if(cookies != null){
            parsing_cookies = ParsingCookie(cookies)
            for(parsing_cookie in parsing_cookies){
                when(parsing_cookie.key){
                    "studentID_saveServer" -> preferencesManager.setString(context, "studentID_save", parsing_cookie.value)
                    "studentID_delete" -> preferencesManager.removeKey(context, "studentID_save")
                }
            }
        }
    }

    //디바이스 정보를 쿠키로 생성
    fun SetCookie_DeviecInformation (url : String?){
        cookieManager.setCookie(url, "DeviceModel="  + Build.MODEL)
        cookieManager.setCookie(url, "DeviceVersion=" +  "Android_" + Build.VERSION.RELEASE)
    }

    //쿠키 Log
    fun Print_Cookie (url : String?){
        Log.d(TAG, "CookieController - Print_Cookie()")
        val cookies : String? = cookieManager.getCookie(url)
        var parsing_cookies : HashMap<String, String>
        parsing_cookies = ParsingCookie(cookies!!)
        for(parsing_cookie in parsing_cookies.keys){
            Log.d(TAG, parsing_cookie)
        }
    }

    //자동 로그인용 쿠키 저장
    fun SetCookie_AutoLogin (context : Context, url : String?) : Boolean{
        val loginCookie = preferencesManager.getString(context, "studentID_save")
        if(loginCookie.equals("")){
            return false
        }
        cookieManager.setCookie(url, "studentID_save=" + loginCookie)
        return true
    }

    //모든 쿠키 삭제
    fun DeleteCookie_All(){
        Log.d(TAG, "CookieController - DeleteCookie_All()")
        cookieManager.removeAllCookies {  }
    }

    //쿠키를 decoed해서 HashMap으로 반환
    fun ParsingCookie (cookies : String) : HashMap<String, String>{
        val cookie_split = cookies.split(";")
        val parsing_cookies : HashMap<String, String> = hashMapOf("" to "")
        for(cookie in cookie_split){
            val Argument = cookie.split("=")
            parsing_cookies.put(Argument[0].replace(" ", ""), Argument[1].replace(" ", ""))
        }
        return parsing_cookies
    }
}
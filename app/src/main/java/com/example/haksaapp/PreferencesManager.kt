package com.example.haksaapp

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager {

    public val PREFERENCES_NAME = "haksa_App_Data"
    val DEFAULT_VALUE_STRING = ""

    private fun getPreferences (context : Context) : SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    //String 데이터 저장
    fun setString(context : Context, key : String, value : String){
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    //String 데이터 가져오기
    fun getString (context : Context, key : String) : String{
        val prefs = getPreferences(context)
        val value = prefs.getString(key, DEFAULT_VALUE_STRING)
        return value!!
    }

    fun removeKey(context : Context, key : String){
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.remove(key)
        editor.apply()
    }
}
package com.example.haksaapp

import android.content.Context
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.haksaapp.Utils.HttpUtil
import com.example.haksaapp.databinding.ActivityMainBinding

class WebViewSetting {
    private val TAG = "WebViewSetting_로그"
    public var Url : String? = HttpUtil.BASE_URL

    public fun webviweSetting(context : Context, viewBinding: ActivityMainBinding, ip : String){
        //WebView Setting
            this.Url = ip

        val cookieController = CookieController()

        viewBinding.webview.settings.apply {
            javaScriptEnabled = true                        // 자바스크립트 실행 허용
            javaScriptCanOpenWindowsAutomatically = false   // 자바스크립트에서 새창 실 행 허용
            setSupportMultipleWindows(false)                // 새 창 실행 허용
            loadWithOverviewMode = true                     // 메타 태그 허용

            useWideViewPort = true                          // 화면 사이즈 맞추기 허용
            setSupportZoom(false)                           // 화면 줌 허용
            builtInZoomControls = false                     // 화면 확대 축소 허용 여부

            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK // 브라우저 캐시 허용 여부
            domStorageEnabled = true                        // 로컬저장소 허용

            cacheMode = WebSettings.LOAD_DEFAULT            //캐시 설정
        }

        //url변경시 호출
        viewBinding.webview.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.d(TAG, "MainActivity - shouldOverrideUrlLoading()")
                cookieController.MainCooie_Controller(context ,url)
                cookieController.Print_Cookie(Url)
                Url = url
                view?.loadUrl(url)
                return true
            }
        }

        viewBinding.webview.loadUrl(Url)
    }
}
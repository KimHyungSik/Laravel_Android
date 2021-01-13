package com.example.haksaapp

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.haksaapp.Networking.NetWorkStatus
import com.example.haksaapp.Utils.CreateNotificatonChannel
import com.example.haksaapp.Utils.HttpUtil.BASE_URL
import com.example.haksaapp.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity로그"
    private var Url : String? = BASE_URL            //cafe24서버 주소
    private val cookieController = CookieController()
    private var doubleBackToExitPressedOnce = false //뒤로가기 두번 키
    private var viewBinding : ActivityMainBinding? = null
    private var webviewSet = WebViewSetting()

    //minSdkVersion 이후에 나온 API 에러 제어용
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding!!.root)

        Url = intent.getStringExtra("Url")


        //채널 생성
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //알람 채널 생성 매니저
            CreateNotificatonChannel().createNotificationChannel(getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        }

        //인터넷 연결 확인
        if(NetWorkStatus().getNetWorkStatus(this)) {
            if(cookieController.SetCookie_AutoLogin(this, Url)){
                //웹뷰 세팅
                Log.d(TAG, Url!! + "/LoginCheck")
                webviewSet.webviweSetting(this, viewBinding!!, (Url!! + "/LoginCheck").replace(" ", ""))
            }else{
                //웹뷰 세팅
                webviewSet.webviweSetting(this, viewBinding!!, Url!!)
            }
            cookieController.SetCookie_DeviecInformation(Url)
            cookieController.Print_Cookie(Url)
        }else{//인터넷 연결 실패시
            val toast = Toast.makeText(applicationContext, "인터넷 연결 실패",  Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onStop() {
        cookieController.Print_Cookie(Url)
        super.onStop()
    }

    override fun onDestroy() {
        cookieController.Print_Cookie(Url)
        cookieController.DeleteCookie_All()

        super.onDestroy()
    }

    //뒤로가기 액션 조절
    override fun onBackPressed() {
        if(webview.canGoBack() && webviewSet.Url != BASE_URL + "Main"){
            val histoty = webview.copyBackForwardList()
            webviewSet.Url = histoty.getItemAtIndex(histoty.getCurrentIndex()-1).getUrl()
            webview.goBack()
            return
        }else{
            if (doubleBackToExitPressedOnce) {
                cookieController.MainCooie_Controller(this, Url)
                cookieController.DeleteCookie_All()
                moveTaskToBack(true)                                // 태스크를 백그라운드로 이동
                finishAndRemoveTask()                                       // 액티비티 종료
                android.os.Process.killProcess(android.os.Process.myPid())  // 앱 프로세스 종료
                return
            }

            doubleBackToExitPressedOnce = true
            Toast.makeText(applicationContext, "'뒤로'버튼  한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
                .show()

            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
            return
        }
    }
}
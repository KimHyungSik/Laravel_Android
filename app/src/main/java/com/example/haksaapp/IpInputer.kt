package com.example.haksaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.example.haksaapp.databinding.ActivityIpInputerBinding

class IpInputer : AppCompatActivity() {
    var binding : ActivityIpInputerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIpInputerBinding.inflate(layoutInflater)

        setContentView(binding!!.root)

        binding!!.ipBtn.setOnClickListener {
            MoveIntent()
        }
        binding!!.mainUrl.setOnClickListener {
            MainIntent()
        }
        binding!!.ipText.setOnKeyListener(View.OnKeyListener {v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                MoveIntent()
            }
            false
        })

    }

    fun MoveIntent(){
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("Url", binding!!.ipText.text.toString())
        }
        startActivity(intent)
    }
    fun MainIntent(){
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("Url", "https://app.koreait.kr")
        }
        startActivity(intent)
    }
}
package com.example.haksaapp.Utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class CreateNotificatonChannel {
    private val TAG = "CreateNotificatonChanne - 로그"
    //Notification Channel 생성
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(notificationManager: NotificationManager){
        Log.d(TAG, "CreateNotificatonChannel - createNotificationChannel()")
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()

        val channelName = "기본"
        val channelDescrition = "Haksa App Notification Channel"

        val mChannel = NotificationChannel(NotificationChannelData.CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = channelDescrition
            enableLights(true)
            lightColor = Color.GREEN
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), audioAttributes)
            setShowBadge(false)
        }
        notificationManager.createNotificationChannel(mChannel)
    }
}
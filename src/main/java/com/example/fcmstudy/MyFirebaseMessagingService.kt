package com.example.fcmstudy

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService(){
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("mytag", message.notification?.title!!)
        Log.d("mytag", message.notification?.body!!)

        var builder = NotificationCompat.Builder(this)
            .setSmallIcon(android.R.drawable.ic_notification_clear_all) // 아이콘
            .setContentTitle(message.notification?.title!!)             // 제목
            .setContentText(message.notification?.body!!)               // 내용
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 받고 싶은 알림만 받을 수 있음(유저가 특정 채널 알림 끄기 가능)
            val channelId = "fcm_message"
            val channel = NotificationChannel(
                channelId,
                "Channel Title",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
        }

        // https://stackoverflow.com/questions/39607856/what-is-notification-id-in-android
        notificationManager.notify(1, builder.build())

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

//      token (=device token) : 단말기를 유일하게 구분할 수 있는 토큰
        Log.d("mytag", token)
    }
}
package com.doxpass.suncity.ui.theme.Notification
/*

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.doxpass.suncity.MainActivity
import com.doxpass.suncity.R

class MyNotification(var context: Context, var title: String, var msg: String ) {
    val channelID: String = "FCM100"
    val channelName: String = "FCMMessage"
    val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationBuilder: NotificationCompat.Builder

    fun FirNotification() {
        // Check if NotificationManager is null
        if (notificationManager == null) {
            Log.d("MyNotification", "NotificationManager is null")
            return
        } else {
            Log.d("MyNotification", "NotificationManager is initialized")
        }

        // Create NotificationChannel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            Log.d("MyNotification", "Notification channel created")
        }

        // Create intent and PendingIntent
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        // Log that we're creating the notification
        Log.d("MyNotification", "Creating notification...")

        // Build the notification
        notificationBuilder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.suncity)  // Ensure this icon exists
            .setContentTitle(title)
            .setContentText(msg)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Fire the notification
        notificationManager.notify(100, notificationBuilder.build())
        Log.d("MyNotification", "Notification fired")
    }

}

 */






/*
    fun FirNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        } else {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        Log.d("MyNotification", "Creating notification...")

        notificationBuilder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.mipmap.ic_launcher)  // Ensure this icon exists
            .setContentTitle(title)
            .setContentText(msg)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(100, notificationBuilder.build())
    }

 */




/*
    fun FirNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0 , intent, PendingIntent.FLAG_IMMUTABLE)
        notificationBuilder = NotificationCompat.Builder(context, channelID)
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
        notificationBuilder.addAction(R.drawable.suncity, "Open Message", pendingIntent)
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(msg)
        notificationBuilder.setAutoCancel(true)
        notificationManager.notify(100, notificationBuilder.build())
    }

 */
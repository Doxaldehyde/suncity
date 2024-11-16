package com.doxpass.suncity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FCMService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Handle the message and show notification
    remoteMessage.notification?.let { sendNotification(it.title, it.body)
        }
    }

    private fun sendNotification(title: String?, message: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)
        val channelId = "default_channel"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.suncity) // Replace with your app icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Default Channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(this).notify(0, notificationBuilder.build())
        }
    }




/*
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // Update server
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Respond to received messages
    }

 */
/*
import android.util.Log
import com.doxpass.suncity.ui.theme.Notification.MyNotification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage




class FCMService : FirebaseMessagingService() {

    val tag: String = "FCMToken"
    override fun onNewToken(token: String) {
        Log.d(tag, "FCMToken: $token")
    }

    private fun sendTokenToFirebase(token: String) {
        // Get the currently signed-in user
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid // Get the user's unique ID

        if (userId != null) {
            // Get a reference to the database
            val database = FirebaseDatabase.getInstance().reference
            val userTokenRef = database.child("users").child(userId).child("fcmToken")

            // Store or update the token
            userTokenRef.setValue(token)
                .addOnSuccessListener {
                    Log.d(tag, "Token stored/updated successfully")
                }
                .addOnFailureListener { e ->
                    Log.e(tag, "Failed to store/update token: ${e.message}")
                }
        } else {
            Log.e(tag, "User is not authenticated, cannot send token.")
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(tag, "Message data payload: ${remoteMessage.data}")
        }

            // Check if message contains a notification payload.
            remoteMessage.notification?.let {
                Log.d(tag, "Message Notification Body: ${it.body}")
                val notish = MyNotification(applicationContext, it.title.toString(), it.body.toString())
                notish.FirNotification()
            }

            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.

    }
}

 */

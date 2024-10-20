package com.doxpass.suncity

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FCMService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // Update server
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Respond to received messages
    }
}



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

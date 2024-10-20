package com.doxpass.suncity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dox.profile.GuestDetails.Guest
import com.dox.suncity.ChatScreen.ChatViewModel
import com.doxpass.suncity.ui.theme.Notification.ChatScreen
import com.doxpass.suncity.ui.theme.Notification.ChatViewModel1
import com.doxpass.suncity.ui.theme.Notification.EnterTokenDialog


import com.doxpass.suncity.ui.theme.SunCityTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging





class MainActivity : ComponentActivity() {
    private val viewModel: ChatViewModel1 by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        requestNotificationPermission()
     //   FirebaseMessaging.getInstance().subscribeToTopic("Chat")

        installSplashScreen()
        val guestsList = mutableListOf<Guest>()

        // Request POST_NOTIFICATIONS permission for Android 13 (API level 33) and above
      //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      //      requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100)
    //    }

/*
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCMToken", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Get the FCM registration token
            val token = task.result
            Log.d("FCMToken", "FCM Token: $token")
        }
        EventBus.getDefault().register(this);

 */
        setContent {
            SunCityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = viewModel.state
                    if(state.isEnteringToken) {
                        EnterTokenDialog(
                            token = state.remoteToken,
                            onTokenChange = viewModel::onRemoteTokenChange,
                            onSubmit = viewModel::onSubmitRemoteToken
                        )
                    } else {
                        ChatScreen(
                            messageText = state.messageText,
                            onMessageSend = {
                                viewModel.sendMessage(isBroadcast = false)
                            },
                            onMessageBroadcast = {
                                viewModel.sendMessage(isBroadcast = true)
                            },
                            onMessageChange = viewModel::onMessageChange
                        )
                    }
                  //  FCMMessage()
                  //  Navigation()
                }
            }

        }
    }
    private fun requestNotificationPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if(!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }
/*
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MyMessage?) {
        // Do something
    }


    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }

 */
}


/*
@Composable
fun FCMMessage(){
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(onClick = {
            val notish = MyNotification(context, "FCM", "This is notification for FCM testing")
            notish.FirNotification()
        }) {
            androidx.compose.material3.Text(text = "Fire Notification")
        }
    }
}

 */

// dMHYzTbsTAS1t1jyUhq9nT:APA91bEou8t3MJi76vfCxHiU5_CR0dJwL_E4s5V5JG-ZBoKOJP7RlQ-fIJ88FgasDgKmdsP9wMnDjWp2ULhVYiS-tDKQ3pbkzgUZRR52KTpUguP48TJ7REMVJs19W9EZ67f4Iehm4UXT

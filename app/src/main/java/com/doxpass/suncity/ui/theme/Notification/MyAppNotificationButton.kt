package com.doxpass.suncity.ui.theme.Notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MyApp(notificationViewModel: NotificationViewModel = viewModel()) {
    val fcmToken by notificationViewModel.fcmToken
  //  val isTokenAvailable = fcmToken != null

    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
        //    Text("FCM Token: ${fcmToken ?: "Fetching..."}")

            Button(
                onClick = {
                    val message = "There is improvement, congratulations"
                    notificationViewModel.sendNotification(message)
                },
                enabled = fcmToken != null
            ) {
                Text(text = if (fcmToken != null) "Send Notification" else "Fetching Token...")
            }

            Button(
                onClick = {
                    val message = "From my second phone"
                    notificationViewModel.sendNotification(message)
                },
                enabled = fcmToken != null
            ) {
                Text(text = if (fcmToken != null) "Send Notification" else "Fetching Token...")
            }
        }
    }
}


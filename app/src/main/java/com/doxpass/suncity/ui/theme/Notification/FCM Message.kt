package com.doxpass.suncity.ui.theme.Notification







/*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

@Composable
fun FCMMessage(){
    var message = remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        OutlinedButton(onClick = {
            val notish = MyNotification(context, "Suncity Estate", "This is notification for FCM testing")
            notish.FirNotification()
        }) {
            Text(text = "Fire Notification")
        }
        AnimatedVisibility(visible = true) {
            Text(text = message.value.toString(), fontSize = 10.sp)
        }
    }
}

 */
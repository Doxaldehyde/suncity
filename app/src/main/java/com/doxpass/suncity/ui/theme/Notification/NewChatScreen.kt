package com.doxpass.suncity.ui.theme.Notification

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dox.suncity.Login.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


@Composable
fun ChatScreen4(
    viewModel: ChatViewModel1 = viewModel(), // Using viewModel here
) {
    var userAddress by remember { mutableStateOf("") }
    val auth = Firebase.auth
    val user = auth.currentUser
    val userUID = user?.uid

    LaunchedEffect(userUID) {
        if (userUID != null) {
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(userUID)
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.let {
                        userAddress = it.homeAddress

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }
    val state = viewModel.state

    // Request notification permission
    requestNotificationPermission()

    if (state.isEnteringToken) {
        EnterTokenDialog(
            token = state.remoteToken,
            onTokenChange = viewModel::onRemoteTokenChange,
            onSubmit = viewModel::onSubmitRemoteToken
        )
    } else {
        ChatScreenContent(
            messageText = state.messageText,
            onMessageSend = {
                viewModel.sendMessage(isBroadcast = false)
            },
            onMessageBroadcast = {
                viewModel.sendMessage(isBroadcast = true)
            },
            onMessageChange = viewModel::onMessageChange,
            onFireAlert = {
                // Trigger fire broadcast
                viewModel.sendFireAlert("There is fire situation in $userAddress")
            }
        )
    }
}

@Composable
fun ChatScreenContent(
    messageText: String,
    onMessageSend: () -> Unit,
    onMessageBroadcast: () -> Unit,
    onMessageChange: (String) -> Unit,
    onFireAlert: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = messageText,
            onValueChange = onMessageChange,
            placeholder = {
                Text("Enter a message")
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = onMessageSend
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send"
                )
            }
            Spacer(Modifier.width(16.dp))
            IconButton(
                onClick = onMessageBroadcast
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Broadcast"
                )
            }
            Spacer(Modifier.width(16.dp))
            IconButton(
                onClick = onFireAlert // New Fire Alert Button
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,  // Use a fire-related icon
                    contentDescription = "Fire Alert"
                )
            }
        }
    }
    // Your ChatScreen UI implementation here
}

@Composable
private fun requestNotificationPermission() {
    val context = LocalContext.current
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }
}

// android:enableOnBackInvokedCallback="true"
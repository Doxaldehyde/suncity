package com.dox.suncity.ChatScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(text = "Estate Group Chat", modifier = Modifier.padding(start = 50.dp),
                textAlign = TextAlign.Center)
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate("home_screen")
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            // Add any additional actions (buttons, icons) here
        },
        modifier = Modifier
            .zIndex(1f)
            .background(MaterialTheme.colorScheme.primary)

    )
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel, navController: NavController, modifier: Modifier = Modifier) {
    var isLoading by remember { mutableStateOf(true) }
    ChatTopAppBar(navController = navController)
    val viewModel: ChatViewModel = remember { ChatViewModel() }
    val messages = viewModel.messages
    var messageText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val messagesRef: DatabaseReference = database.getReference("messages")


    fun sendMessage(messageText: String) {
        if (messageText.isNotBlank()) {
            val user = FirebaseAuth.getInstance().currentUser
            val userUID = user?.uid ?: ""

            fetchUserFirstNameFromFirebase(userUID) { firstName ->
                val newMessageRef = messagesRef.push()
                val message = Message(messageText, isSent = true, senderName = firstName)
                newMessageRef.setValue(message)
            }
        }
    }

    LaunchedEffect(Unit) {
        messagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                viewModel.messages.clear() // Clear existing messages
                for (messageSnapshot in dataSnapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    if (message != null) {
                        viewModel.messages.add(message)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error here
                isLoading = false
            }
        })
    }
    LaunchedEffect(isLoading) {
        delay(3000) // Adjust the delay time as needed
        isLoading = false
    }
//    val receivedMessageText =
 //       "Received message content" // Replace with the actual received message content
  //  viewModel.messages.add(Message(receivedMessageText, isSent = false))
    if (isLoading) {
        // Show loading indicator
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Show chat UI when data is loaded

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Chat messages
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(messages) { message ->
                    ReceivedMessageItem(message)
                }
            }

            // Message input
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    shape = RoundedCornerShape(32.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (messageText.isNotBlank()) {
                                viewModel.messages.add(
                                    Message(
                                        messageText,
                                        isSent = true,
                                        senderName = ""
                                    )
                                )
                                sendMessage(messageText)
                                // Send message to Firebase here
                                messageText = ""
                                keyboardController?.hide()
                            }
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 8.dp)
                        .size(48.dp)
                        .clip(CircleShape)

                        .background(color = MaterialTheme.colorScheme.primary)
                ) {

                    IconButton(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                viewModel.messages.add(
                                    Message(
                                        messageText,
                                        isSent = true,
                                        senderName = ""
                                    )
                                )
                                sendMessage(messageText)
                                // Send message to Firebase here
                                messageText = ""
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send, contentDescription = "send message",
                            modifier = Modifier,
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
    // Add a placeholder received message
    LaunchedEffect(Unit) {
        val receivedMessageText = "Received message content" // Replace with the actual received message content
        viewModel.messages.add(Message(receivedMessageText, isSent = false))
    }
}

fun fetchUserFirstNameFromFirebase(userUID: String, onSuccess: (String) -> Unit) {
    val database = FirebaseDatabase.getInstance()
    val usersRef: DatabaseReference = database.getReference("users").child(userUID)
    usersRef.child("firstName").addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val firstName = dataSnapshot.getValue(String::class.java)
            onSuccess(firstName ?: "Unknown User")
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Handle database error
        }
    })
}

@Composable
fun ReceivedMessageItem(message: Message) {
    val background = if (message.isSent) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor = if (message.isSent) Color.White else Color.Black
    val horizontalArrangement = if (message.isSent) Arrangement.End else Arrangement.Start


    Card(
        modifier = Modifier

            .padding(8.dp)
            .background(background)
            .clip(RoundedCornerShape(10.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column {
            // Display received messages with different UI here
            Text(
                text = message.senderName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = horizontalArrangement){

                Text(
                    text = message.text,
                    color = textColor,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp, end = 8.dp, top = 4.dp)
                )
                Text(
                    text = getCurrentTime(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }

}

fun getCurrentTime(): String {
    val currentTime = SimpleDateFormat("HH:mm").format(Date())
    return "$currentTime"
}

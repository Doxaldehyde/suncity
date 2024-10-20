package com.dox.suncity.GuestDetails

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dox.profile.GuestDetails.Guest
import com.dox.profile.GuestDetails.GuestListViewModel
import com.dox.profile.GuestDetails.GuestViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun GuestListScreen(navController: NavController,
                    guestViewModel: GuestViewModel,
                    selectedOption: String
                    , modifier: Modifier = Modifier) {

    var selectedGuest by remember { mutableStateOf<Guest?>(null) }
    val scrollState = rememberScrollState()

    var guests by remember {
        mutableStateOf<List<Guest>>(emptyList())
    }
    val user = FirebaseAuth.getInstance().currentUser
    val userUid = user?.uid
    var isLoading by remember { mutableStateOf(true) }
    if (userUid != null) {
        val database = FirebaseDatabase.getInstance()
        val guestsRef = database.getReference("users/$userUid/guests")

        LaunchedEffect(guestsRef) {
            guestsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val guestsList = snapshot.children.mapNotNull { guestSnapshot ->
                        guestSnapshot.getValue(Guest::class.java)
                    }
                    guests = guestsList.sortedByDescending { it.currentTime }
                    isLoading = false
                    //   guests = guestsList
                }

                override fun onCancelled(error: DatabaseError) {
                    isLoading = false
                    // Handle any errors here if needed
                }
            })
        }
    }
    if (isLoading) {
        // Show loading indicator
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Text(
                text = "Guest List",
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            guests.forEach { guest ->
                GuestListCard(guest) {
                    selectedGuest = guest
                }
            }
            selectedGuest?.let { guest ->
                GuestDetailsDialog(guest) {
                    selectedGuest = null
                }
            }
        }
    }
}
@Composable
fun GuestListCard(guest: Guest, onCardClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(70.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onCardClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF0085A0)
                ), modifier = Modifier
                    .size(65.dp)
                    .padding(8.dp)
            ) {

                Text(
                    text = guest.randomNumbers.joinToString(","),
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )


            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = guest.name, modifier = Modifier,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
               Row {
                   Text(
                       text = guest.phoneNumber, modifier = Modifier,
                       color = MaterialTheme.colorScheme.secondary,
                       fontSize = 12.sp,
                       fontWeight = FontWeight.Bold
                   )
                   Spacer(modifier = Modifier.weight(1f))
                   guest.status?.let {
                       Text(text = it,
                           color = MaterialTheme.colorScheme.secondary,
                           modifier = Modifier.padding(end = 10.dp))
                   }
               }
                Text(
                    text = "Generated at: ${formatTime(guest.currentTime)}", modifier = Modifier,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold
                )
               
            }
        }
    }
}
fun formatTime(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
    val date = Date(timestamp)
    return dateFormat.format(date)
}
@Composable
fun GuestDetailsDialog(guest: Guest, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val guestListViewModel: GuestListViewModel = viewModel()
    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
            // Customize the content of your dialog based on the guest details
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .height(100.dp)
                    .background(color = Color(0xFF0085A0)) // Change background color here
                , verticalAlignment = Alignment.CenterVertically

            ) {
                TextButton(onClick = {
                    if (guest.status == "In" || guest.status == "Detain"){
                    guestListViewModel.updateGuestOut(guest, "Leaving")
                        Toast.makeText(context, "Exit approval successful", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(context, "Action not allow", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Text(text = "Exit Approval",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,)
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {
                    if (guest.status == "In" || guest.status == "Leaving") {
                        guestListViewModel.updateGuestOut(guest, "Detain")
                    }
                    else {
                        Toast.makeText(context, "Action not allow", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Text(text = "Detain",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red)
                }
            }
        }
    )
}





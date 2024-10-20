
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.dox.profile.GuestDetails.Guest
import com.dox.profile.GuestDetails.GuestViewModel
import com.doxpass.suncity.ShareViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


@Composable
fun SecurityScreen(navController: NavController, shareViewModel: ShareViewModel, guestViewModel: GuestViewModel) {

    var loading by remember { mutableStateOf(false) }
    var snackbarHostState = remember { SnackbarHostState() }

    var guests by remember {
        mutableStateOf<List<Guest>>(emptyList())
    }
    var selectedGuest by remember { mutableStateOf<Guest?>(null) }
    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")
    val context = LocalContext.current

    LaunchedEffect(usersRef) {
        // Add a listener for all users
        usersRef.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentDate = LocalDate.now() // Get the current date
                val allGuests = mutableListOf<Guest>()

                for (userSnapshot in snapshot.children) {
                    val guestsSnapshot = userSnapshot.child("guests")
                    for (guestSnapshot in guestsSnapshot.children) {
                        val guest = guestSnapshot.getValue(Guest::class.java)
                        guest?.let {
                            // Convert currentTime to LocalDate
                            val guestDate = Instant.ofEpochMilli(it.currentTime).atZone(ZoneId.systemDefault()).toLocalDate()

                            // Check if the guest date is today
                            if (guestDate == currentDate) {
                                allGuests.add(it)
                            }
                        }
                    }
                }

                guests = allGuests
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

    fun refreshData() {
        loading = true // Set loading indicator to true
        // Add a listener for all users
        usersRef.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentDate = LocalDate.now() // Get the current date
                val allGuests = mutableListOf<Guest>()

                for (userSnapshot in snapshot.children) {
                    val guestsSnapshot = userSnapshot.child("guests")
                    for (guestSnapshot in guestsSnapshot.children) {
                        val guest = guestSnapshot.getValue(Guest::class.java)
                        guest?.let {
                            // Convert currentTime to LocalDate
                            val guestDate = Instant.ofEpochMilli(it.currentTime).atZone(ZoneId.systemDefault()).toLocalDate()

                            // Check if the guest date is today
                            if (guestDate == currentDate) {
                                allGuests.add(it)
                            }
                        }
                    }
                }

                guests = allGuests
            }

            override fun onCancelled(error: DatabaseError) {
                loading = false
                // Handle database error
            }
        })
        loading = false

    }


    Column(modifier = Modifier
        .padding(16.dp)) {
        Text(
            text = "Security Department",
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Text(text = "Dependants",
            modifier = Modifier
                .clickable { navController.navigate("dependant_list") }
                .fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = {
                signOut()
                navController.navigate("login_screen")
            }) {
                Text(text = "Sign Out")
            }
            TextButton(onClick = {refreshData() }) {
                Text(text = "Refresh")
            }
        }

        LazyColumn(
            content = {
                items(
                    items = guests.chunked(4),
                    itemContent = { rowItems ->
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                            rowItems.forEach { guest ->
                                GridItem(
                                    guest = guest,
                                    onClick = { selectedGuest = guest },
                                    onCheckInClick =  {
                                    },
                                    onCheckOutClick = {

                                    }
                                )
                            }
                        }
                    }
                )
            }
        )

        selectedGuest?.let { guest ->
            Dialog(
                onDismissRequest = { selectedGuest = null },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Name: ${guest.name}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = "Contact: ${guest.phoneNumber}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        )
                    )
                    Text(
                        text = "Status: ${guest.status}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        )
                    )

                    Text(
                        text = "VehicleType: ${guest.vehicleType}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        )
                    )
                    Text(
                        text = "PlateNumber: ${guest.plateNumber}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        )
                    )
                    Text(
                        text = "Comment: ${guest.comment}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier,
                    ) {
                        TextButton(onClick = {
                            // Update the status to "In" when Check In is clicked
                          //  loading = true
                            if (guest.status == "Booked") {
                                updateGuestStatus(guest, "In")
                                Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show()}
                            else{Toast.makeText(context, "Guest Not Allow to the estate", Toast.LENGTH_LONG).show()}
                            loading = false

                        }) {
                            Text(text = "Check In",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = {
                            if (guest.status == "Leaving"){
                            updateGuestStatusOut(guest, "Out")
                                Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show()}
                            else{
                               Toast.makeText(context, "No approval to leave the estate", Toast.LENGTH_LONG).show()
                            }
                            guestViewModel.deleteGuest(guest.id ?: "")
                        }) {
                            Text(text = "Check Out",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,)
                        }
                    }


                }
            }
        }

    }
}


fun signOut() {
    FirebaseAuth.getInstance().signOut()
}

@Composable
fun GridItem(guest: Guest, onClick: () -> Unit, onCheckInClick: () -> Unit, onCheckOutClick: () -> Unit) {

    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .size(75.dp)
            .padding(8.dp)
            .clickable(onClick = onClick)
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
}

private fun updateGuestStatus(guest: Guest, newStatus: String) {
    val userUid = FirebaseAuth.getInstance().currentUser?.uid
    val database = FirebaseDatabase.getInstance()

    val guestsRef = database.getReference("users")

    val guestId = guest.id

    if (guestId != null) {
        // Query to find the user UID based on the guest ID
        guestsRef.orderByChild("guests/$guestId/id").equalTo(guestId).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (userSnapshot in dataSnapshot.children) {
                        val userUidToUpdate = userSnapshot.key
                        // Now you have the UID of the user to update
                        val guestRef = guestsRef.child("$userUidToUpdate/guests/$guestId")
                        guestRef.child("status").setValue(newStatus) { error, _ ->
                            if (error == null) {
                                // Status updated successfully
                            } else {
                                // Handle the error
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle the error
                }
            }
        )
    } else {
        // Handle the case when the guest ID is null
    }
}
private fun updateGuestStatusOut(guest: Guest, newStatus : String){
    val userUid = FirebaseAuth.getInstance().currentUser?.uid
    val database = FirebaseDatabase.getInstance()

    val guestsRef = database.getReference("users")

    val guestId = guest.id

    if (guestId != null) {
        // Query to find the user UID based on the guest ID
        guestsRef.orderByChild("guests/$guestId/id").equalTo(guestId).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (userSnapshot in dataSnapshot.children) {
                        val userUidToUpdate = userSnapshot.key
                        // Now you have the UID of the user to update
                        val guestRef = guestsRef.child("$userUidToUpdate/guests/$guestId")
                        guestRef.child("status").setValue(newStatus) { error, _ ->
                            if (error == null) {
                                // Status updated successfully
                            } else {
                                // Handle the error
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle the error
                }
            }
        )
    } else {
        // Handle the case when the guest ID is null
    }
}


/*
    LaunchedEffect(usersRef) {
        // Add a listener for all users
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allGuests = mutableListOf<Guest>()

                for (userSnapshot in snapshot.children) {
                    val guestsSnapshot = userSnapshot.child("guests")
                    for (guestSnapshot in guestsSnapshot.children) {
                        val guest = guestSnapshot.getValue(Guest::class.java)
                        guest?.let { allGuests.add(it) }
                    }
                }

                guests = allGuests
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
 */
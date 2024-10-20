package com.dox.suncity.Profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.ImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import com.dox.profile.GuestDetails.Guest
import com.dox.suncity.AddDependant.Dependant
import com.dox.suncity.AddDependant.DependentViewModel
import com.dox.suncity.Login.User
import com.doxpass.suncity.R

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


@Composable
fun ProfileScreen(navController: NavController, dependentViewModel: DependentViewModel, context: Context){
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Save the selected image URI in SharedPreferences
            val sharedPreferences = context.getSharedPreferences("UserProfilePrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit()
                .putString("user_image_uri", it.toString())
                .apply()
        }
    }

    // Get the user's profile image URI from SharedPreferences
    val sharedPreferences: SharedPreferences = remember {
        context.getSharedPreferences("UserProfilePrefs", Context.MODE_PRIVATE)
    }
    val imageUri = sharedPreferences.getString("user_image_uri", "")

    var isResidentMode by remember { mutableStateOf(true) }

    Column (modifier = Modifier
      //  .verticalScroll(scrollState)
        .padding(10.dp)) {
        Icon(
            Icons.Default.ArrowBack, contentDescription = "back arrow",
            modifier = Modifier
                .size(28.dp)
                .wrapContentWidth(align = Alignment.Start)
                .clickable { navController.navigate("home_screen") })
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "My Account", modifier = Modifier,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(15.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            ,horizontalArrangement = Arrangement.SpaceBetween) {
          Text(text = "Resident", modifier = Modifier
              .clickable {
              isResidentMode = true
          })
            Text(text = "Dependents", modifier = Modifier.clickable { isResidentMode = false })
        }
        if (isResidentMode) {
            onResident(context, launcher, imageUri)}
        else {
            onAddDependent(dependentViewModel)
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button5 {
            showDialog = true
        }
        if (showDialog) {
            DeleteAccountDialog(
                onConfirm = {
                    deleteUserAccount()
                    // After deletion, navigate user to login screen or perform other actions
                    navController.navigate("login_screen")
                },
                onDismiss = { showDialog = false }
            )
        }
    }


}

@Composable
fun DeleteAccountDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Account") },
        text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0085A0))
            ) {
                Text("Delete",
                    modifier = Modifier,
                    color = Color(0xFFEBF2F3))
            }
        },
        dismissButton = {

            TextButton(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0085A0)))
            {
                Text("Cancel",
                    modifier = Modifier,
                    color = Color(0xFFEBF2F3)
                )
            }
            Spacer(modifier = Modifier.width(100.dp))
        }
    )
}
@Composable
fun onResident(context: Context, launcher: ActivityResultLauncher<String>, imageUri: String?){
    var isLoading by remember { mutableStateOf(true) }

    Column (horizontalAlignment = Alignment.CenterHorizontally){


    ProfileImage(context, launcher, imageUri)
        ProfileName()
    }
    Spacer(modifier = Modifier.height(40.dp))
    ProfileCard()
}

@Composable
fun ProfileName(){
    // Create a state for the user's first name
    var userFirstName by remember { mutableStateOf("") }
    var userlastName by remember { mutableStateOf("") }
    var userHomeAddress by remember { mutableStateOf("") }
    var useroccupancyType by remember { mutableStateOf("") }

    // Read the user's UID from Firebase Authentication
    val auth = Firebase.auth
    val user = auth.currentUser
    val userUID = user?.uid

    // Fetch user information from Firebase Realtime Database
    LaunchedEffect(userUID) {
        if (userUID != null) {
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(userUID)
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.let {
                        userFirstName = it.firstName
                        userlastName = it.lastName
                        userHomeAddress = it.homeAddress
                        useroccupancyType = it.occupancyType

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle database error
                }
            })
        }
    }

    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
    Row (verticalAlignment = Alignment.CenterVertically){
        Text(text = userFirstName, modifier = Modifier,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = userlastName, modifier = Modifier,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold)
    }
        Text(text = userHomeAddress, modifier = Modifier,
            color = MaterialTheme.colorScheme.primaryContainer,
            fontWeight = FontWeight.Bold)
        Text(text = useroccupancyType)
    }

}
@SuppressLint("SuspiciousIndentation")
@Composable
fun ProfileImage(context: Context, launcher: ActivityResultLauncher<String>, imageUri: String?) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

                Image(
                    painter = painterResource(id = R.drawable.suncity2),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp),
                    contentScale = ContentScale.Crop
                )

        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCard(){
    val context = LocalContext.current
    var userPhoneNumber by remember { mutableStateOf("") }
    var useremailAddress by remember { mutableStateOf("") }
    var usergender by remember { mutableStateOf("") }
    var usermaritalStatus by remember { mutableStateOf("") }
    var userHomeAddress by remember { mutableStateOf("") }
    var useroccupancyType by remember { mutableStateOf("") }

    // Read the user's UID from Firebase Authentication
    val auth = Firebase.auth
    val user = auth.currentUser
    val userUID = user?.uid

    // Fetch user information from Firebase Realtime Database
    LaunchedEffect(userUID) {
        if (userUID != null) {
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(userUID)
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.let {
                        userPhoneNumber = it.phoneNumber
                        useremailAddress = it.emailAddress
                      usergender = it.gender
                      usermaritalStatus = it.maritalStatus
                        userHomeAddress = it.homeAddress
                        useroccupancyType = it.occupancyType

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle database error
                }
            })
        }
    }

  Card(shape = RoundedCornerShape(30.dp)
      ,modifier = Modifier
          .fillMaxWidth()
          .padding(5.dp),
   /*   colors = CardDefaults.cardColors(
          containerColor = Color(0xFFFFFBFF)
      )

    */
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )) {

       Column (modifier = Modifier
           .padding(10.dp)
           ){
           Text(text = "About", modifier = Modifier, fontSize = 18.sp,fontWeight = FontWeight.Bold)
           Divider(modifier = Modifier, color = Color(0xFF0085A0))
           Spacer(modifier = Modifier.height(15.dp))
           Text(text = userPhoneNumber)
           Text(text = "Phone Number", modifier = Modifier, fontWeight = FontWeight.Bold)
           Divider(modifier = Modifier, color = Color(0xFF0085A0))
           Spacer(modifier = Modifier.height(15.dp))
           Text(text = useremailAddress)
           Text(text = "Email", modifier = Modifier, fontWeight = FontWeight.Bold)
           Divider(modifier = Modifier, color = Color(0xFF0085A0))
           Spacer(modifier = Modifier.height(15.dp))
           Text(text = userHomeAddress)
           Text(text = "House Address", modifier = Modifier, fontWeight = FontWeight.Bold)
           Divider(modifier = Modifier, color = Color(0xFF0085A0))
           Spacer(modifier = Modifier.height(15.dp))
           Text(text = useroccupancyType)
           Text(text = "Occupant", modifier = Modifier, fontWeight = FontWeight.Bold)
           Spacer(modifier = Modifier.height(15.dp))

       }

    }

}


@Composable
fun onAddDependent(dependentViewModel: DependentViewModel) {
    val context = LocalContext.current
    var dependants by remember {
        mutableStateOf<List<Dependant>>(emptyList())
    }
    val user = FirebaseAuth.getInstance().currentUser
    val userUid = user?.uid

    if (userUid != null) {
        val database = FirebaseDatabase.getInstance()
        val dependantsRef = database.getReference("users/$userUid/dependants")

        LaunchedEffect(dependantsRef) {
            dependantsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dependantsList = snapshot.children.mapNotNull { dependentSnapshot ->
                        dependentSnapshot.getValue(Dependant::class.java)
                    }
                    dependants = dependantsList
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
    DependentsList(dependants,
        onItemClick = { /* Your onItemClick logic */ },
        onDeleteClick = { dependant -> deleteDependent(dependant) })

}

fun deleteDependent(dependant: Dependant) {

    val currentUser = FirebaseAuth.getInstance().currentUser
    val userUid = currentUser?.uid

    if (userUid != null) {
        val database = FirebaseDatabase.getInstance()
        val dependantsRef = database.getReference("users/$userUid/dependants")

        // Find the dependent by its ID and remove it from the database
        dependantsRef.child(dependant.dependentId).removeValue()
            .addOnSuccessListener {
            //    Toast.makeText(context, "Dependent deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
            //    Toast.makeText(context, "Failed to delete dependent: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}



@Composable
fun DependentsList(dependants: List<Dependant>,
                   onItemClick: (Dependant) -> Unit,
                   onDeleteClick: (Dependant) -> Unit) {

    val context = LocalContext.current
    var selectedDependant by remember { mutableStateOf<Dependant?>(null) }

    // Display a confirmation dialog when a dependent is clicked
    if (selectedDependant != null) {
        AlertDialog(
            onDismissRequest = { selectedDependant = null },
            title = { Text("Delete Dependent") },
            text = { Text("Are you sure you want to delete this dependent?") },
            confirmButton = {
                Button(
                    onClick = {

                        onDeleteClick(selectedDependant!!)
                        selectedDependant = null
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0085A0))
                ) {
                    Text("Delete",
                        color = Color(0xFFEBF2F3))
                }
            },
            dismissButton = {
                Button(
                    onClick = { selectedDependant = null },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0085A0))
                ) {
                    Text("Cancel",
                        color = Color(0xFFEBF2F3))
                }
                Spacer(modifier = Modifier.width(100.dp))
            }
        )
    }

    // Use LazyColumn to efficiently display a list
    LazyColumn {
        items(dependants) { dependent ->
            // You can customize the UI for each dependent item here
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .clickable { selectedDependant = dependent},
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${dependent.firstName} ${dependent.lastName}",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = dependent.relationship,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )
            }
            Divider()
        }
    }
    fun deleteDependent(dependant: Dependant) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userUid = currentUser?.uid

        if (userUid != null) {
            val database = FirebaseDatabase.getInstance()
            val dependantsRef = database.getReference("users/$userUid/dependants")

            // Find the dependent by its ID and remove it from the database
            dependantsRef.child(dependant.dependentId).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Dependent deleted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to delete dependent: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

}

@Composable
fun Button5(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF0085A0),
            contentColor = androidx.compose.material.MaterialTheme.colors.onPrimary // Adjust text color based on primary color
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(30.dp))
            .height(59.dp)
    ) {
        Text("Delete Account")
    }
}

fun deleteUserAccount() {
    val currentUser = FirebaseAuth.getInstance().currentUser

    // Check if the current user is not null
    currentUser?.let { user ->
        // Delete the user account
        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User account deleted successfully
                    println("User account deleted successfully")
                    // Optionally, navigate the user back to the login screen or perform other actions
                } else {
                    // An error occurred while trying to delete the user account
                    val error = task.exception
                    error?.let {
                        println("Error deleting user account: ${it.localizedMessage}")
                        // Optionally, show an alert to inform the user about the error
                    }
                }
            }
    } ?: run {
        // No user is currently signed in
        return
    }
}



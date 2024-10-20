package com.dox.suncity.GuestDetails
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dox.profile.GuestDetails.Guest
import com.dox.profile.GuestDetails.GuestViewModel
import com.doxpass.suncity.R
import com.doxpass.suncity.ShareViewModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun GuestScreen(guestViewModel: GuestViewModel, navController: NavController, shareViewModel: ShareViewModel){
    val user = FirebaseAuth.getInstance().currentUser
    val userUid = user?.uid
    val database = FirebaseDatabase.getInstance()
    val guestsRef = database.getReference("users/$userUid/guests")

    // Create a list to store guests retrieved from Firebase
    var firebaseGuests by remember {
        mutableStateOf<List<Guest>>(emptyList())
    }


    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var vehicleType by remember { mutableStateOf("") }
    var plateNumber by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    // Add a variable to track the selected option
    var selectedOption by remember { mutableStateOf("One-Time Visit") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)
        .verticalScroll(scrollState))
    {
        Row {
            IconButton(onClick = {
                navController.navigate("home_screen") }) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back arrow" )
            }
            Text(text = "Book a Visitor",
                modifier = Modifier.padding(start = 8.dp,top = 8.dp, bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
        }

        Text(text = "Generate a quick one-time visit Access code",
            modifier = Modifier.padding(start = 8.dp))

        RadioButton1(selectedOption = selectedOption){
            newOption ->
            selectedOption = newOption
        }
        Text(text = "Name", modifier = Modifier.padding(start = 8.dp), fontSize = 12.sp)
           editedTextField(value = name, keyboardOptions = KeyboardOptions.Default.copy(
               keyboardType = KeyboardType.Text,
               imeAction = ImeAction.Next) ,
               label = R.string.E_g_Jovi_Tega, onValueChanged = { name = it })
        Text(text = "Phone Number", modifier = Modifier.padding(start = 8.dp), fontSize = 12.sp)
           editedTextField(value = phoneNumber, keyboardOptions = KeyboardOptions.Default.copy(
               keyboardType = KeyboardType.Number,
               imeAction = ImeAction.Done) ,
               label = R.string.E_g_08152323909, onValueChanged = { phoneNumber = it })
        Text(text = stringResource(id = R.string.Vehicle_Type),modifier = Modifier.padding(start = 8.dp), fontSize = 12.sp)
        editedTextField(value = vehicleType, keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next) ,
            label = R.string.time_entry, onValueChanged = { vehicleType = it })
        Text(text = stringResource(id = R.string.Plate_Number),modifier = Modifier.padding(start = 8.dp), fontSize = 12.sp)
        editedTextField(value = plateNumber, keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next) ,
            label = R.string.time_due, onValueChanged = { plateNumber = it })

        Text(text = stringResource(id = R.string.Comment),modifier = Modifier.padding(start = 8.dp), fontSize = 12.sp)
        editedTextField2(value = comment, keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next) ,
            onValueChanged = { comment = it })
        Spacer(modifier = Modifier.height(30.dp))
        Button1(guestViewModel = guestViewModel, name = name,  phoneNumber = phoneNumber,
            vehicleType = vehicleType, plateNumber = plateNumber, comment = comment,
            selectedOption = selectedOption,navController = navController, shareViewModel = shareViewModel)

    }
}
@Composable
fun RadioButton1(selectedOption: String, onOptionSelected: (String) -> Unit){
    val options = listOf("One-Time Visit", "Exit Approval", "Group Visit")

    Row(modifier = Modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
        options.forEach { option ->
            Row(modifier = Modifier
                .height(48.dp)
                .padding(horizontal = 8.dp)
                .selectable(
                    selected = (selectedOption == option),
                    onClick = { onOptionSelected(option) }, // Update the selectedOption
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (selectedOption == option),
                    onClick = null
                )
                Text(text = option, modifier = Modifier.padding(), fontSize = 11.sp)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun editedTextField(value: String, keyboardOptions: KeyboardOptions,
                    @StringRes label : Int,
                    onValueChanged: (String) -> Unit, modifier: Modifier = Modifier
){
    TextField(value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(5.dp)),
        singleLine = true,
        label = { Text(stringResource(label),modifier = Modifier,
            color = Color.Gray) },
        keyboardOptions = keyboardOptions
    )

}
@ExperimentalMaterial3Api
@Composable
fun editedTextField2(value: String, keyboardOptions: KeyboardOptions,
                    onValueChanged: (String) -> Unit, modifier: Modifier = Modifier
){
    TextField(value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        singleLine = false,
        keyboardOptions = keyboardOptions
    )

}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Button1(guestViewModel: GuestViewModel, name: String, phoneNumber: String, vehicleType: String,
            plateNumber: String,
            comment: String,
            selectedOption: String, navController: NavController, shareViewModel: ShareViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val isFormValid = name.isNotBlank() &&
            phoneNumber.isNotBlank()
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(30.dp))
            .height(59.dp),
        onClick = {
            if (isFormValid) {
                val randomNumbers = List(1) { Random.nextInt(100, 1000) }
                val currentTime = LocalDateTime.now()
                val currentTimeString = currentTime.toString()

                // Get the comment from the view model or any other source
                // val comment = guestViewModel.getComment()
                // Save the guest information to Firebase here
                val user = FirebaseAuth.getInstance().currentUser
                val userUid = user?.uid

                if (userUid != null) {
                    val database = FirebaseDatabase.getInstance()
                    val guestsRef = database.getReference("users/$userUid/guests")

                    val guestId = UUID.randomUUID().toString() // Generate unique ID

                val newGuest = Guest(guestId,
                    name,
                    phoneNumber,
                    randomNumbers,
                    comment = comment,
                    plateNumber = plateNumber,
                    vehicleType = vehicleType,
                    currentTime = Date().time)
                newGuest.status = when (selectedOption) {
                    "One-Time Visit" -> "Booked"
                    "Exit Approval" -> "Leaving"
                    "Group Visit" -> "Group" // Adjust this as needed
                    else -> "Booked" // Default status
                }


                    val guestRef = guestsRef.child(guestId) // Use the generated ID here
                    guestRef.setValue(newGuest)
                }

                // Navigate to another screen (if needed)
                navController.navigate("home_screen")
            } else {
                Toast.makeText(context, "Enter name and phone number", Toast.LENGTH_SHORT)
                    .show()
            }
        },
        colors = androidx.compose.material.ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF0085A0),
            contentColor = androidx.compose.material.MaterialTheme.colors.onPrimary // Adjust text color based on primary color
        )
    ) {
        Text(text = "Generate Code")
    }
}
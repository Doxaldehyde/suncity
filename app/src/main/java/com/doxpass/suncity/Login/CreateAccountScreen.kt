package com.dox.suncity.Login

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doxpass.suncity.R
import com.doxpass.suncity.ShareViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(navController: NavController, authViewModel: AuthViewModel, shareViewModel: ShareViewModel) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var homeAddress by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var maritalStatus by remember { mutableStateOf("") }
    var occupancyType by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    // Get all records in registeredEmails node from firebase

    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("preRegisteredEmails")
    val emailsList = mutableListOf<String>()

    reference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            for (data in snapshot.children) {
                val email = data.child("email").getValue(String::class.java)

                println(email)
                if (email != null) {
                    emailsList.add(email.trim().lowercase())
                }
            }

            // Update the LiveData with fetched data
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle database error
            Log.e(
                ContentValues.TAG,
                "Database error for prereifstered: ${error.message}"
            )
        }
    })


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
            //   .verticalScroll(scrollState)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                text = stringResource(id = R.string.Estate_name)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Icon(
                Icons.Default.ArrowBack, contentDescription = "back arrow",
                modifier = Modifier
                    .size(28.dp)
                    .wrapContentWidth(align = Alignment.Start)
                    .clickable { navController.navigate("login_screen") })
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Create account in just few seconds", modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 21.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Start by creating your account", modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(435.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(modifier = Modifier) {
                            Text(
                                text = "First Name",
                                modifier = Modifier.padding(start = 10.dp),
                                fontSize = 12.sp
                            )
                            EditedTextField1(value = firstName,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                                ),
                                label = R.string.First_Name,
                                onValueChanged = { firstName = it })
                        }
                        Column(modifier = Modifier) {
                            Text(
                                text = "Last Name",
                                modifier = Modifier.padding(start = 10.dp),
                                fontSize = 12.sp
                            )
                            EditedTextField1(value = lastName,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                                ),
                                label = R.string.Last_Name,
                                onValueChanged = { lastName = it })
                        }
                    }
                    Text(
                        text = "Phone Number",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp
                    )
                    EditedTextField(value = phoneNumber,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        label = R.string.E_g_08152323909,
                        onValueChanged = { phoneNumber = it })

                    Text(
                        text = "Home address",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp
                    )
                    EditedTextField(value = homeAddress,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        label = R.string.Home_address,
                        onValueChanged = { homeAddress = it })

                   /* Text(
                        text = "Gender",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp
                    )
                    EditedTextField(value = gender,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        label = R.string.Gender,
                        onValueChanged = { gender = it })

                    Text(
                        text = "Marital Status",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp
                    )
                    EditedTextField(value = maritalStatus,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        label = R.string.Marital_status,
                        onValueChanged = { maritalStatus = it })

                    */



                    Text(
                        text = "Occupancy type",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp
                    )
                    EditedTextField(value = occupancyType,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        label = R.string.Occupancy_type,
                        onValueChanged = { occupancyType = it })
                    Text(
                        text = "Email",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp
                    )
                    EditedTextField(value = emailAddress,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        label = R.string.Email_address,
                        onValueChanged = { emailAddress = it })



                    Text(
                        text = "Password",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp
                    )
                    EditedTextField2(value = password,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        label = R.string.Password,
                        onValueChanged = { password = it })

                    Text(
                        text = "Confirm password",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp
                    )
                    EditedTextField2(value = confirmPassword,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                        ),
                        label = R.string.Confirm_password,
                        onValueChanged = { confirmPassword = it })

                }
            }

            val isFormValid = firstName.isNotBlank() &&
                    lastName.isNotBlank() &&
                    emailAddress.isNotBlank() &&
                    phoneNumber.isNotBlank() &&
                    homeAddress.isNotBlank() &&

                    occupancyType.isNotBlank() &&
                    password.isNotBlank() &&
                    confirmPassword.isNotBlank() &&
                    password == confirmPassword
            Spacer(modifier = Modifier.height(25.dp))
            val coroutineScope = rememberCoroutineScope()

            Button4(
                onClick = {
                    if (isFormValid) {
                        if (emailsList.contains(emailAddress)) {
                            // Proceed with registration
                            val newUser = User(
                                userId = "",
                                firstName = firstName,
                                lastName = lastName,
                                emailAddress = emailAddress.trim().lowercase(),
                                homeAddress = homeAddress,
                                phoneNumber = phoneNumber,

                                occupancyType = occupancyType
                            )

                            println("Emails list => ");
                            println(emailsList)
                            println("Stop")



                            authViewModel.email = emailAddress
                            authViewModel.password = password
                            coroutineScope.launch {
                                val registrationSuccessful = authViewModel.registerUser()
                                if (registrationSuccessful) { // Get the user's Firebase Authentication UID
                                    var uid = authViewModel.getAuthenticatedUserUID()
                                    if (uid != null) {
                                        // Set the user's userId in the newUser object
                                        newUser.userId = uid

                                        // Store user data in the Firebase Realtime Database
                                        val database: FirebaseDatabase =
                                            FirebaseDatabase.getInstance()
                                        val usersRef: DatabaseReference =
                                            database.getReference("users")

                                        usersRef.child(uid).setValue(newUser)

                                        val adminViewModel = AdminViewModel()
                                        adminViewModel.makeUserAdmin(uid)

                                        shareViewModel.firstName.value = firstName

                                        navController.navigate("login_screen")
                                        Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                                    } else {
                                        // Handle UID retrieval failure
                                        Toast.makeText(
                                            context,
                                            "Fail to retrieve UID",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                } else {
                                    // Handle registration failure
                                    Toast.makeText(context, "Registration fail", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                        else{
                            Toast.makeText(
                                context,
                                "you are not registered, contact estate chairman",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    } else {
                        // Display an error message or notification to the user
                        Toast.makeText(
                            context,
                            "Please fill in all fields correctly",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                },
                navController = navController,
                authViewModel = authViewModel,
                isEnabled = isFormValid
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Already have Account?")
                TextButton(onClick = { navController.navigate("login_screen") }) {
                    Text(text = "Login",
                        modifier = Modifier,
                        color = Color(0xFF0085A0)
                    )
                }
            }
        }

    }


}






@ExperimentalMaterial3Api
@Composable
fun EditedTextField(value: String, keyboardOptions: KeyboardOptions,
                    @StringRes label : Int,
                    onValueChanged: (String) -> Unit, modifier: Modifier = Modifier
){
    TextField(value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        singleLine = true,
        label = { Text(stringResource(label),modifier = Modifier,
            fontWeight = FontWeight.Light,
            color = Color.Gray) },
        keyboardOptions = keyboardOptions,

        )

}
@ExperimentalMaterial3Api
@Composable
fun EditedTextField1(value: String, keyboardOptions: KeyboardOptions,
                     @StringRes label : Int,
                     onValueChanged: (String) -> Unit, modifier: Modifier = Modifier
){
    TextField(value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        singleLine = true,
        label = { Text(stringResource(label),modifier = Modifier,
            fontWeight = FontWeight.Light,
            color = Color.Gray) },
        keyboardOptions = keyboardOptions
    )

}
@Composable
fun Button4(onClick: () -> Unit,
            navController: NavController,
            authViewModel: AuthViewModel,
            isEnabled : Boolean,){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            .clip(RoundedCornerShape(30.dp))
            .height(59.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF0085A0),
            contentColor = MaterialTheme.colors.onPrimary // Adjust text color based on primary color
        )
    ) {
        Text(text = "Register")
    }
}

@ExperimentalMaterial3Api
@Composable
fun EditedTextField2(value: String, keyboardOptions: KeyboardOptions,
                     @StringRes label : Int,
                     onValueChanged: (String) -> Unit, modifier: Modifier = Modifier
){
    var passwordVisible by remember { mutableStateOf(false) }
    val visualTransformation = if (passwordVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
    TextField(value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        singleLine = true,
        label = { Text(stringResource(label),modifier = Modifier,
            fontWeight = FontWeight.Light,
            color = Color.Gray) },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                val icon = if (passwordVisible) Icons.Default.ArrowDropDown else Icons.Default.KeyboardArrowUp
                Icon(icon, contentDescription = "visible button")
            }
        }

    )

}
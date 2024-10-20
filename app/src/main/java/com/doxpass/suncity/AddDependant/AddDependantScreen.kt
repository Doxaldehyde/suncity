package com.dox.suncity.AddDependant

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dox.suncity.Login.Dependent
import com.doxpass.suncity.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDependantScreen(navController: NavController, dependentViewModel: DependentViewModel) {
    val scrollState = rememberScrollState()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var relationship by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val userViewModel = remember { UserViewModel() }
    val context = LocalContext.current

    val isFormsValid = firstName.isNotBlank() && lastName.isNotBlank() && relationship.isNotBlank()
    // Get the current user UID
    val user = FirebaseAuth.getInstance().currentUser
    val userUid = user?.uid

    // Create a reference to the Firebase database for dependents
    val database = FirebaseDatabase.getInstance()
    val dependantsRef = database.getReference("users/$userUid/dependants")
    var firebaseDependants by remember {
        mutableStateOf<List<Dependant>>(emptyList())
    }

    val newDependentId = UUID.randomUUID().toString()

    Column(modifier = Modifier
        .verticalScroll(scrollState)
        .padding(10.dp)
        ) {
        Icon(
            Icons.Default.ArrowBack, contentDescription = "back arrow",
            modifier = Modifier
                .size(28.dp)
                .wrapContentWidth(align = Alignment.Start)
                .clickable { navController.navigate("home_screen") })
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Add Dependents", modifier = Modifier,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Add profile by entering their details",
            modifier = Modifier,
            fontSize = 12.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Image(modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.suncity1) , contentDescription = "suncity logo" )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "First Name",
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 12.sp)
        editedTextField(value = firstName, keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            label = R.string.First_Name ,
            onValueChanged = {firstName = it})
        Text(
            text = "Last Name",
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 12.sp)
        editedTextField(value = lastName, keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ),
            label = R.string.Last_Name , onValueChanged = {lastName = it} )
        Text(
            text = "Relationship",
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 12.sp)
        editedTextField(value = relationship, keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ),
            label = R.string.Relationship , onValueChanged = {relationship = it} )
        Spacer(modifier = Modifier.height(20.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
            .clip(RoundedCornerShape(30.dp))
            .height(59.dp),
            onClick = {
                if (isFormsValid) {


                    val newDependent = Dependent(newDependentId ,firstName, lastName, relationship)

                    // Push a new dependent to the database
                    dependantsRef.child(newDependentId).setValue(newDependent)
                    showDialog = true
                }
                else{
                    Toast.makeText(context, "Enter complete dependant details", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            colors = androidx.compose.material.ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF0085A0),
                contentColor = androidx.compose.material.MaterialTheme.colors.onPrimary // Adjust text color based on primary color
            )
            ) {
            Text(text = "Add Profile")
        }
        if (showDialog){
            DisplayDialog { showDialog = false
                            firstName = ""
                            lastName = ""
                            relationship = ""
            }
        }
    }
}
@Composable
fun DisplayDialog(onDone : () -> Unit){
 AlertDialog(
     onDismissRequest = { /*TODO*/ },
     title = { Text(text = "You have successfully added a member", modifier = Modifier,
         fontSize = 10.sp)},
     confirmButton = {
            Button(onClick = onDone,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0085A0))) {
                Text(text = "Done",
                    modifier = Modifier,
                    color = Color(0xFFEBF2F3))
            }
     })
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
            .clip(RoundedCornerShape(8.dp)),
        singleLine = true,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions
    )

}
package com.dox.profile.Login

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dox.suncity.Login.AuthViewModel
import com.doxpass.suncity.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel){
    var emailAddress by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }

    val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    // Check if email and password exist in SharedPreferences
    val savedEmail = sharedPreferences.getString("email", null)
    val savedPassword = sharedPreferences.getString("password", null)

    // If saved email and password exist, set them as default values
    if (savedEmail != null && savedPassword != null) {
        emailAddress = savedEmail
        password = savedPassword
    }


    Column(modifier = Modifier
        .verticalScroll(scrollState)
        .fillMaxSize()
        .padding(10.dp)) {
        Spacer(modifier = Modifier.height(60.dp))
        Image(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.suncity1), contentDescription = "DPK logo"
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "LOGIN", modifier = Modifier
                .fillMaxWidth(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Come back with your ID", modifier = Modifier
                .fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "For efficacy, fast and secure login", modifier = Modifier
                .fillMaxWidth(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Email address", modifier = Modifier.padding(bottom = 5.dp))
       Box {

           EeditedTextField(value = emailAddress,
               keyboardOptions = KeyboardOptions.Default.copy(
                   keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
               ),
               label = R.string.Email_address,
               onValueChanged = { emailAddress = it })

           if (isLoading.value) {
               Box(
                   modifier = Modifier.fillMaxSize(),
                   contentAlignment = Alignment.Center
               ) {
                   CircularProgressIndicator(
                       modifier = Modifier
                           .size(48.dp),
                       color = Color(0xFF0085A0),
                   )
               }


           }
       }

        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Password", modifier = Modifier.padding(bottom = 5.dp))
        EditedTextField3(value = password,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            ),
            label = R.string.Password,
            onValueChanged = { password = it })
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "forget password?")
            TextButton(onClick = {
                if (emailAddress.isNotBlank()) {
                    coroutineScope.launch {
                        val resetSuccessful = authViewModel.resetPassword(emailAddress)
                        if (resetSuccessful) {
                            Toast.makeText(context, "Password reset email sent successfully.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Password reset email could not be sent. Please check your email address.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Please enter your email address.", Toast.LENGTH_SHORT).show()
                }
            }
            ) {
                Text(text = "Reset",
                    modifier = Modifier,
                    color = Color(0xFF0085A0))
            }
        }
        Spacer(modifier = Modifier.height(60.dp))

        Button3(
            onClick = {
                authViewModel.email = emailAddress
                authViewModel.password = password
                coroutineScope.launch {
                    isLoading.value = true
                    val loginSuccessful = authViewModel.loginUser()

                    if (loginSuccessful) {
                        Toast.makeText(context, "Successfully login", Toast.LENGTH_SHORT).show()

                        with(sharedPreferences.edit()) {
                            putString("email", emailAddress)
                            putString("password", password)
                            apply()
                        }

                  navController.navigate("home_screen")
                 //   navController.navigate("security_screen")
                    } else {
                        Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                    }
                    isLoading.value = false
                }
            },
            navController = navController,
            authViewModel = authViewModel
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an account?")
            TextButton(onClick = { navController.navigate("createAccount_screen") }) {
                Text(text = "Create account",
                    modifier = Modifier,
                    color = Color(0xFF0085A0))
            }
        }


    }
}



@ExperimentalMaterial3Api
@Composable
fun EeditedTextField(value: String, keyboardOptions: KeyboardOptions,
                    @StringRes label : Int,
                    onValueChanged: (String) -> Unit, modifier: Modifier = Modifier
){
    TextField(value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .clip(RoundedCornerShape(8.dp)),
        singleLine = true,
        label = { Text(
            stringResource(label),modifier = Modifier,
            fontWeight = FontWeight.Light,
            color = Color.Gray) },
        keyboardOptions = keyboardOptions
    )

}

@Composable
fun Button3(onClick: () -> Unit,
            navController: NavController,
            authViewModel: AuthViewModel){
    Button(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clip(RoundedCornerShape(30.dp))
        .height(59.dp)
        ,onClick = onClick
        ,colors = androidx.compose.material.ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF0085A0),
            contentColor = androidx.compose.material.MaterialTheme.colors.onPrimary // Adjust text color based on primary color
        )
        ){
        Text(text = "Login")
    }
}
@ExperimentalMaterial3Api
@Composable
fun EditedTextField3(value: String, keyboardOptions: KeyboardOptions,
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
            .padding(0.dp)
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
                Icon(icon, contentDescription = "toggle icon")
            }
        }

    )

}

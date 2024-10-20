package com.doxpass.suncity.Emergency

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.doxpass.suncity.R

@Composable
fun EmergencyScreen(navController: NavController, modifier: Modifier = Modifier){
    val scrollState = rememberScrollState()
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(scrollState)) {
        EmergencyText()
        Spacer(modifier = Modifier.height(50.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Fire("+2347031522199", R.drawable.img_15)
            Spacer(modifier = Modifier.width(20.dp))
            Security()
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Medical()
            Spacer(modifier = Modifier.width(20.dp))
            PBES()
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Privacy Policy", modifier = Modifier,
            color = MaterialTheme.colorScheme.primary)
    }
   /* fun initiatePhoneCall(context: Context, phoneNumber: String) {
        val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val phoneUri = Uri.parse("tel:$phoneNumber")
            val callIntent = Intent(Intent.ACTION_CALL, phoneUri)
            context.startActivity(callIntent)
        } else {
            Log.e("PhoneCall", "CALL_PHONE permission not granted")
        }
    }

    */
}
@Composable
fun EmergencyText(){
    Text(text = "Emergency calls", modifier = Modifier
        .fillMaxWidth()
        .padding(top = 30.dp),
        fontSize = 22.sp,
       textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(5.dp))
    Text(text = "Need urgent assistant?, press the panic button \n to call for help",
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        fontSize = 16.sp,
         textAlign = TextAlign.Center,
        color = Color.LightGray)
}

@Composable
fun Fire(phoneNumber: String = "+2347031522199", imageId: Int) {
    val context = LocalContext.current

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            initiatePhoneCall(context, phoneNumber)
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Image(
        modifier = Modifier
            .size(130.dp)
            .clickable {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CALL_PHONE
                    ) == PERMISSION_GRANTED
                ) {
                    initiatePhoneCall(context, phoneNumber)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                }
            },
        painter = painterResource(id = imageId),
        contentDescription = "Fire"
    )
}

fun initiatePhoneCall(context: Context, phoneNumber: String) {
    val phoneUri = Uri.parse("tel:$phoneNumber")
    val callIntent = Intent(Intent.ACTION_CALL, phoneUri)
    startActivity(context, callIntent, null)
}
@Composable
fun Security(){
    val context = LocalContext.current

    val phoneNumber = "+2347010192220"

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            initiatePhoneCall(context, phoneNumber)
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Image(
        modifier = Modifier
            .size(130.dp)
            .clickable {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CALL_PHONE
                    ) == PERMISSION_GRANTED
                ) {
                    initiatePhoneCall(context, phoneNumber)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                }
            },
        painter = painterResource(id = R.drawable.img_17),
        contentDescription = "Security"
    )
    }

@Composable
fun Medical() {
    val context = LocalContext.current

    val phoneNumber = "+2348033124314"

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            initiatePhoneCall(context, phoneNumber)
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Image(
        modifier = Modifier
            .size(130.dp)
            .clickable {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CALL_PHONE
                    ) == PERMISSION_GRANTED
                ) {
                    initiatePhoneCall(context, phoneNumber)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                }
            },
        painter = painterResource(id = R.drawable.img_18),
        contentDescription = "Medical"
    )
}

@Composable
fun PBES(){
    val context = LocalContext.current

    val phoneNumber = "+2347046344217"

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            initiatePhoneCall(context, phoneNumber)
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Image(
        modifier = Modifier
            .size(130.dp)
            .clickable {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CALL_PHONE
                    ) == PERMISSION_GRANTED
                ) {
                    initiatePhoneCall(context, phoneNumber)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                }
            },
        painter = painterResource(id = R.drawable.img_16),
        contentDescription = "PBES"
    )
    }
package com.dox.suncity.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.doxpass.suncity.R


@Composable
fun OnboardingScreen1(navController: NavController){
    val scrollState = rememberScrollState()
    Column (modifier = Modifier.verticalScroll(scrollState)){
        Image(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
            .height(418.dp),
            contentScale = ContentScale.Crop
            ,painter = painterResource(id = R.drawable.onboarding2),
            contentDescription = "Estate environment")
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Keep your Estate Secured",
                modifier = Modifier,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
               // color = androidx.compose.material3.MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Secure your estate with a secured mobile",
                modifier = Modifier,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "platform",
                modifier = Modifier,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(5.dp))

            Spacer(modifier = Modifier.height(50.dp))
            Button5(onClick = { navController.navigate("onboarding_screen2")}, navController = navController)

            TextButton(onClick = { navController.navigate("login_screen") }) {
                Text(text = "Skip",
                    modifier = Modifier,
                    color = Color(0xFF0085A0))
            }
        }


    }
}
@Composable
fun Button5(onClick: () -> Unit, navController: NavController) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(30.dp))
            .height(59.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF0085A0),
            contentColor = MaterialTheme.colors.onPrimary // Adjust text color based on primary color
        )
    ) {
        Text(text = "Next")
    }
}
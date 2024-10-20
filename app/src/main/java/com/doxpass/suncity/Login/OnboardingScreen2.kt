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
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doxpass.suncity.R


@Composable
fun OnboardingScreen2(navController: NavController){
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Image(modifier = Modifier
            .fillMaxWidth()
            .height(418.dp),
            contentScale = ContentScale.Crop
            ,painter = painterResource(id = R.drawable.img_19),
            contentDescription = "CCTV")
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(modifier = Modifier.height(40.dp))
            androidx.compose.material3.Text(
                text = "Have your assets safe",
                modifier = Modifier,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                // color = androidx.compose.material3.MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(20.dp))
            androidx.compose.material3.Text(
                text = "Have your belongings safe and secured",
                modifier = Modifier,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(60.dp))
            Button5(onClick = { navController.navigate("login_screen")}, navController = navController)

            TextButton(onClick = { navController.navigate("login_screen") }) {
                Text(text = "Skip",
                    modifier = Modifier,
                    color = Color(0xFF0085A0)
                )
            }
        }


    }
}
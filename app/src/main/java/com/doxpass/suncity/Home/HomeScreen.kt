package com.dox.suncity.Home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.TintAwareDrawable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.doxpass.suncity.R

import kotlinx.coroutines.delay


@Composable
fun HomeScreen(navController: NavController, onNextButton: () -> Unit, modifier: Modifier = Modifier){
    var isLoading by remember { mutableStateOf(true) }

    // Simulate loading delay
    LaunchedEffect(Unit) {
        delay(2000) // Simulate data fetching delay
        isLoading = false
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
        // Show content when data is loaded
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp)
            ) {
                GuestBooking(navController = navController)
                AddDependant(navController = navController)
            }
            Spacer(modifier = Modifier.height(50.dp))

        }
    }
}

@Composable
fun GuestBooking(navController: NavController, modifier: Modifier = Modifier){
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = modifier
            .size(180.dp)
            .padding(5.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth(0.5f)
            .clickable { navController.navigate("guest_screen") }){
        Image(modifier = Modifier
            .size(190.dp)
            .padding(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.img_2), contentDescription = "guest booking" )
    }
}
@Composable
fun AddDependant(navController: NavController, modifier: Modifier = Modifier){
    Card(modifier = modifier
        .size(180.dp)
        .padding(5.dp)
        .clip(RoundedCornerShape(8.dp))
        .fillMaxWidth(0.5f)
        .clickable { navController.navigate("addDependent_screen") }){
        Image(modifier = Modifier
            .size(190.dp)
            .padding(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.img_1), contentDescription = "add dependant" )
    }
}

@Composable
fun getIconForScreen(screen: String): ImageVector {
    return when (screen) {
        "Home" -> Icons.Default.Home
        "Feed" -> Icons.Default.AccountBox
        "Post" -> Icons.Default.Add
        "Alert" -> Icons.Default.Notifications
        "Jobs" -> Icons.Default.Done
        else -> Icons.Default.Home
    }
}


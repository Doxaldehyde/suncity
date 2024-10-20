package com.doxpass.suncity

import EmailAddress
import NotificationBoardView
import SecurityScreen
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter

import com.dox.profile.GuestDetails.GuestViewModel
import com.dox.profile.Login.LoginScreen
import com.dox.suncity.AddDependant.AddDependantScreen
import com.dox.suncity.AddDependant.DependentViewModel
import com.dox.suncity.ChatScreen.ChatScreen
import com.dox.suncity.ChatScreen.ChatViewModel
import com.dox.suncity.GuestDetails.GuestListScreen
import com.dox.suncity.GuestDetails.GuestScreen
import com.dox.suncity.Home.HomeScreen
import com.dox.suncity.Home.Screen
import com.dox.suncity.Login.AuthScreen
import com.dox.suncity.Login.AuthViewModel
import com.dox.suncity.Login.CreateAccountScreen
import com.dox.suncity.Login.OnboardingScreen1
import com.dox.suncity.Login.OnboardingScreen2
import com.dox.suncity.Login.OnboardingScreen3
import com.dox.suncity.Login.User
import com.dox.suncity.Profile.ProfileScreen
import com.doxpass.suncity.Emergency.EmergencyScreen


import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayAppBar(
    navController: NavController,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    shareViewModel: ShareViewModel,
    imageUri : String?,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    var userFirstName by remember { mutableStateOf("") }
    val auth = Firebase.auth
    val user = auth.currentUser
    val userUID = user?.uid

    LaunchedEffect(userUID) {
        if (userUID != null) {
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(userUID)
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.let {
                        userFirstName = it.firstName

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }
    val authViewModel : AuthViewModel = viewModel()
    TopAppBar(
        title = {
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
                ,modifier = Modifier.padding()){
                Image(modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
                    .clickable { }
                    ,contentScale = ContentScale.Crop
                    ,painter = if (!imageUri.isNullOrEmpty()) {
                        rememberImagePainter(imageUri)
                    } else {
                        painterResource(id = R.drawable.suncitylogo)
                    },
                    contentDescription = "suncity logo")
                Column (modifier = Modifier.padding(start = 5.dp)){
                    Text(text = stringResource(id = R.string.Estate_name), modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold)
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Welcome back!", modifier = Modifier,
                            fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = userFirstName, modifier = Modifier,
                            fontSize = 12.sp)
                    }

                }
            }
            },
            actions = {

                IconButton(onClick = {
                        navController.navigate("auth_screen") }) {
                    Icon(Icons.Outlined.DateRange, contentDescription = "" )
                }

                IconButton(onClick = {
                    navController.navigate("contact_information") }) {
                    Icon(Icons.Outlined.Notifications, contentDescription = "" )
                }

                IconButton(onClick = { authViewModel.signOut()
                    authViewModel.clearSavedCredentials(context)
                   navController.navigate("login_screen")
                }) {
                    Icon(Icons.Outlined.ExitToApp, contentDescription = "")
                }
            }

    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val dependentViewModel: DependentViewModel = viewModel()
    val guestViewModel: GuestViewModel = viewModel()
    val authViewModel : AuthViewModel = viewModel()
    val shareViewModel: ShareViewModel = viewModel()
    val viewModel: ChatViewModel = remember { ChatViewModel() }

    NavHost(
        navController = navController,
        startDestination = Screen.FCMMessage.route
    )
    {
        composable(route = Screen.FCMMessage.route){
        //    FCMMessage()
        }

        composable(route = Screen.OnboardingScreen1.route){
            OnboardingScreen1(navController = navController)
        }

        composable(route = Screen.OnboardingScreen2.route){
            OnboardingScreen2(navController = navController)
        }
        composable(route = Screen.OnboardingScreen3.route){
            OnboardingScreen3(navController = navController)
        }
        composable(route = Screen.CreateAccountScreen.route){
            CreateAccountScreen(navController = navController,
                authViewModel = authViewModel,
                shareViewModel = shareViewModel)
        }
        composable(route = Screen.AuthScreen.route){
            AuthScreen(navController = navController)
        }
        composable(route = Screen.contactInformation.route){
         // EmailAddress(navController = navController)
         NotificationBoardView(navController = navController)
        }
        composable(route = Screen.LoginScreen.route){
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(route = Screen.SecurityScreen.route){
            SecurityScreen(navController = navController, shareViewModel = shareViewModel, guestViewModel = guestViewModel)
        }
        composable(route = Screen.HomeScreen.route) {

            Scaffold(
                topBar = {

                    LunchTrayAppBar(
                        navController = navController,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() },
                        shareViewModel = shareViewModel, imageUri = null
                    )
                },
                bottomBar = {

                    BottomNavigation(
                        backgroundColor = Color(0xFF0085A0)
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        val bottomNavigationItems = listOf(
                            Screen.HomeScreen,
                            Screen.GuestListScreen,
                            Screen.EmergencyScreen,
                            Screen.ChatScreen,
                            Screen.ProfileScreen
                        )

                        bottomNavigationItems.forEach { screen ->
                            BottomNavigationItem(
                                selected = currentRoute == screen.route,
                                icon = {
                                    Icon(
                                        getIconForScreen(screen),
                                        contentDescription = "Screen"
                                    )
                                },
                                label = { Text(text = screen.title , modifier = Modifier,
                                    fontSize = 8.sp) },
                                onClick = {
                                    navController.navigate(screen.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                    }
                                },
                            )
                        }
                    }
                }
            )

            { innerPadding ->


                HomeScreen(
                    navController = navController, onNextButton = {},
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

                    }
                    composable(route = Screen.GuestScreen.route) {
                        GuestScreen(guestViewModel = guestViewModel, navController = navController, shareViewModel = shareViewModel)
                    }
                    composable(route = Screen.SubscriptionScreen.route) {
                  //      SubscriptionScreen(navController = navController)
                    }
                    composable(route = Screen.EmergencyScreen.route){
                        Scaffold(
                            bottomBar = {
                                BottomNavigation(
                                    backgroundColor = Color(0xFF0085A0)
                                ) {
                                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                                    val currentRoute = navBackStackEntry?.destination?.route
                                    val bottomNavigationItems = listOf(
                                        Screen.HomeScreen,
                                        Screen.GuestListScreen,
                                        Screen.EmergencyScreen,
                                        Screen.ChatScreen,
                                        Screen.ProfileScreen
                                    )

                                    bottomNavigationItems.forEach { screen ->
                                        BottomNavigationItem(
                                            selected = currentRoute == screen.route,
                                            icon = {
                                                Icon(
                                                    getIconForScreen(screen),
                                                    contentDescription = "Screen"
                                                )
                                            },
                                            label = {
                                                Text(
                                                    text = screen.title, modifier = Modifier,
                                                    fontSize = 8.sp
                                                )
                                            },
                                            onClick = {
                                                navController.navigate(screen.route) {

                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                }
                                            },
                                        )
                                    }
                                }
                            }
                        ) { innerPadding ->
                            EmergencyScreen(navController = navController, modifier = Modifier.padding(innerPadding))

                        }
                    }

                    composable(route = Screen.ProfileScreen.route){

                        ProfileScreen(navController = navController, dependentViewModel = dependentViewModel, context = LocalContext.current )
                    }
                    composable(route = Screen.ChatScreen.route){
                        ChatScreen(viewModel,navController = navController,
                            modifier = Modifier)
                    }
                    composable(route = Screen.GuestListScreen.route) {
                        Scaffold(

                            bottomBar = {

                                BottomNavigation(
                                    backgroundColor = Color(0xFF0085A0)
                                ) {
                                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                                    val currentRoute = navBackStackEntry?.destination?.route
                                    val bottomNavigationItems = listOf(
                                        Screen.HomeScreen,
                                        Screen.GuestListScreen,
                                        Screen.EmergencyScreen,
                                        Screen.ChatScreen,
                                        Screen.ProfileScreen
                                    )

                                    bottomNavigationItems.forEach { screen ->
                                        BottomNavigationItem(
                                            selected = currentRoute == screen.route,
                                            icon = {
                                                Icon(
                                                    getIconForScreen(screen),
                                                    contentDescription = "Screen"
                                                )
                                            },
                                            label = {
                                                Text(
                                                    text = screen.title, modifier = Modifier,
                                                    fontSize = 8.sp
                                                )
                                            },
                                            onClick = {
                                                navController.navigate(screen.route) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                }
                                            },
                                        )
                                    }
                                }
                            }
                        ) { innerPadding ->

                            GuestListScreen(navController = navController, guestViewModel = guestViewModel,
                                selectedOption = String()
                                ,modifier = Modifier.padding(innerPadding))
                        }
                    }
                    composable(route = Screen.AddDependantScreen.route){
                        AddDependantScreen(navController = navController, dependentViewModel = dependentViewModel)
                    }
        composable(route = Screen.dependantList.route){
            dependantList(navController = navController)
        }
                }
            }



@Composable
fun getIconForScreen(screen: Screen): ImageVector {
    return when (screen) {
        Screen.HomeScreen -> Icons.Default.Home
       Screen.GuestListScreen -> Icons.Default.List
        Screen.EmergencyScreen -> Icons.Default.Add
        Screen.ChatScreen -> Icons.Default.Send
       Screen.ProfileScreen -> Icons.Default.AccountBox
        else -> Icons.Default.Home
    }
}

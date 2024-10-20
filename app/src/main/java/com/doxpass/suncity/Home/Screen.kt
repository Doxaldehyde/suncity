package com.dox.suncity.Home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title : String){
    object OnboardingScreen1 : Screen("onboarding_screen1", "")
    object OnboardingScreen2 : Screen("onboarding_screen2", "")
    object OnboardingScreen3 : Screen("onboarding_screen3", "")
    object CreateAccountScreen : Screen("createAccount_screen" , "")
    object AuthScreen : Screen("auth_screen", "")
    object LoginScreen : Screen ("login_screen", "")
    object HomeScreen : Screen("home_screen", "Home")
    object GuestScreen : Screen("guest_screen" , "")
    object SubscriptionScreen : Screen("subscription_screen" , "")
    object GuestListScreen : Screen("guest_list" , "Guest-list")
    object EmergencyScreen : Screen("emergency" , "Emergency")
    object ChatScreen : Screen("chat" , "Chat")
    object ProfileScreen : Screen("profile" , "Profile")
    object AddDependantScreen : Screen("addDependent_screen" , "")
    object contactInformation: Screen("contact_information", "")
    object SecurityScreen: Screen("security_screen", "")
    object dependantList: Screen("dependant_list", "")
    object FCMMessage: Screen ("FCMMessage_screen", "")


}



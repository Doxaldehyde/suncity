package com.dox.suncity.Login


import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.Constants
import kotlinx.coroutines.tasks.await
class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    var email : String = ""
    var password : String = ""

    suspend fun getAuthenticatedUserUID(): String? {
        return auth.currentUser?.uid
    }
    suspend fun resetPassword(email: String): Boolean {
        try {
            val auth = FirebaseAuth.getInstance()
            auth.sendPasswordResetEmail(email).await()
            true
        } catch (e: Exception) {
            // Handle exceptions, e.g., if the email address is not registered.
            // You can log the error or display an error message.
            Log.e(Constants.MessageNotificationKeys.TAG, "Error sending password reset email: ${e.message}")
            false
        }
        return true
    }

    suspend fun registerUser(): Boolean {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            return true
        } catch (e: Exception) {
            // Handle registration failure
            Log.e(
                ContentValues.TAG,
                "Account creation error: ${e.message}"
            )
            return false
        }
    }

    // Function to log in an existing user
    suspend fun loginUser(): Boolean {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            return true
        } catch (e: Exception) {
            // Handle login failure
            return false
        }
    }

    // Function to check if the user is authenticated
    fun isAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    // Function to sign out the user
    fun signOut() {
        auth.signOut()
    }
    fun clearSavedCredentials(context: Context) {
        val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove("email")
            remove("password")
            apply()
        }
    }

}
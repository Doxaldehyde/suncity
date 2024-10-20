package com.doxpass.suncity

import android.os.Build
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


class ShareViewModel: ViewModel() {
    var firstName: MutableState<String?> = mutableStateOf(null)}

    /*
fun deleteGuest(guest: Guest) {
    val user = FirebaseAuth.getInstance().currentUser
    val userUid = user?.uid

    if (userUid != null) {
        val database = FirebaseDatabase.getInstance()
        val guestsRef = database.getReference("users/$userUid/guests")
        val guestId = guest.id // Assuming guest.id contains the UID

        guestsRef.child(guestId).removeValue()
            .addOnSuccessListener {
                Log.d("DeleteGuest", "Guest deletion successful")
            }
            .addOnFailureListener { e ->
                Log.e("DeleteGuest", "Error deleting guest", e)
            }
    }
}

    fun updateStatusInFirebase(guest: Guest, newStatus: String) {
        Log.d("UpdateFirebase", "Before update: ${guest.id} - ${guest.status}")
        Log.d("UpdateFirebase", "Updating status for guest: ${guest.id} to $newStatus")

        val user = FirebaseAuth.getInstance().currentUser
        val userUid = user?.uid

        if (userUid != null) {
            val database = FirebaseDatabase.getInstance()
            val guestsRef = database.getReference("users/$userUid/guests")

            // Assuming that 'guest.id' contains the unique identifier of the guest
            val guestId = guest.id

            guest.status = newStatus

         guestsRef.child(guestId).setValue(guest)
               .addOnSuccessListener {
                    Log.d("UpdateFirebase", "Update successful")

                    // Now, you can log the status after a short delay
                    android.os.Handler().postDelayed({
                        Log.d("UpdateFirebase", "After update: ${guest.id} - $newStatus")
                    }, 1000) // Delay for 1 second (adjust as needed)
                }
                .addOnFailureListener { e ->
                    Log.e("UpdateFirebase", "Error updating status", e)
                }

        }
    }
}

     */


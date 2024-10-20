package com.dox.profile.GuestDetails

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.util.*

class GuestViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance()
    private val guestsRef = database.getReference("users")

    private val guests = mutableListOf<Guest>()

    private val eventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            guests.clear()
            for (userSnapshot in snapshot.children) {
                val guestsSnapshot = userSnapshot.child("guests")
                for (guestSnapshot in guestsSnapshot.children) {
                    val guest = guestSnapshot.getValue(Guest::class.java)
                    guest?.let { guests.add(it) }
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("GuestViewModel", "Database error: ${error.message}")
        }
    }

    init {
        // Add a listener for all users
        guestsRef.addValueEventListener(eventListener)
    }

    override fun onCleared() {
        super.onCleared()
        // Remove the database listener when ViewModel is cleared
        guestsRef.removeEventListener(eventListener)
    }

    fun deleteGuest(guestId: String) {
        guestsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    val guestRef = userSnapshot.child("guests").child(guestId)
                    if (guestRef.exists()) {
                        val checkInTime = guestRef.child("checkInTime").getValue(Long::class.java)
                        val currentTime = Date().time
                        val twentyFourHoursInMillis = 1 * 60 * 1000 // 24 hours in milliseconds

                        if (checkInTime != null && currentTime - checkInTime > twentyFourHoursInMillis) {
                            // Delete the guest if check-in time is older than 24 hours
                            guestRef.ref.removeValue()
                            return
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("GuestViewModel", "Database error: ${error.message}")
            }
        })
    }

    fun getGuests(): List<Guest> {
        return guests
    }
}

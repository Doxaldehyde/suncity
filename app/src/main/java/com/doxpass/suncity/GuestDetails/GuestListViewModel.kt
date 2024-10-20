package com.dox.profile.GuestDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class GuestListViewModel : ViewModel() {
    fun updateGuestOut(guest: Guest, newStatus: String){
        val user = FirebaseAuth.getInstance().currentUser
        val userUid = user?.uid

        if (userUid != null ) {
            val database = FirebaseDatabase.getInstance()
            val guestsRef = database.getReference("users/$userUid/guests")

            // Assuming that 'guest.id' contains the unique identifier of the guest
            val guestId = guest.id

            // Update the status directly without checking the current status
            val guestRef = guestsRef.child(guestId)
            guestRef.child("status").setValue(newStatus) { error, _ ->
                if (error == null) {
                    // Status updated successfully
                } else {
                    // Handle the error
                }
            }
        }
        else {

        }
    }
    private val guests = MutableLiveData<List<Guest>>()

    // Initialize the guest list with an empty list
    init {
        guests.value = emptyList()
    }

    fun setGuests(newGuests: List<Guest>) {
        guests.value = newGuests
    }

    fun getGuests(): LiveData<List<Guest>> {
        return guests
    }
}
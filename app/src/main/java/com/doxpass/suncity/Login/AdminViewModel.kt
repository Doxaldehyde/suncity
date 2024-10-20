package com.dox.suncity.Login

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class AdminViewModel {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val adminsRef: DatabaseReference = database.getReference("admins")

    suspend fun makeUserAdmin(uid: String): Boolean {
        return try {
            adminsRef.child(uid).setValue(true).await()
            true
        } catch (e: Exception) {
            // Handle the error (e.g., logging, displaying a message)
            false
        }
    }
}
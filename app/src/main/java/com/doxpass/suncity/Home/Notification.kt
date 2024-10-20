package com.dox.profile.Home
import NotificationItem
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationViewModel : ViewModel() {
    private val _notifications = MutableLiveData<List<NotificationItem>>()
    val notifications: LiveData<List<NotificationItem>> get() = _notifications

    // Implement your logic to fetch notifications from Firebase or any other source
    fun fetchNotifications() {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("NotificationItem")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notificationsList = mutableListOf<NotificationItem>()

                for (data in snapshot.children) {
                    val title = data.child("title").getValue(String::class.java)
                    val body = data.child("body").getValue(String::class.java)

                    title?.let { title ->
                        body?.let { body ->
                            notificationsList.add(NotificationItem(title, body))
                        }
                    }
                }

                // Update the LiveData with fetched data
                _notifications.value = notificationsList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
                Log.e(TAG, "Database error: ${error.message}")
            }
        })
    }
}
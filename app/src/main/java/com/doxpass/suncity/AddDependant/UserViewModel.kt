package com.dox.suncity.AddDependant

import androidx.lifecycle.ViewModel
import com.dox.suncity.Login.User


class UserViewModel : ViewModel() {
    var currentUser: User = User() // Initialize with default values or fetch from your data source

    fun updateCurrentUser(updatedUser: User) {
        currentUser = updatedUser
    }
}
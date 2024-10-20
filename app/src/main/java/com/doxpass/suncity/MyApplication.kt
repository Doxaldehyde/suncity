package com.doxpass.suncity

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.dox.profile.GuestDetails.GuestViewModel

class MyApplication : Application() {
    val guestViewModel: GuestViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(this).create(GuestViewModel::class.java)
    }


}
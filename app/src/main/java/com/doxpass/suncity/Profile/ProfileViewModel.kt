package com.dox.profile.Profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel: ViewModel() {
    val userImageUri = MutableLiveData<Uri?>()


}





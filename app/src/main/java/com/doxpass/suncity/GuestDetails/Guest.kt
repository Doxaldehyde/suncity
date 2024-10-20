package com.dox.profile.GuestDetails

import java.util.Date

data class Guest(
    val id: String = "",
    var name: String = "",
    var phoneNumber: String = "",
    val randomNumbers: List<Int> = emptyList(),
   var comment: String = "",
   var plateNumber: String = "",
   var vehicleType: String = "",
    val currentTime: Long = Date().time,
    var status: String = ""
)

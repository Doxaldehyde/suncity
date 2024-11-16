package com.doxpass.suncity.ui.theme.Notification

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


data class SendMessageDto(
    val to: String?,
    val notification: NotificationBody
)

data class NotificationBody(
    val title: String,
    val body: String
)

data class NotificationRequest(val token: String, val message: String)


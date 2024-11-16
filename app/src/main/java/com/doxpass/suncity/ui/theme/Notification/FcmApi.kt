package com.doxpass.suncity.ui.theme.Notification

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {

    @POST("/send")
    suspend fun sendMessage(
        @Body body: SendMessageDto
    )

    @POST("/broadcast")
    suspend fun broadcast(
        @Body body: SendMessageDto
    )
}
interface ApiService { @POST("send-notification")
fun sendNotification(@Body requestBody: NotificationRequest): Call<ResponseBody>
}
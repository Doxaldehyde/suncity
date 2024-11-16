package com.doxpass.suncity.ui.theme.Notification

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class NotificationViewModel : ViewModel() {

    private val gson = GsonBuilder().setLenient().create()

    private val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/") // Replace with your server URL
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    var fcmToken = mutableStateOf<String?>(null)
        private set

    init { fetchFcmToken() }
    private fun fetchFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                fcmToken.value = task.result
                println("FCM Token: $fcmToken")
            } else {
                println("Fetching FCM token failed: ${task.exception?.message}")
            }
        }
    }

    fun sendNotification(message: String) {
        val token = fcmToken.value ?: return
        val request = NotificationRequest(token, message)
        apiService.sendNotification(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    println("Notification sent successfully: ${response.body()}")
                } else {
                    println("Failed to send notification: ${response.errorBody()?.string()}")
                }
            }

             override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("Error sending notification: ${t.message}")
            }
        })
    }
}

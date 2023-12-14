package com.example.sakina.feature_authentication.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthRetrofitClient {

    companion object{

        private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthenticateInterceptor())  // Add your custom interceptor for token handling
            .build()

        private val retrofit by lazy{
            Retrofit.Builder()
                .baseUrl("https://6ffe-197-41-168-137.ngrok.io/api/account/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        val api by lazy{
            retrofit.create(AuthApi::class.java)
        }
    }
}
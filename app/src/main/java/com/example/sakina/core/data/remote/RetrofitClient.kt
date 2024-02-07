package com.example.sakina.core.data.remote

import com.example.sakina.core.util.Constant.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object{

        private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthenticateInterceptor())  // Add your custom interceptor for token handling
            .build()

        private val retrofit by lazy{
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        val api by lazy{
            retrofit.create(SakinaAPI::class.java)
        }
    }
}
package com.example.sakina.core.data.remote.main_api


import com.example.sakina.core.util.Constant.MAIN_API_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainRetrofitClient {

    companion object {

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthenticateInterceptor())
//            .authenticator(AuthAuthenticator())
            .build()

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(MAIN_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        val api: MainAPI by lazy {
            retrofit.create(MainAPI::class.java)
        }
    }
}
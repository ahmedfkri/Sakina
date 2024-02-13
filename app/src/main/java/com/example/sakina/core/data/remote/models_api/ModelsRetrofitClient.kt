package com.example.sakina.core.data.remote.models_api

import com.example.sakina.core.data.remote.main_api.MainAPI
import com.example.sakina.core.util.Constant
import com.example.sakina.core.util.Constant.MODELS_API_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ModelsRetrofitClient {

    companion object {

        private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .build()

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(MODELS_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        val api: ModelsAPI by lazy {
            retrofit.create(ModelsAPI::class.java)
        }
    }
}
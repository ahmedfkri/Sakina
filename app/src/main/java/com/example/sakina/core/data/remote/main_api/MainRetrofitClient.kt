package com.example.sakina.core.data.remote.main_api

import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.JWT_TOKEN
import com.example.sakina.core.util.Constant.MAIN_API_BASE_URL
import com.example.sakina.core.util.Constant.REFRESH_TOKEN
import com.example.sakina.feature_authentication.domain.model.RefreshToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainRetrofitClient {

    companion object {

         private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthenticateInterceptor())
            .authenticator(AuthAuthenticator())
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
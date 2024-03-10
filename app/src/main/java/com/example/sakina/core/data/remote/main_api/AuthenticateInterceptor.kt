package com.example.sakina.core.data.remote.main_api

import android.util.Log
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.JWT_TOKEN
import com.example.sakina.core.util.Constant.REFRESH_TOKEN
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.feature_authentication.domain.model.RefreshToken
import kotlinx.coroutines.runBlocking

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticateInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val accessToken = MySharedPref.getString(JWT_TOKEN, "")

        request = request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(request)


        return response
    }
}

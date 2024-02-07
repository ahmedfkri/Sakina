package com.example.sakina.core.data.remote

import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticateInterceptor :Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = MySharedPref.getString(Constant.JWT_TOKEN,"NON")

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(newRequest)
    }
}
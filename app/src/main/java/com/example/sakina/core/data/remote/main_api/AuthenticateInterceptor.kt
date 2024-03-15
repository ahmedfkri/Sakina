package com.example.sakina.core.data.remote.main_api

import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.JWT_TOKEN


import okhttp3.Interceptor
import okhttp3.Response

class AuthenticateInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val accessToken = MySharedPref.getString(JWT_TOKEN, "")

        request = request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()


        return chain.proceed(request)
    }
}

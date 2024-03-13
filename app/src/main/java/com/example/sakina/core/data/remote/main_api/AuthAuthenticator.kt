package com.example.sakina.core.data.remote.main_api

import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.JWT_TOKEN
import com.example.sakina.core.util.Constant.MAIN_API_BASE_URL
import com.example.sakina.feature_authentication.domain.model.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.model.RefreshToken
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthAuthenticator : Authenticator {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthenticateInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(MAIN_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = MySharedPref.getString(JWT_TOKEN, "") ?: return null

        return runBlocking {
            val newToken = getNewToken(token)

            if (!newToken.isSuccessful || newToken.body() == null) {
                // Couldn't refresh the token, so restart the login process
                // MySharedPref.clearValue(JWT_TOKEN)
                return@runBlocking null
            }

            return@runBlocking newToken.body()?.let {
                MySharedPref.putString(JWT_TOKEN, it.jwtToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.jwtToken}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String): retrofit2.Response<AuthenticateResponse> {
        val service = retrofit.create(RefreshTokenAPI::class.java)
        return service.requestJwt(RefreshToken(refreshToken))
    }
}

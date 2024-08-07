package com.plcoding.jwtauthktorandroid.data.auth

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("signup")
    suspend fun signUp(
        @Body request: AuthRequest
    )

    @POST("auth/sign-in")
    suspend fun signIn(
        @Body request: AuthRequest
    ): TokenResponse

    @GET("api/book/")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )
}
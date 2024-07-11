package com.plcoding.jwtauthktorandroid.data.books

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface BookApi {

    @GET("api/book")
    suspend fun getBooks(
        @Header("Authorization") token: String
    ): List<Book>

    @GET("api/book/{id}")
    suspend fun getBook(
        @Header("Authorization") token: String,
        @Body id: Int
    ): Book

    @POST("api/book")
    suspend fun addBook(
        @Header("Authorization") token: String,
        @Body book: Book
    )

    @DELETE("api/book/{id}")
    suspend fun deleteBook(
        @Header("Authorization") token: String,
        @Body id: Int
    )

    @PUT("api/book")
    suspend fun updateBook(
        @Header("Authorization") token: String,
        @Body book: Book
    )
}
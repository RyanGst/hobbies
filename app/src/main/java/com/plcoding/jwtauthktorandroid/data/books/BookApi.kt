package com.plcoding.jwtauthktorandroid.data.books

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface BookApi {

    @GET("api/book/")
    suspend fun getBooks(
        @Header("Authorization") token: String,
        @Header("Accept") accept: String = "application/json",
    ): List<Book>

    @GET("api/book/{id}")
    suspend fun getBookById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Book

    @POST("api/book/")
    suspend fun addBook(
        @Header("Authorization") token: String,
        @Body book: Book
    )

    @DELETE("api/book/{id}")
    suspend fun deleteBook(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    )

    @PUT("api/book/")
    suspend fun updateBook(
        @Header("Authorization") token: String,
        @Body book: Book
    )
}
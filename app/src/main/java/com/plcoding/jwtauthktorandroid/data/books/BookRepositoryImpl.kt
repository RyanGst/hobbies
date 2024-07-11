package com.plcoding.jwtauthktorandroid.data.books

import android.content.SharedPreferences


class BookRepositoryImpl(
    private val api: BookApi,
    private val prefs: SharedPreferences

) : BookRepository {
    override suspend fun getBooks(): BookQueryResult<List<Book>> {
        return try {
            val token = prefs.getString("jwt", null) ?: return BookQueryResult.UnknownError()
            val books = api.getBooks("Bearer $token")
            BookQueryResult.Success(books)
        } catch (e: Exception) {
            BookQueryResult.UnknownError()
        }
    }

    override suspend fun getBookById(id: Int): BookQueryResult<Book> {
        return try {
            val token = prefs.getString("jwt", null) ?: return BookQueryResult.UnknownError()
            val book = api.getBook("Bearer $token", id)
            BookQueryResult.Success(book)
        } catch (e: Exception) {
            BookQueryResult.UnknownError()
        }
    }

    override suspend fun addBook(book: Book): BookQueryResult<Book> {
        return try {
            val token = prefs.getString("jwt", null) ?: return BookQueryResult.UnknownError()
            api.addBook("Bearer $token", book)
            BookQueryResult.Success(book)
        } catch (e: Exception) {
            BookQueryResult.UnknownError()
        }
    }

    override suspend fun updateBook(book: Book): BookQueryResult<Book> {
        return try {
            val token = prefs.getString("jwt", null) ?: return BookQueryResult.UnknownError()
            api.updateBook("Bearer $token", book)
            BookQueryResult.Success(book)
        } catch (e: Exception) {
            BookQueryResult.UnknownError()
        }
    }

    override suspend fun deleteBook(id: Int): BookQueryResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return BookQueryResult.UnknownError()
            api.deleteBook("Bearer $token", id)
            BookQueryResult.Success(Unit)
        } catch (e: Exception) {
            BookQueryResult.UnknownError()
        }
    }


}
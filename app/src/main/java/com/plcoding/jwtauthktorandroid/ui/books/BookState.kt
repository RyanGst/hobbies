package com.plcoding.jwtauthktorandroid.ui.books

import com.plcoding.jwtauthktorandroid.data.books.Book

data class BookState(
    val isLoading: Boolean = false,
    var books: List<Book> = emptyList()
)

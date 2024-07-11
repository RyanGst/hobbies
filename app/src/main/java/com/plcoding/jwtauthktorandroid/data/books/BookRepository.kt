package com.plcoding.jwtauthktorandroid.data.books


interface BookRepository {
    suspend fun getBooks(): BookQueryResult<List<Book>>
    suspend fun getBookById(id: Int): BookQueryResult<Book>
    suspend fun addBook(book: Book): BookQueryResult<Book>
    suspend fun updateBook(book: Book): BookQueryResult<Book>
    suspend fun deleteBook(id: Int): BookQueryResult<Unit>
}
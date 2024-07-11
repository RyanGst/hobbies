package com.plcoding.jwtauthktorandroid.data.books

sealed class BookQueryResult<T>(val data: T? = null) {
    class Success<T>(data: T? = null): BookQueryResult<T>(data)
    class Error<T>(data: T? = null): BookQueryResult<T>(data)
    class UnknownError<T>(data: T? = null): BookQueryResult<T>(data)
}

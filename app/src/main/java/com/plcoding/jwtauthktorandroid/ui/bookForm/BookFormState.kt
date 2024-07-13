package com.plcoding.jwtauthktorandroid.ui.bookForm

data class BookFormState(
    val id: Int? = null,
    val isLoading: Boolean = false,
    val title: String = "",
    val author: String = "",
    val price: Int? = null,
    val launchDate: String = "",
)

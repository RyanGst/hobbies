package com.plcoding.jwtauthktorandroid.ui.bookForm

sealed class BookFormUiEvent {
    data class TitleChanged(val value: String): BookFormUiEvent()
    data class AuthorChanged(val value: String): BookFormUiEvent()
    data class PriceChanged(val value: String): BookFormUiEvent()
    data class LaunchDateChanged(val value: String): BookFormUiEvent()
    object SaveBook: BookFormUiEvent()
}

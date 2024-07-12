package com.plcoding.jwtauthktorandroid.ui.books

sealed class BookUiEvent {

    object GetBooks : BookUiEvent()
    object ShowError : BookUiEvent()
    object CreateBook : BookUiEvent()

}

package com.plcoding.jwtauthktorandroid.ui.books

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.jwtauthktorandroid.data.books.Book
import com.plcoding.jwtauthktorandroid.data.books.BookQueryResult
import com.plcoding.jwtauthktorandroid.data.books.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    var state by mutableStateOf(BookState())

    private val resultChannel = Channel<BookQueryResult<List<Book>>>()
    val bookResults = resultChannel.receiveAsFlow()

    init {
        getBooks()
    }

    fun onEvent(event: BookUiEvent) {
        when (event) {
            is BookUiEvent.GetBooks -> {
                getBooks()
            }

            BookUiEvent.CreateBook -> {

            }

            BookUiEvent.ShowError -> TODO()
            is BookUiEvent.DeleteBook -> {
                deleteBook(event.id)
            }
        }
    }

    private fun deleteBook(id: Int) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.deleteBook(id)
            state = state.copy(isLoading = false)
            getBooks()

        }

    }

    private fun getBooks() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.getBooks()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

}
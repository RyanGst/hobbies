package com.plcoding.jwtauthktorandroid.ui.bookForm

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
class BookFormViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    var state by mutableStateOf(BookFormState())

    private val resultChannel = Channel<BookQueryResult<Book>>()
    val bookFormResult = resultChannel.receiveAsFlow()

    val authors : List<String> = listOf(
        "Robert C. Martin",
        "Martin Fowler",
        "Kent Beck",
        "Erick Evans",
        "David Flanagan",
        "David Goggins",
    )

    fun onEvent(event: BookFormUiEvent) {
        when (event) {
            is BookFormUiEvent.AuthorChanged -> {
                state = state.copy(author = event.value)
            }

            is BookFormUiEvent.LaunchDateChanged -> {
                state = state.copy(launchDate = event.value)

            }

            is BookFormUiEvent.PriceChanged -> {
                state = state.copy(price = event.value.toIntOrNull())
            }

            BookFormUiEvent.SaveBook -> {
                saveBook()
            }

            is BookFormUiEvent.TitleChanged -> {
                state = state.copy(title = event.value)
            }

            else -> {}
        }
    }

    private fun saveBook() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val book = Book(
                title = state.title,
                author = state.author,
                price = state.price!!,
                launchDate = state.launchDate
            )
            val result = repository.addBook(
                book
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun updateBook(book: Book) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.updateBook(book)
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}
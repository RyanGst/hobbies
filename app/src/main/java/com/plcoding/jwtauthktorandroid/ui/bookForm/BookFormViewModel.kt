package com.plcoding.jwtauthktorandroid.ui.bookForm

import android.util.Log
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

            is BookFormUiEvent.LoadBook -> {
                loadBook(event.bookId)
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

            val result: BookQueryResult<Book> = if (state.id == null) {
                repository.addBook(book)
            } else {
                repository.updateBook(book.copy(id = state.id))
            }

            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }


    private fun loadBook(bookId: Int) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = repository.getBookById(bookId)) {
                is BookQueryResult.Success -> {
                    val book = result.data!!
                    state = state.copy(
                        id = book.id,
                        title = book.title,
                        author = book.author,
                        price = book.price,
                        launchDate = book.launchDate,
                        isLoading = false
                    )
                }
                else -> {
                    Log.d("BookFormViewModel", "Error loading book")
                    Log.d("BookFormViewModel", result?.data.toString())
                    resultChannel.send(result)
                    state = state.copy(isLoading = false)
                }
            }
        }
    }
}
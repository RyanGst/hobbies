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
): ViewModel() {

    var state by mutableStateOf(BookFormState())

    private val resultChannel = Channel<BookQueryResult<List<Book>>>()
    val bookFormResult = resultChannel.receiveAsFlow()


    fun onEvent(event: BookFormUiEvent) {
        when(event) {
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
        }
    }

    private fun saveBook() {}

}
package com.plcoding.jwtauthktorandroid.ui.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.jwtauthktorandroid.data.books.Book
import com.plcoding.jwtauthktorandroid.data.books.BookQueryResult
import com.plcoding.jwtauthktorandroid.data.books.BookRepository
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun BookScreen(
//    navigator: DestinationsNavigator,
    viewModel: BookViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${state.books.size} =Books")
        Button(onClick = { viewModel.onEvent(BookUiEvent.GetBooks) }) {
            Text(text = "Load Books")
        }
        LazyColumn {
            items(state.books) { book ->
                BookItem(book)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF, showSystemUi = true)
@Composable
fun BookScreenPreview() {

    BookScreen(BookViewModel(object : BookRepository {
        override suspend fun getBooks(): BookQueryResult<List<Book>> {
            return BookQueryResult.Success(
                listOf(Book(1, "Book 1", "Ryan", 1, "2401"), )
            )
        }

        override suspend fun getBookById(id: Int): BookQueryResult<Book> {
            TODO("Not yet implemented")
        }

        override suspend fun addBook(book: Book): BookQueryResult<Book> {
            TODO("Not yet implemented")
        }

        override suspend fun updateBook(book: Book): BookQueryResult<Book> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteBook(id: Int): BookQueryResult<Unit> {
            TODO("Not yet implemented")
        }
    }))
}

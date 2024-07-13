package com.plcoding.jwtauthktorandroid.ui.books

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.jwtauthktorandroid.data.books.Book
import com.plcoding.jwtauthktorandroid.data.books.BookQueryResult
import com.plcoding.jwtauthktorandroid.data.books.BookRepository
import com.plcoding.jwtauthktorandroid.ui.destinations.BookFormScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun BookScreen(
    navigator: DestinationsNavigator,
    viewModel: BookViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val bookResults by viewModel.bookResults.collectAsState(initial = BookQueryResult.Idle())

    LaunchedEffect(viewModel, context) {
        viewModel.onEvent(BookUiEvent.GetBooks)
    }


    LaunchedEffect(viewModel, context) {
        viewModel.bookResults.collect { result ->
            when (result) {
                is BookQueryResult.Success -> {
                    Toast.makeText(
                        context,
                        "${result.data?.size} - Books loaded",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is BookQueryResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        "An unknown error occurred",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is BookQueryResult.Error -> {
                    Toast.makeText(
                        context,
                        "An error occurred",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is BookQueryResult.Idle -> TODO()
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val books = if (bookResults is BookQueryResult.Success) {
                (bookResults as BookQueryResult.Success<List<Book>>).data ?: emptyList()
            } else {
                emptyList()
            }
            Text(text = "${books.size} Books")
            Button(onClick = { viewModel.onEvent(BookUiEvent.GetBooks) }) {
                Text(text = "Load Books")
            }
            Button(onClick = {
                navigator.navigate(BookFormScreenDestination())
            }) {
                Text(text = "Create New Book")
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
                    items(books) { book ->
                        BookItem(
                            book,
                            onEdit = {
                                navigator.navigate(BookFormScreenDestination(bookId = it.id.toString()))
                            },
                            onDelete = {
                                viewModel.onEvent(BookUiEvent.DeleteBook(it.id!!))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF, showSystemUi = true)
@Composable
fun BookScreenPreview() {

    BookScreen(EmptyDestinationsNavigator, BookViewModel(object : BookRepository {
        override suspend fun getBooks(): BookQueryResult<List<Book>> {
            return BookQueryResult.Success(
                listOf(Book(1, "Book 1", "Ryan", 1, "2401"))
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

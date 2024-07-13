package com.plcoding.jwtauthktorandroid.ui.bookForm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.jwtauthktorandroid.data.books.Book
import com.plcoding.jwtauthktorandroid.data.books.BookQueryResult
import com.plcoding.jwtauthktorandroid.data.books.BookRepository
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator


@Composable
@Destination
fun BookFormScreen(
    navigator: DestinationsNavigator,
    viewModel: BookFormViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state
    val authors = viewModel.authors
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Create New Book") },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = state.title,
                onValueChange = { viewModel.onEvent(BookFormUiEvent.TitleChanged(it)) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            var expanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = state.author,
                    onValueChange = {},
                    label = { Text("Author") },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                            contentDescription = null,
                            Modifier.clickable { expanded = !expanded }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    authors.forEach { author ->
                        DropdownMenuItem(onClick = {
                            viewModel.onEvent(BookFormUiEvent.AuthorChanged(author))
                            expanded = false
                        }) {
                            Text(text = author)
                        }
                    }

                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.price?.toString() ?: "",
                onValueChange = { viewModel.onEvent(BookFormUiEvent.PriceChanged(it)) },
                label = { Text("Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.launchDate,
                onValueChange = { viewModel.onEvent(BookFormUiEvent.LaunchDateChanged(it)) },
                label = { Text("Launch Date") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.onEvent(BookFormUiEvent.SaveBook) },
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Create Book")
                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF, showSystemUi = true)
@Composable
fun BookFormScreenPreview() {
    BookFormScreen(EmptyDestinationsNavigator, BookFormViewModel(
        object : BookRepository {
            override suspend fun getBooks(): BookQueryResult<List<Book>> {
                TODO("Not yet implemented")
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
        }
    ))
}
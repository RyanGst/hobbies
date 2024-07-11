package com.plcoding.jwtauthktorandroid.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.jwtauthktorandroid.data.auth.AuthRepository
import com.plcoding.jwtauthktorandroid.data.auth.AuthResult
import com.plcoding.jwtauthktorandroid.ui.destinations.AuthScreenDestination
import com.plcoding.jwtauthktorandroid.ui.destinations.BookScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.flow.collect

@Composable
@Destination(start = true)
fun AuthScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when(result) {
                is AuthResult.Authorized -> {
                    navigator.navigate(BookScreenDestination) {
                        popUpTo(AuthScreenDestination.route) {
                            inclusive = true
                        }
                    }
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(
                        context,
                        "You're not authorized",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        "An unknown error occurred",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = state.signInUsername,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.SignInUsernameChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Username")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.signInPassword,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.onEvent(AuthUiEvent.SignIn)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign in")
        }
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF, showSystemUi = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen(EmptyDestinationsNavigator, viewModel = MainViewModel(object : AuthRepository {
        override suspend fun signUp(username: String, password: String): AuthResult<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun signIn(username: String, password: String): AuthResult<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun authenticate(): AuthResult<Unit> {
            TODO("Not yet implemented")
        }
    }))
}
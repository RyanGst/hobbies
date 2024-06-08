package com.ryangst.hobbies.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ryangst.hobbies.ui.screens.AuthScreen

@Composable
fun AppHobbies(
    navController: NavHostController = rememberNavController()
) {
    val modifier = Modifier

    NavHost(navController = navController, startDestination = "", modifier = modifier) {
        composable(route = "auth") {
            AuthScreen()
        }
    }
}
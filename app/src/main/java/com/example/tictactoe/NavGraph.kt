package com.example.tictactoe

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable

fun SetupNavGraph(
    navController: NavHostController
    //viewModel: GameViewModel
)
{
    NavHost(
        navController = navController,
        startDestination = Screen.StartScreen.route)
    {

        composable(route = Screen.StartScreen.route)
        {
            StartScreen(navController)
        }

        composable(
            route = Screen.GameScreen.route,
            arguments = listOf(navArgument("size")
            {
                type = NavType.IntType
            })

        )
        {


            var size = it.arguments?.getInt("size")
            val viewModel = GameViewModel(size!!)
            GameScreen(viewModel = viewModel, size)
        }


    }
}
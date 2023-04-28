package com.example.tictactoe

sealed class Screen(val route: String)
{
    object StartScreen: Screen(route = "start_screen")
    object GameScreen: Screen(route = "game_screen/{size}")
}

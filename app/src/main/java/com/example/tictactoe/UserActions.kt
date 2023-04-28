package com.example.tictactoe

//sealed class definiuje nam wszystkie typy klas jakie może przyjąć UserActions (wszystkie akcje użytkownika)
sealed class UserActions
{
    object PlayAgainClicked: UserActions()
    //object StartGame: UserActions()

    //klasa przechowuje cellnr wiec  musi byc data class
    data class BoardTapped(val cellNr: Int): UserActions()
}

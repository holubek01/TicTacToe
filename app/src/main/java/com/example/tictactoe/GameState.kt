package com.example.tictactoe

//gdy stan będzie zmienianmy to automatycznie będzie odświeżać się intefejs użytkownika
data class GameState(
    //val boardSize: Int = 0,
    val playerCircleCount: Int = 0,
    val playerCrossCount: Int = 0,
    val drawsCount: Int = 0,
    val whoseTurn: String = "Player 'O' turn",
    val currentTurn: BoardCellValue = BoardCellValue.CIRCLE,
    val victoryType: VictoryType = VictoryType.NONE,
    val hasWon : Boolean = false,

    val lineNr: Int = 0
)

enum class BoardCellValue {
    CIRCLE,
    CROSS,
    NONE
}

enum class VictoryType {
    /*
    HORIZONTALLINE1,
    HORIZONTALLINE2,
    HORIZONTALLINE3,

    VERTICALLINE1,
    VERTICALLINE2,
    VERTICALLINE3,

    DIAGONALLINE1,
    DIAGONALLINE2,

     */

    HORIZONTALLINE,
    VERTICALLINE,
    DIAGONALLINE1,
    DIAGONALLINE2,
    NONE

}

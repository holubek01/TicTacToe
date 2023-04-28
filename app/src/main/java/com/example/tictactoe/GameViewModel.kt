package com.example.tictactoe

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel(boardSize: Int): ViewModel() {

    //każda zmiana stanu będzie rejestrowana przez obserwatora
    //by pozwala połączyć state z mutowalnym stanem (musimy tego użyć aby było na bieżąco aktualizowane)
    var state by mutableStateOf(GameState())
    var boardSize = boardSize


    val boardItems: MutableMap<Int, BoardCellValue> = mutableMapOf<Int, BoardCellValue>().apply {
        for (i in 1..boardSize*boardSize) {
            put(i, BoardCellValue.NONE)
        }
    }

    fun onAction(action: UserActions)
    {

        when(action)
        {
            is UserActions.BoardTapped -> {
                //dodaje np wartość 'CIRCLE' do pola cellNr
                addValueToBoard(action.cellNr)
            }

        UserActions.PlayAgainClicked -> {

            //zresetować można tylko pod koniec
            //(zasada - gramy do konca)
            if(isFull() || state.hasWon) resetGame()
            }


        }
    }



    private fun resetGame() {
        boardItems.forEach { (i,_) ->
            boardItems[i] = BoardCellValue.NONE
        }

        println("popo")

        state = state.copy(
            whoseTurn = "Player 'O' turn",
            currentTurn = BoardCellValue.CIRCLE,
            victoryType = VictoryType.NONE,
            hasWon = false,
            lineNr = 0
        )
    }

    private fun addValueToBoard(cellNr: Int) {
        if(boardItems[cellNr]!=BoardCellValue.NONE) return

        if(state.currentTurn == BoardCellValue.CIRCLE)
        {
            boardItems[cellNr] = BoardCellValue.CIRCLE

            if (checkWin(BoardCellValue.CIRCLE))
            {
                state = state.copy(
                    whoseTurn = "Player O won!",
                    playerCircleCount = state.playerCircleCount+1,
                    currentTurn = BoardCellValue.NONE,
                    hasWon = true
                )

            }

            else if(isFull())
            {
                state = state.copy(
                    whoseTurn = "Game draw",
                    drawsCount = state.drawsCount+1
                )
            }
            else
            {
                //kopiujemy stan i zmieniamy tylko podane wartości
                state = state.copy(
                    whoseTurn = "Player 'X' turn",
                    currentTurn = BoardCellValue.CROSS
                )
            }


        }

        else if(state.currentTurn == BoardCellValue.CROSS)
        {
            boardItems[cellNr] = BoardCellValue.CROSS

            if (checkWin(BoardCellValue.CROSS))
            {
                state = state.copy(
                    whoseTurn = "Player X won!",
                    playerCrossCount = state.playerCrossCount+1,
                    currentTurn = BoardCellValue.NONE,
                    hasWon = true
                )

            }

            else if(isFull())
            {
                state = state.copy(
                    whoseTurn = "Game draw",
                    drawsCount = state.drawsCount+1
                )
            }

            else
            {
                state = state.copy(
                    whoseTurn = "Player 'O' turn",
                    currentTurn = BoardCellValue.CIRCLE
                )
            }
        }



    }

    private fun checkWin(value: BoardCellValue): Boolean {

        //uniwersalne
        var strike = 0

        //pionowe
        for (i in 1..boardSize)
        {
            for (j in i..boardSize*(boardSize-1)+i step boardSize)
            {
                println("elo")
                println(j)
                println(boardItems[j])
                println(value)
                if (boardItems[j] != value) {
                    strike = 0
                    break}
                else strike++
            }

            if (strike == boardSize)
            {
                println("tom1")
                state = state.copy(
                    victoryType = VictoryType.VERTICALLINE,
                    lineNr = i)
                return true
            }

            strike = 0

            for (j in ((i-1)*boardSize + 1)..(i*boardSize))
            {
                if (boardItems[j] != value) {
                    strike = 0
                    break}
                else strike++
            }

            if (strike == boardSize)
            {
                println("tom2")
                println(i)
                state = state.copy(
                    victoryType = VictoryType.HORIZONTALLINE,
                    lineNr = i)
                return true
            }
        }


        strike = 0

        for(i in 1..boardSize*boardSize step (boardSize+1))
        {
            if (boardItems[i] != value) {
                strike = 0
                break}
            else strike++
        }

        if (strike == boardSize)
        {
            state = state.copy(
                victoryType = VictoryType.DIAGONALLINE1,)
            return true
        }

        strike=0

        for(i in boardSize..boardSize*(boardSize-1)+1 step (boardSize-1))
        {
            if (boardItems[i] != value) {
                strike = 0
                break}
            else strike++
        }

        if (strike == boardSize)
        {
            state = state.copy(
                victoryType = VictoryType.DIAGONALLINE2,)
            return true
        }

        return false


        /*
        if(boardItems[1] == value && boardItems[2] == value && boardItems[3] == value)
        {
            //state = state.copy(victoryType = VictoryType.HORIZONTALLINE1)
            state = state.copy(
                victoryType = VictoryType.HORIZONTALLINE,
                lineNr = 1)
            return true
        }

        if(boardItems[4] == value && boardItems[5] == value && boardItems[6] == value)
        {
            state = state.copy(victoryType = VictoryType.HORIZONTALLINE2)
            return true
        }

        if(boardItems[7] == value && boardItems[8] == value && boardItems[9] == value)
        {
            state = state.copy(victoryType = VictoryType.HORIZONTALLINE3)
            return true
        }

        if(boardItems[1] == value && boardItems[4] == value && boardItems[7] == value)
        {
            state = state.copy(victoryType = VictoryType.VERTICALLINE1)
            return true
        }

        if(boardItems[2] == value && boardItems[5] == value && boardItems[8] == value)
        {
            state = state.copy(victoryType = VictoryType.VERTICALLINE2)
            return true
        }

        if(boardItems[3] == value && boardItems[6] == value && boardItems[9] == value)
        {
            state = state.copy(victoryType = VictoryType.VERTICALLINE3)
            return true
        }

        if(boardItems[1] == value && boardItems[5] == value && boardItems[9] == value)
        {
            state = state.copy(victoryType = VictoryType.DIAGONALLINE1)
            return true
        }

        if(boardItems[7] == value && boardItems[5] == value && boardItems[3] == value)
        {
            state = state.copy(victoryType = VictoryType.DIAGONALLINE2)
            return true
        }
        return false

         */
    }

    private fun isFull(): Boolean {


        if(boardItems.containsValue(BoardCellValue.NONE)) return false
        return true

    }
}
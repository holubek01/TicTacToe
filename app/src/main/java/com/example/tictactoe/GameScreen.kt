package com.example.tictactoe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//TODO: dla większych plansz zmniejszyć paddingi

//interfejs użytkownika
//musimy jako parametr przekazać vieModel aby można było go połączyć ze stanem
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameScreen(viewModel: GameViewModel, size: Int?)
{

    var state = viewModel.state

    //state = state.copy(boardSize = size!!)


    Column(modifier = androidx.compose.ui.Modifier
        .fillMaxSize()
        .padding(horizontal = 10.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly)
        {
            
            Row(modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                //spaceBetween nie doda wolnej przestrzeni na końcu
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
            {
                //Text(text = "Player '0': ${state.playerCircleCount}", fontSize = 16.sp)

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    CircleIcon()

                    Text(
                        text = "Player: ",
                        fontSize = 22.sp
                    )

                    Text(
                        text = state.playerCircleCount.toString(),
                        fontSize = 22.sp
                    )
                }

                //aby aktualizowało się to state.drawCount
                Text(text = "Draw: ${state.drawsCount} ", fontSize = 22.sp)
                //Text(text = "Player 'X': ${state.playerCrossCount}", fontSize = 16.sp)

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    CrossIcon()

                    Text(
                        text = "Player: ",
                        fontSize = 22.sp
                    )

                    Text(
                        text = state.playerCrossCount.toString(),
                        fontSize = 22.sp
                    )
                }
            }

            Text(
                text = "Tic Tac Toe",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                color = Color.Black
            )

            //plansza
            Box(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)    //określa że taka sama wysokość i szerokość
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))        //rogi zaokrąglone i zacieniowane
                    .background(Color.LightGray),

                contentAlignment = Alignment.Center
            )

            {
                //wewnątrz Box jest komponent BoardBase który odpowiada za rysowanie na planszy linii, kółek i krzyżyków
                BoardBase(size)

                //Box jedynie definiował nam wygląd planszy, musimy jeszcze dostać się jakoś do tych pól
                LazyVerticalGrid(
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    columns = GridCells.Fixed(size!!)
                )
                {
                    viewModel.boardItems.forEach { (cellNr, boardCellValue) ->
                        //każde pole jest reprezentowane przez column
                        item {
                            Column(
                                modifier = androidx.compose.ui.Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)

                                    //gdy klikniemy to wykona się onAction
                                    .clickable(
                                        //nie chcemy aby podczas kliknięcia podświetla się kwadrat
                                        interactionSource = MutableInteractionSource(),
                                        indication = null
                                    ) {
                                        viewModel.onAction(
                                            UserActions.BoardTapped(cellNr)
                                        )
                                    },

                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                //animacja podczas rysowania symboli
                                AnimatedVisibility(
                                    visible = viewModel.boardItems[cellNr] != BoardCellValue.NONE,

                                    //experimental - jest ryzyko ze nie zadziała
                                    enter = scaleIn(tween(100))
                                ) {
                                    //w zależności od rodzaju jest rysowane kółko albo krzyżyk
                                    if(boardCellValue == BoardCellValue.CIRCLE)
                                    {
                                        Circle(size)
                                    }
                                    else if(boardCellValue == BoardCellValue.CROSS)
                                    {
                                        Cross(size)
                                    }
                                }




                            }
                        }
                    }
                }

                Column(
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    //jesli hasWon to narysuj wygrywającą linie
                    AnimatedVisibility(
                        visible = state.hasWon,
                        enter = fadeIn(tween(2000))
                    ) {

                        DrawVicotryLine(state = state, size)

                    }
                }



            }

            //kolejka i button
            Row(
                modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                if(state.whoseTurn.contains("draw"))
                {
                    Text(
                        text = state.whoseTurn,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = androidx.compose.ui.Modifier.weight(1f)
                    )
                }
                else
                {
                    Text(
                        text = "Player",
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic
                    )
                    if ("O" in state.whoseTurn) {
                        CircleIcon()
                    } else if ("X" in state.whoseTurn) {
                        CrossIcon()
                    }

                    if(state.whoseTurn.contains("won"))
                    {
                        Text(
                            text = "won!",
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Italic,
                            modifier = androidx.compose.ui.Modifier.weight(1f)
                        )
                    }
                    else
                    {
                        Text(
                            text = "turn",
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Italic,
                            modifier = androidx.compose.ui.Modifier.weight(1f)
                        )
                    }
                }





                Button(
                    onClick = { viewModel.onAction(UserActions.PlayAgainClicked) },
                    shape = RoundedCornerShape(5.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp),   //cień 5px
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Text(text = "Play Again", fontSize = 16.sp)
                }
            }

        }
    //Log.d("Args", it.arguments?.getInt("size").toString())
}


@Composable
fun DrawVicotryLine(state: GameState, size: Int?)
{
    when(state.victoryType)
    {
        /*VictoryType.HORIZONTALLINE2 -> WinHorizontalLine2()
        VictoryType.HORIZONTALLINE3 -> WinHorizontalLine3()
        VictoryType.VERTICALLINE1 -> WinVerticalLine1()
        VictoryType.VERTICALLINE2 -> WinVerticalLine2()
        VictoryType.VERTICALLINE3 -> WinVerticalLine3()

         */
        VictoryType.DIAGONALLINE1 -> WinDiagonalLine1()
        VictoryType.DIAGONALLINE2 -> WinDiagonalLine2()

        VictoryType.HORIZONTALLINE -> WinHorizontalLine(state.lineNr, boardSize = size)
        VictoryType.VERTICALLINE -> WinVerticalLine(state.lineNr, size)
        else -> {}
    }
}



//pozwala na preview ekranu
@Preview
@Composable
fun Prev()
{
    GameScreen(viewModel = GameViewModel(4), size = 4)
}
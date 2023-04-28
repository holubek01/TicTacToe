package com.example.tictactoe

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


//TODO: przy większej planszy będzie więcej linii oraz w innnych miejsach,
//rozmiary komponentów też będą inne
@Composable
fun BoardBase(boardSize: Int?)
{

    Canvas(
        modifier = androidx.compose.ui.Modifier
            .size(400.dp)
            //.padding(10.dp)
        )
        {

            //trzeba uzależnić od size

            for(i in 1..boardSize!!-1)
            {
                //jedna z pionowych linii
                drawLine(
                    color = Color.Black,
                    strokeWidth = 5f,           //grubość
                    cap = StrokeCap.Round,      //zaokrąglone zakończenie linii
                    start = Offset(x = size.width*i/boardSize, y=0f),       //linia jest w 1/3 szerokości boarda
                    end = Offset(x = size.width*i/boardSize, y=size.height)
                )

                drawLine(
                    color = Color.Black,
                    strokeWidth = 5f,
                    start = Offset(x = 0f, y=size.height*i/boardSize),
                    end = Offset(x = size.width, y=size.height*i/boardSize)
                )


            }
        }
}


//gdy funkcja zostanie wywołana to narysuje krzyżyk
@Composable
fun Cross(boardSize: Int?)
{
    val symbolSize = (70.0/(boardSize!!.toFloat()/3.0))
    val symbolWeight = (20.0/(boardSize.toFloat()/3.0))
    val symbolPadding = (5.0/(boardSize.toFloat()/3.0))


    Canvas(modifier = androidx.compose.ui.Modifier
        .size(symbolSize.dp)       //60 dp bo całe pole ma 300 dp a 1 pole 100dp
        //.padding(symbolPadding.dp)
    )
    {
        drawLine(
            color = com.example.tictactoe.ui.theme.Cross,
            strokeWidth = symbolWeight.toFloat(),
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height)
        )

        drawLine(
            color = com.example.tictactoe.ui.theme.Cross,
            strokeWidth = symbolWeight.toFloat(),
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        )
    }
}


@Composable
fun Circle(boardSize: Int?)
{
    val symbolSize = (70.0/(boardSize!!.toFloat()/3.0))
    val symbolWeight = (20.0/(boardSize.toFloat()/3.0))
    val symbolPadding = (5.0/(boardSize.toFloat()/3.0))

    println("chuj")
    println(symbolSize)
    println(symbolWeight)
    println(symbolPadding)

    Canvas(modifier = androidx.compose.ui.Modifier
        .size(symbolSize.dp)       //60 dp bo całe pole ma 300 dp a 1 pole 100dp
        //.padding(symbolPadding.dp)
        )
    {
        drawCircle(
            color = com.example.tictactoe.ui.theme.Circle,
            style = Stroke(width = symbolWeight.toFloat())         //okrąg a nie koło
        )
    }
}

@Composable
fun CircleIcon()
{
    Canvas(modifier = androidx.compose.ui.Modifier
        .size(34.dp)       //60 dp bo całe pole ma 300 dp a 1 pole 100dp
        .padding(7.dp))
    {
        drawCircle(
            color = com.example.tictactoe.ui.theme.Circle,
            style = Stroke(width = 10f)         //okrąg a nie koło
        )
    }
}


@Composable
fun CrossIcon()
{
    Canvas(modifier = androidx.compose.ui.Modifier
        .size(30.dp)       //60 dp bo całe pole ma 300 dp a 1 pole 100dp
        .padding(7.dp)
    )
    {
        drawLine(
            color = com.example.tictactoe.ui.theme.Cross,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height)
        )

        drawLine(
            color = com.example.tictactoe.ui.theme.Cross,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        )
    }
}


@Composable
fun WinHorizontalLine(lineNr: Int,boardSize: Int?)
{
    Canvas(modifier = androidx.compose.ui.Modifier
        .size(400.dp)
       // .padding(5.dp)
        )
    {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height*((2*lineNr)-1)/((boardSize!!*2).toFloat())),
            end = Offset(x = size.width, y = size.height*((2*lineNr)-1)/((boardSize!!*2).toFloat()))
        )
    }
}



@Composable
fun WinVerticalLine(lineNr: Int,boardSize: Int?)
{
    Canvas(modifier = androidx.compose.ui.Modifier
        .size(400.dp)
    )
    {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = size.width*((2*lineNr)-1)/(boardSize!!*2), y = 0f),
            end = Offset(x = size.width*(2*lineNr-1)/(boardSize!!*2), y = size.height)
        )
    }
}



@Composable
fun WinDiagonalLine1()
{
    Canvas(modifier = androidx.compose.ui.Modifier
        .size(400.dp)
    )
    {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height)
        )
    }
}


@Composable
fun WinDiagonalLine2()
{
    Canvas(modifier = androidx.compose.ui.Modifier
        .size(400.dp)
    )
    {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        )
    }
}





@Preview
@Composable
fun Previews()
{
    BoardBase(3)
    /*
    BoardBase()
    Cross()
    Circle()
    WinHorizontalLine1()
    WinHorizontalLine2()
    WinHorizontalLine3()

    WinVerticalLine1()
    WinVerticalLine2()
    WinVerticalLine3()

    WinDiagonalLine1()
    WinDiagonalLine2()
    */

}
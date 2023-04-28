package com.example.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun SliderExample(onSliderValueChanged: (Float) -> Unit) {
    val sliderValue = remember { mutableStateOf(3f) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Slider(
                modifier = Modifier
                    .weight(0.85f)
                    .padding(10.dp),
                value = sliderValue.value,
                onValueChange = {
                    sliderValue.value = it
                    onSliderValueChanged(it)
                },
                valueRange = 3f..20f,
                steps = 17,
                colors = SliderDefaults.colors(
                    activeTrackColor = Color.Cyan,
                    inactiveTrackColor = Color.LightGray,
                    thumbColor = Color.Cyan
                )
            )
            Text(
                text = sliderValue.value.toInt().toString(),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }
    }
}

@Composable
fun StartScreen(navController: NavController)
{
    var sliderValue by remember { mutableStateOf(3f) }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly){


        Text(
            text = "Tic Tac Toe",
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            color = Color.Cyan
        )

        Text(
            text = "Wybierz rozmiar planszy",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        SliderExample(onSliderValueChanged = { value ->
            sliderValue = value
        })

        Button(
            onClick = { navController.navigate(route = "game_screen/${sliderValue.toInt()}") },
            shape = RoundedCornerShape(5.dp),
            elevation = ButtonDefaults.buttonElevation(5.dp),   //cień 5px
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Cyan
            )
        ) {
            Text(text = "Start", fontSize = 28.sp)
        }


    }
}

@Preview
@Composable
fun Prevv()
{
    StartScreen( navController = rememberNavController())
}

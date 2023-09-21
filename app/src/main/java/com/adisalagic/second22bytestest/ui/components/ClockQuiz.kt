package com.adisalagic.second22bytestest.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adisalagic.second22bytestest.R
import com.adisalagic.second22bytestest.ui.viewmodels.ClockQuizViewModel
import com.adisalagic.second22bytestest.utils.toTimer
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ClockQuiz() {
    val model = viewModel<ClockQuizViewModel>(LocalViewModelStoreOwner.current!!)
    val data by model.state.collectAsState()
    BackHandler {
        return@BackHandler
    }
    LaunchedEffect(key1 = data.quizState) {
        if (data.quizState == ClockQuizViewModel.QuizState.SHOWING_CITIES) {
            model.resetGame()
        }
    }
    AnimatedVisibility(visible = data.quizState == ClockQuizViewModel.QuizState.NOT_STARTED, enter = fadeIn(), exit = fadeOut()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.sdp, top = 350.sdp, end = 10.sdp, bottom = 10.sdp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "match the\ntime zone\nwith the city".uppercase(),
                style = TextStyle(
                    fontSize = 30.ssp,
                    lineHeight = 30.ssp,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(50.sdp))
            DefaultGradientButton(onClick = { model.nextStage() }, text = "PLAY")
        }
    }
    AnimatedVisibility(visible = data.quizState == ClockQuizViewModel.QuizState.DIFFICULTY_CHOOSE, enter = fadeIn(), exit = fadeOut()) {
        Column(
            modifier = Modifier.padding(horizontal = 10.sdp, vertical = 40.sdp),
            verticalArrangement = Arrangement.spacedBy(20.sdp, Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultGradientButton(
                onClick = {
                    model.setDifficulty(ClockQuizViewModel.Difficulty.CASUAL);
                    model.nextStage()
                },
                text = "CASUAL"
            )
            DefaultGradientButton(
                onClick = {
                    model.setDifficulty(ClockQuizViewModel.Difficulty.TIME_ATTACK);
                    model.nextStage()
                },
                text = "TIME ATTACK"
            )
        }
    }
    AnimatedVisibility(visible = data.quizState == ClockQuizViewModel.QuizState.SHOWING_CITIES, enter = fadeIn(), exit = fadeOut()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.sdp)
                .padding(top = 300.sdp),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(10.sdp, Alignment.CenterVertically),
                horizontalArrangement = Arrangement.spacedBy(10.sdp, Alignment.CenterHorizontally)
            ) {
                items(data.pairs) {
                    ClockTile(
                        id = it.first,
                        enabled = false,
                        it.second,
                        data.quizState == ClockQuizViewModel.QuizState.SHOWING_CITIES,
                        onClick = {})
                }
            }
        }
    }
    AnimatedVisibility(visible = data.quizState == ClockQuizViewModel.QuizState.IN_PROGRESS, enter = fadeIn(), exit = fadeOut()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ClockTile(
                id = data.clickedId,
                enabled = false,
                name = data.clickedString,
                isVisible = false,
                size = 150.sdp,
                onClick = {}
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.sdp, end = 10.sdp, bottom = 30.sdp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column {
                AnswerTextField(onValueChanged = {
                    model.setAnswer(it)
                })
                Spacer(modifier = Modifier.height(20.sdp))
                DefaultGradientButton(
                    enabled = data.answer.isNotBlank(),
                    onClick = {
                        model.checkAnswer()
                    },
                    text = "OK"
                )
            }
        }
    }
    AnimatedVisibility(visible = data.quizState == ClockQuizViewModel.QuizState.ENDED, enter = fadeIn(), exit = fadeOut()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.sdp, top = 350.sdp, end = 10.sdp, bottom = 10.sdp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Ваш счет: ${data.score}",
                style = TextStyle(
                    fontSize = 30.ssp,
                    lineHeight = 30.ssp,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(50.sdp))
            DefaultGradientButton(onClick = { model.nextStage() }, text = "ЗАНОВО")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.sdp), contentAlignment = Alignment.TopCenter
    ) {
        if (data.timer != 0) {
            Text(text = data.timer.toTimer(), fontSize = 18.ssp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
    }
}
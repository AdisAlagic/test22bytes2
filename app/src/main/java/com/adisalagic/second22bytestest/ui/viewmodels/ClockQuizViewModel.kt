package com.adisalagic.second22bytestest.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adisalagic.second22bytestest.utils.giveRandom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class ClockQuizViewModel : ViewModel() {
    private val _state =
        MutableStateFlow(ClockQuizDataState())
    val state = _state.asStateFlow()

    private var timerJob = Job()
    private var timerScope = CoroutineScope(timerJob)

    private val data get() = _state.value

    fun resetGame() {
        val list = giveRandom(6).shuffled()
        _state.update {
            it.copy(
                pairs = list,
                quizState = QuizState.SHOWING_CITIES,
                question = Random.nextInt(0, list.size)
            )
        }
        recreateJob()
        timerScope.launch {
            startTimer(10) {
                _state.update { it.copy(quizState = QuizState.IN_PROGRESS) }
                startTimeAttack()
            }
        }

    }

    fun setAnswer(answer: String) {
        _state.update { it.copy(answer = answer) }
    }

    fun checkAnswer() {
        if (data.answer.contains(data.clickedString, true)) {
            resetGame()
            _state.update { it.copy(score = data.score + 1) }
        }else {
            nextStage()
        }
    }

    private fun recreateJob() {
        timerJob.cancel()
        timerJob = Job()
        timerScope = CoroutineScope(timerJob)
    }

    private fun startTimeAttack() {
        if (data.difficulty != Difficulty.TIME_ATTACK) {
            return
        }
        recreateJob()
        timerScope.launch {
            startTimer(30) {
                nextStage()
            }
        }
    }

    private suspend fun CoroutineScope.startTimer(seconds: Int, onEnd: () -> Unit): CoroutineScope {
        var sec = seconds
        while (sec != 0 && isActive) {
            _state.update { it.copy(timer = sec) }
            delay(1000)
            sec--
        }
        _state.update { it.copy(timer = sec) }
        onEnd()
        return this
    }

    private fun stageSelect(quizState: QuizState) {
        _state.update { it.copy(quizState = quizState) }
    }

    fun setDifficulty(difficulty: Difficulty) {
        _state.update { it.copy(difficulty = difficulty) }
    }

    fun nextStage() {
        recreateJob()
        _state.update { it.copy(timer = 0) }
        stageSelect(
            when (data.quizState) {
                QuizState.NOT_STARTED -> QuizState.DIFFICULTY_CHOOSE
                QuizState.DIFFICULTY_CHOOSE -> QuizState.SHOWING_CITIES
                QuizState.SHOWING_CITIES -> QuizState.IN_PROGRESS
                QuizState.IN_PROGRESS -> QuizState.ENDED
                QuizState.ENDED -> QuizState.DIFFICULTY_CHOOSE
            }
        )

    }

    data class ClockQuizDataState(
        /**
         * Пары часов и ответов на них. Хранят DrawableRes и Ответ
         */
        val pairs: List<Pair<Int, String>> = emptyList(),
        val quizState: QuizState = QuizState.NOT_STARTED,
        val difficulty: Difficulty = Difficulty.EMPTY,
        val timer: Int = 0,
        val question: Int = -1,
        val answer: String = "",
        val score: Int = 0
    ) {
        val clickedId get() = pairs[question].first
        val clickedString get() = pairs[question].second
    }
    enum class QuizState {
        NOT_STARTED,
        DIFFICULTY_CHOOSE,
        SHOWING_CITIES,
        IN_PROGRESS,
        ENDED
    }

    enum class Difficulty {
        EMPTY,
        CASUAL,
        TIME_ATTACK
    }
}
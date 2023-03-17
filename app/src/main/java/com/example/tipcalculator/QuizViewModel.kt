package com.example.tipcalculator

import android.media.MediaPlayer
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.absoluteValue

class QuizViewModel : ViewModel() {

    val questions = listOf(
        Question.Question(R.string.quest1, true, false),
        Question.Question(R.string.quest2, false, false),
        Question.Question(R.string.quest3, true, false),
        Question.Question(R.string.quest4, true, false),
        Question.Question(R.string.quest5, false, false)
    )

    private var _index = MutableLiveData(0)
    val index: LiveData<Int>
        get() = _index

    private var _correct = 0
    val correct: Int
        get() = _correct

    private var _wrong = 0
    val wrong: Int
        get() = _wrong

    private var _gameWon = MutableLiveData(false)
    val gameWon: LiveData<Boolean>
        get() = _gameWon

     var currentQuestionAnswer = questions[_index.value?:0].answer
        get() = currentQuestionAnswer

     var currentQuestionText = questions[_index.value?:0].question
        get() = currentQuestionText

    var currentQuestionCheatStatus = questions[_index.value?:0].cheated
        get() = currentQuestionCheatStatus

    fun setCheatedStatusForCurrentQuestion(status: Boolean){
        currentQuestionCheatStatus = status
    }
    fun checkIfGameWon() {
        if (_correct == 3) {
            _gameWon.value = true
        }
    }

    fun nextQuestion() {
        val currentIndex = index.value ?:0
        _index.value = currentIndex + 1
    }

    fun checkAnswer( answer: Boolean):Boolean {
        if (questions[_index.value?:0].answer == answer ) {
            return true
        }
        else {
            _wrong++
            return false
        }
    }


}
package com.example.tipcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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

    private var currentQuestionAnswer = false
        get() = currentQuestionAnswer

    private var currentQuestionText = MutableLiveData(questions[index.value]).toString()
        get() = currentQuestionText

    private var currentQuestionCheatStatus = false
        get() = currentQuestionCheatStatus


}
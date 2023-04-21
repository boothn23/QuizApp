package com.example.tipcalculator

import android.util.Log
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

    val currentQuestionAnswer: Boolean
        get() = questions[_index.value ?: 0].answer

    val currentQuestionText: Int
        get() = questions[_index.value ?: 0].question

    val currentQuestionCheatStatus: Boolean
        get() = questions[_index.value ?: 0].cheated

    fun setCheatedStatusForCurrentQuestion(status: Boolean) {
        questions[_index.value ?: 0].cheated = status
    }

    fun checkIfGameWon() {
        if (_correct == 3) {
            _gameWon.value = true
        }
    }
    fun reset() {
        _correct = 0
        _wrong = 0
        _index.value = 0
        questions[0].cheated = false
        questions[1].cheated = false
        questions[2].cheated = false
        questions[3].cheated = false
        questions[4].cheated = false
    }

    fun nextQuestion() {
        val currentIndex = index.value ?: 0
        if (currentIndex < questions.size - 1) {
            _index.value = currentIndex + 1
        } else
            _index.value = 0

    }



    fun checkAnswer(answer: Boolean): Boolean {
        if (currentQuestionAnswer == answer && currentQuestionCheatStatus) {
            Log.i("MainActivity", "${_correct}")
            return true
        }
        else if (currentQuestionAnswer == answer) {
            _correct += 1
            checkIfGameWon()
            return true
        }
        else {
            _wrong += 1
            return false
        }
        }

    }

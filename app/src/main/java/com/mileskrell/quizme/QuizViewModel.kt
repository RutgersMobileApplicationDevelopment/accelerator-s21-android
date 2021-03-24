package com.mileskrell.quizme

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class QuizViewModel : ViewModel() {

    companion object {
        const val TIMER_DURATION = 60_000L
        const val TICK_DURATION = 1_000L
    }

    private val _problem = MutableLiveData<Problem>()
    val problem: LiveData<Problem>
        get() = _problem

    private val _numCorrect = MutableLiveData(0)
    val numCorrect: LiveData<Int>
        get() = _numCorrect

    private val _secondsRemaining = MutableLiveData<Long>()
    val secondsRemaining: LiveData<Long>
        get() = _secondsRemaining

    val secondsRemainingString = Transformations.map(secondsRemaining) {
        DateUtils.formatElapsedTime(it)
    }

    private val _eventGameOver = MutableLiveData(false)
    val eventGameOver: LiveData<Boolean>
        get() = _eventGameOver

    fun onEventGameOverFinished() {
        _eventGameOver.value = false
    }

    private val timer = object : CountDownTimer(TIMER_DURATION, TICK_DURATION) {
        override fun onTick(millisUntilFinished: Long) {
            _secondsRemaining.value = millisUntilFinished / 1000
        }

        override fun onFinish() {
            _eventGameOver.value = true
        }
    }

    init {
        showNewProblem()
        timer.start()
    }

    fun showNewProblem() {
        val num1 = Random.nextInt(1, 10)
        val num2 = Random.nextInt(1, 10)
        val op = Operation.values().random()
        _problem.value = Problem(op, num1, num2)
    }

    fun submitAnswer(answer: Int) {
        if (answer == problem.value!!.answer) {
            _numCorrect.value = numCorrect.value!! + 1
        }
    }
}

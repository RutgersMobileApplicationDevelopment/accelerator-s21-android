package com.mileskrell.quizme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.mileskrell.quizme.databinding.FragmentQuizBinding
import java.io.Serializable
import kotlin.random.Random

class QuizFragment : Fragment() {

    /*
    There are a lot of fun things you could add to this app. Here are some ideas:

    - Turn this into a game. The goal would be for the user to answer as many problems correctly as possible in 60 seconds.
    - Maybe the app could keep track of both how many problems the user got correct, AND how many they got incorrect.
    - At the end of the game, the user could be sent to a new fragment that would tell them their score.
      - There could be a share button so people could tell their friends how they did (just like in
        the App Navigation lesson in the Developing Android Apps with Kotlin Udacity course).
      - There could also be a button that would let them play the game again.
     */

    companion object {
        const val KEY_PROBLEM = "problem"
        const val KEY_NUM_CORRECT = "num correct"
    }

    lateinit var binding: FragmentQuizBinding

    enum class Operation(val method: (num1: Int, num2: Int) -> Int) {
        PLUS(Int::plus), MINUS(Int::minus), TIMES(Int::times);

        override fun toString() = when (this) {
            PLUS -> "+"
            MINUS -> "-"
            TIMES -> "×"
        }
    }

    class Problem(private val op: Operation, private val num1: Int, private val num2: Int) :
        Serializable {
        val answer = op.method(num1, num2)

        override fun toString() = "$num1$op$num2"
    }

    lateinit var problem: Problem
    var numCorrect = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        binding.numCorrect = numCorrect

        if (savedInstanceState == null) {
            showNewProblem()
        } else {
            problem = savedInstanceState.getSerializable(KEY_PROBLEM) as Problem
            numCorrect = savedInstanceState.getInt(KEY_NUM_CORRECT)
            binding.problem = problem
            binding.numCorrect = numCorrect
        }

        binding.buttonSkip.setOnClickListener {
            showNewProblem()
        }

        binding.editTextAnswer.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                try {
                    val input = binding.editTextAnswer.text.toString().toInt()
                    if (input == problem.answer) {
                        numCorrect++
                        binding.numCorrect = numCorrect
                    }
                } catch (e: NumberFormatException) {
                    // if the input isn't a valid number, it means the user got it wrong, and we're currently just ignoring that
                }
                binding.editTextAnswer.setText("")
                showNewProblem()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        return binding.root
    }

    private fun showNewProblem() {
        val num1 = Random.nextInt(1, 10)
        val num2 = Random.nextInt(1, 10)
        val op = Operation.values().random()
        problem = Problem(op, num1, num2)
        binding.problem = problem
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_NUM_CORRECT, numCorrect)
        outState.putSerializable(KEY_PROBLEM, problem)
    }
}

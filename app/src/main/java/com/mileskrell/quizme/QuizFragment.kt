package com.mileskrell.quizme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mileskrell.quizme.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    lateinit var binding: FragmentQuizBinding
    private val viewModel by viewModels<QuizViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.buttonSkip.setOnClickListener {
            viewModel.showNewProblem()
        }

        binding.editTextAnswer.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val input = binding.editTextAnswer.text.toString().toIntOrNull()
                if (input != null) {
                    viewModel.submitAnswer(input)
                }

                binding.editTextAnswer.setText("")
                viewModel.showNewProblem()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        viewModel.eventGameOver.observe({ lifecycle }) { gameOver ->
            if (gameOver) {
                findNavController().navigate(
                    QuizFragmentDirections.actionQuizFragmentToScoreFragment(
                        viewModel.numCorrect.value!!
                    )
                )
                viewModel.onEventGameOverFinished()
            }
        }

        return binding.root
    }
}

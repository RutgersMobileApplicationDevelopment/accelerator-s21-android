package com.mileskrell.quizme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mileskrell.quizme.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentScoreBinding.inflate(inflater, container, false)

        val args: ScoreFragmentArgs by navArgs()

        binding.textViewScore.text = getString(R.string.score_x, args.score)

        binding.buttonReplay.setOnClickListener {
            findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToQuizFragment())
        }

        return binding.root
    }
}

package com.example.tipcalculator

import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.tipcalculator.databinding.FragmentMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainFragment : Fragment() {
    private val viewModel: QuizViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val rootView = binding.root
        setHasOptionsMenu(true)

        viewModel.index.observe(viewLifecycleOwner) {
            binding.question1.text = getString(viewModel.currentQuestionText)
        }
        viewModel.gameWon.observe(viewLifecycleOwner) { gameWon ->
            if (gameWon) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(getString(R.string.top))
                    .setMessage(getString(R.string.bottom))
//                    .setTitle("Congratulations")
//                    .setMessage("You Won! Would you like to play again?")
                    .setPositiveButton("Yes") { dialog, which ->
                        viewModel.reset()
                    }
                    .setNegativeButton("No") { dialog, which ->
                        val action = MainFragmentDirections.actionMainFragmentToGameWonFragment()
                        rootView.findNavController().navigate(action)
                    }
                    .show()

            }
        }


        val myOnClickListener: View.OnClickListener =
            View.OnClickListener { view ->
                when (view.id) {
                    R.id.true1 -> {
                        checkAnswer(true)
                    }
                    R.id.false1 -> {
                        checkAnswer(false)
                    }
                    R.id.nextQuest1 -> {
                        viewModel.nextQuestion()
                    }
                }
            }

        binding.cheat.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToCheatFragment()
            rootView.findNavController().navigate(action)
        }

        binding.true1.setOnClickListener(myOnClickListener)
        binding.false1.setOnClickListener(myOnClickListener)
        binding.nextQuest1.setOnClickListener(myOnClickListener)
        binding.imageButton3.setOnClickListener(myOnClickListener)
        binding.question1.setOnClickListener(myOnClickListener)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    fun checkAnswer(answer: Boolean) {
        if (viewModel.currentQuestionCheatStatus && viewModel.checkAnswer(answer) ) {
            Toast.makeText(activity, getString(R.string.cheater), Toast.LENGTH_SHORT).show()

        }
        else if (viewModel.checkAnswer(answer)) {
            Toast.makeText(activity, getString(R.string.correct), Toast.LENGTH_SHORT).show()
//            myMediaPlayer = MediaPlayer.create(context, R.raw.ding)
//            myMediaPlayer.start()
        } else {
            Toast.makeText(activity, getString(R.string.wrong), Toast.LENGTH_SHORT).show()
//            myMediaPlayer = MediaPlayer.create(context, R.raw.boat)
//            myMediaPlayer.start()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
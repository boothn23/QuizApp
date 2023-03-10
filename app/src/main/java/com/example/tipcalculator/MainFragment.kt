package com.example.tipcalculator

import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.tipcalculator.databinding.FragmentMainBinding
import kotlinx.coroutines.NonCancellable.start

const val SAVED_QUESTION =""
class MainFragment : Fragment() {
    private val viewModel: QuizViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    lateinit var myMediaPlayer: MediaPlayer




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val rootView = binding.root
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(SAVED_QUESTION)
            cheated = savedInstanceState.getBoolean(SAVED_QUESTION)
        }

        setHasOptionsMenu(true)
        setFragmentResultListener("REQUESTING_REPLY_KEY") { requestKey, bundle ->
            val result = bundle.getBoolean("REPLY_KEY")
            cheated = result
        }


        binding.question1.text = getString(questions[index].question)
        val myOnClickListener: View.OnClickListener =
            View.OnClickListener { view ->
                when (view.id) {
                    R.id.true1 -> {
                        checkAnswer(true)
                        if (correct == 3) {
                            val action = MainFragmentDirections.actionMainFragmentToGameWonFragment(wrong)
                            rootView.findNavController().navigate(action)
                        }
                    }
                    R.id.false1 -> {
                        checkAnswer(false)
                        if (correct == 3) {
                            val action = MainFragmentDirections.actionMainFragmentToGameWonFragment(wrong)
                            rootView.findNavController().navigate(action)
                        }
                    }
                    R.id.imageButton3 -> {
                        if (index > 1) {
                            index--
                        }
                        else {
                            index = questions.size-1
                        }
                        binding.question1.text = getString(questions[index].question)
                    }

                    R.id.nextQuest1 -> {
                        if (index < questions.size-1) {
                            index++
                        }
                        else {
                            index = 0
                        }
                        binding.question1.text = getString(questions[index].question)
                    }
                    R.id.question1 -> {
                        if (index < questions.size-1) {
                            index++
                        }
                        else {
                            index = 0
                        }
                        binding.question1.text = getString(questions[index].question)
                    }

                }
            }

        binding.cheat.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToCheatFragment(questions[index].answer)
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

    fun checkAnswer( answer: Boolean) {
        if (questions[index].answer == answer && cheated) {
            Toast.makeText(activity, getString(R.string.cheater), Toast.LENGTH_SHORT).show()
            cheated = false
        }
        else if (questions[index].answer == answer ) {
            Toast.makeText(activity, getString(R.string.correct), Toast.LENGTH_SHORT).show()
            correct++
            myMediaPlayer = MediaPlayer.create(context, R.raw.ding)
            myMediaPlayer.start()
        }

        else {
            Toast.makeText(activity, getString(R.string.wrong), Toast.LENGTH_SHORT).show()
                wrong++
            myMediaPlayer = MediaPlayer.create(context, R.raw.boat)
            myMediaPlayer.start()
            }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(SAVED_QUESTION, index )
        savedInstanceState.putBoolean(SAVED_QUESTION, cheated)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
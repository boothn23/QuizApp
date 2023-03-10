package com.example.tipcalculator

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.tipcalculator.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    private var _binding: FragmentGameWonBinding? = null
    private val binding get() = _binding!!
    lateinit var myMediaPlayer: MediaPlayer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameWonBinding.inflate(inflater, container, false)
        super.onCreate(savedInstanceState)
        val rootView = binding.root
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        val text = args.incorrect.toString()
        binding.wrongs.text = "You had $text incorrect"
        myMediaPlayer = MediaPlayer.create(context, R.raw.scotland)
        myMediaPlayer.isLooping = true
        myMediaPlayer.start()


        binding.pauseButton.setOnClickListener {
            if(myMediaPlayer.isPlaying) {
                myMediaPlayer.pause()
                binding.pauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                Log.i("MainActivity", "${binding.pauseButton.background}")
            }
            else {
                myMediaPlayer.start()
                binding.pauseButton.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                Log.i("MainActivity", "${binding.pauseButton.background}")
            }
        }
        binding.forward.setOnClickListener {
            myMediaPlayer.seekTo(myMediaPlayer.currentPosition + 10000 )
        }
        binding.back.setOnClickListener {
            myMediaPlayer.seekTo(myMediaPlayer.currentPosition - 10000 )
        }


        setHasOptionsMenu(true)
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
    override fun onStop(){
        super.onStop()
        myMediaPlayer.release()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
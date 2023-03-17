package com.example.tipcalculator

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.tipcalculator.databinding.FragmentCheatBinding
import kotlin.math.roundToLong


class CheatFragment : Fragment() {
    private var _binding: FragmentCheatBinding? = null
    private val binding get() = _binding!!
    private var cheat = false
    private val viewModel: QuizViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheatBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val args = CheatFragmentArgs.fromBundle(requireArguments())binding.give.setOnClickListener{
            binding.answer.text = args.cheat.toString()
            cheat = true
            setFragmentResult("REQUESTING_REPLY_KEY", bundleOf("REPLY_KEY" to cheat))
            rootView.findNavController()
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
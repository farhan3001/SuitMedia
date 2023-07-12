package com.example.suitmedia.FirstPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.suitmedia.Helpers
import com.example.suitmedia.Model.SharedView
import com.example.suitmedia.R
import com.example.suitmedia.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val sharedViewModel: SharedView by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val input = _binding?.inputFirst?.text.toString()
            check(input)
        }

        binding.buttonSecond.setOnClickListener {
            val input = _binding?.inputFirst?.text.toString()

            if (Helpers().isPalindromeString(input) and input.isNotEmpty()) {
                sharedViewModel.setName(input)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun check(input: String) {
        if (input.isNotBlank()) {
            val palindrome = Helpers().isPalindromeString(input)

            if (palindrome) {
                _binding?.inputSecond?.setText("isPalindrome")
            } else {
                _binding?.inputSecond?.setText("not palindrome")
            }
        }
    }
}
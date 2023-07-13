package com.example.suitmedia.FirstPage

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.suitmedia.Helpers
import com.example.suitmedia.Model.SharedView
import com.example.suitmedia.R
import com.example.suitmedia.databinding.FragmentFirstBinding
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val sharedViewModel: SharedView by activityViewModels()
    private lateinit var avatar: String
    private var isButtonClickable = true

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

        val name = sharedViewModel.getName()
        avatar = sharedViewModel.getAvatar().toString()

        if (name != null) {
            binding.inputFirst.setText(name)
        }

        if (avatar != getString(R.string.nil) && avatar.isNotEmpty()) {
            Picasso.get().load(avatar).into(binding.imageProfile)
        }

        checkPalindrome()
        nextPage()
        deletePhoto()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun deletePhoto() {
        if (avatar != getString(R.string.none) && avatar.isNotEmpty()) {
            binding.imageProfile.setOnClickListener {
                val builder = AlertDialog.Builder(activity)

                builder.setTitle(getString(R.string.confirm_delete))
                builder.setMessage(getString(R.string.confirm_delete_photo_profile))
                builder.setPositiveButton(
                    getString(R.string.yes),
                    DialogInterface.OnClickListener { dialog, _ ->
                        sharedViewModel.setAvatar(getString(R.string.none))
                        val drawableResId = R.drawable.ic_photo
                        Picasso.get().load(drawableResId).into(binding.imageProfile)
                        dialog.cancel()
                        findNavController().navigate(R.id.action_FirstFragment_to_FirstFragment)
                    })
                builder.setNegativeButton(
                    getString(R.string.no),
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })

                val alert = builder.create()
                alert.show()
            }
        }
    }

    private fun checkPalindrome() {
        binding.buttonFirst.setOnClickListener {
            val input = _binding?.inputFirst?.text.toString()
            check(input)
        }
    }

    private fun check(input: String) {
        if (input.isNotBlank()) {
            val palindrome = Helpers().isPalindromeString(input)

            if (palindrome) {
                _binding?.inputSecond?.setText(getString(R.string.is_palindrome))
            } else {
                _binding?.inputSecond?.setText(getString(R.string.not_palindrome))
            }
        }
    }

    private fun nextPage() {
        binding.buttonSecond.setOnClickListener {
            if (isButtonClickable) {
                isButtonClickable = false

                val input = _binding?.inputFirst?.text.toString()
                if (Helpers().isPalindromeString(input) and input.isNotEmpty()) {
                    sharedViewModel.setName(input)
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.name_is_not_palindrome),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                binding.buttonSecond.postDelayed({
                    isButtonClickable = true
                }, 2000) // 1000 milliseconds = 1 second
            }
        }
    }
}
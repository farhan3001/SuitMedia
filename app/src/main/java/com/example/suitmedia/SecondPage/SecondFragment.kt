package com.example.suitmedia.SecondPage

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.suitmedia.Model.SharedView
import com.example.suitmedia.R
import com.example.suitmedia.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val sharedViewModel: SharedView by activityViewModels()

    private lateinit var buttonBack: ImageButton
    private lateinit var titlePage: TextView

    private lateinit var username: String
    private lateinit var id: String
    private lateinit var email: String
    private lateinit var avatar: String

    private lateinit var myClipboard: ClipboardManager

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        username = arguments?.getString("username").toString()
        id = arguments?.getInt("id").toString()
        email = arguments?.getString("email").toString()
        avatar = arguments?.getString("avatar").toString()

        myClipboard =
            context?.let { getSystemService(it, ClipboardManager::class.java) } as ClipboardManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pageLabel: String = getString(R.string.second_fragment_label)
        val name = sharedViewModel.getName()

        titlePage = view.findViewById(R.id.page_title)
        titlePage.text = pageLabel
        buttonBack = view.findViewById(R.id.button_back)

        if (name != null) {
            binding.name.text = name
            sharedViewModel.setName(name)
        }

        if (avatar != "null") {
            sharedViewModel.setAvatar(avatar)
        }

        if (username != "null") {
            binding.userChose.text = username
        }

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment)
        }

        buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
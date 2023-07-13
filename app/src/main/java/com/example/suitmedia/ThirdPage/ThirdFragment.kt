package com.example.suitmedia.ThirdPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmedia.R
import com.example.suitmedia.ThirdPage.API.APIInstance
import com.example.suitmedia.ThirdPage.API.ApiService
import com.example.suitmedia.ThirdPage.API.DataDomain
import com.example.suitmedia.databinding.FragmentThirdBinding


class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter

    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var buttonBack: ImageButton
    private lateinit var titlePage: TextView

    private lateinit var apiService: ApiService
    private var apiInstance = APIInstance
    private var dataDomain = DataDomain()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)

        val recyclerView = binding.myRecyclerView
        swipe = binding.swipeToRefresh

        userAdapter = UserAdapter(emptyList())
        apiService = apiInstance.runApiService()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pageLabel: String = getString(R.string.third_fragment_label)
        titlePage = view.findViewById(R.id.page_title)
        titlePage.text = pageLabel

        buttonBack = view.findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_SecondFragment)
        }

        getData()
        refreshFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun refreshFragment() {
        swipe.setOnRefreshListener {
            getData()
            swipe.isRefreshing = false
        }
    }

    private fun getData() {
        dataDomain.fetchData(1, 10, userAdapter, apiService, context)
        dataDomain.emptyUserList()
    }
}
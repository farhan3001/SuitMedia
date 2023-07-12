package com.example.suitmedia.ThirdPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmedia.R
import com.example.suitmedia.ThirdPage.API.APIInstance
import com.example.suitmedia.ThirdPage.API.ApiService
import com.example.suitmedia.ThirdPage.API.DataDomain
import com.example.suitmedia.databinding.FragmentThirdBinding


class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null

    private lateinit var userAdapter: UserAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var buttonBack: ImageButton

    private lateinit var apiService: ApiService
    private var apiInstance = APIInstance
    private var dataDomain = DataDomain()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_third, container, false)

        recyclerView = rootView.findViewById(R.id.my_recycler_view)
        swipe = rootView.findViewById(R.id.swipe_to_refresh)
        buttonBack = rootView.findViewById(R.id.button_back)

        userAdapter = UserAdapter(emptyList())
        apiService = apiInstance.runApiService()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }

        getData()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_SecondFragment)
        }

        refreshFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun refreshFragment() {
        swipe.setOnRefreshListener {
            dataDomain.fetchData(1, 10, userAdapter, apiService, context)
            dataDomain.emptyUserList()
            swipe.isRefreshing = false
        }
    }

    private fun getData() {
        dataDomain.fetchData(1, 10, userAdapter, apiService, context)
        dataDomain.emptyUserList()
    }


}
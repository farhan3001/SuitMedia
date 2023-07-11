package com.example.suitmedia.ThirdPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmedia.Model.Users
import com.example.suitmedia.R
import com.example.suitmedia.ThirdPage.API.ApiService
import com.example.suitmedia.databinding.FragmentThirdBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null

    //    private val binding get() = _binding!!
    private lateinit var userAdapter: UserAdapter

    private lateinit var recyclerView: RecyclerView

    private lateinit var swipe: SwipeRefreshLayout

    private lateinit var apiService: ApiService
    private lateinit var buttonBack: ImageButton

    private lateinit var users: List<Users>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_third, container, false)
        recyclerView = rootView.findViewById(R.id.my_recycler_view)
        swipe = rootView.findViewById(R.id.swipe_to_refresh)
        userAdapter = UserAdapter(emptyList())
        users = emptyList<Users>()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        fetchData(1, 10)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshFragment()

        buttonBack = view.findViewById(R.id.button_back)

        buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchData(page: Int, perPage: Int) {

        lifecycleScope.launch {
            try {
                val response = apiService.getUsers(page, perPage)
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    val total = userResponse?.total
                    val newUserList = userResponse?.data ?: emptyList()
//                    userAdapter.userList = newUserList
                    users += newUserList.shuffled()

                    if (total != null) {
                        if (total > 10)
                            fetchData(page + 1, perPage)
                    }
                    userAdapter.notifyDataSetChanged()
                } else {
                    // Handle API error
                }
            } catch (e: Exception) {
                // Handle network or other exceptions
            }
        }
        userAdapter.userList = users
        userAdapter.notifyDataSetChanged()
    }

    fun refreshFragment() {
        swipe.setOnRefreshListener {

            users = emptyList<Users>()
            fetchData(1, 10)
            swipe.isRefreshing = false
        }
    }


}
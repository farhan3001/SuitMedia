package com.example.suitmedia.ThirdPage.API

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.suitmedia.Model.Users
import com.example.suitmedia.ThirdPage.UserAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DataDomain {

    private var users = emptyList<Users>()

    fun fetchData(
        page: Int,
        perPage: Int,
        userAdapter: UserAdapter,
        apiService: ApiService,
        context: Context?
    ) {
        MainScope().launch {
            try {
                val response = apiService.getUsers(page, perPage)
                var i = 1
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    val newUserList = userResponse?.data
                    if (newUserList?.isNotEmpty() == true) {
                        i += page
                        Log.d("Increment", i.toString())
                        users = users + newUserList
                        fetchData(i, perPage, userAdapter, apiService, context)
                    }
                    val usersShuffled = users.shuffled()
                    userAdapter.userList = usersShuffled
                    userAdapter.notifyDataSetChanged()
                } else {
                    // Handle API error
                    Toast.makeText(context, "API ERROR", Toast.LENGTH_SHORT).show()
                }
            } catch (message: Exception) {
                // Handle network or other exceptions
                Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun emptyUserList() {
        users = emptyList()
    }
}
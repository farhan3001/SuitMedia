package com.example.suitmedia.ThirdPage.API

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.suitmedia.Model.Users
import com.example.suitmedia.R
import com.example.suitmedia.ThirdPage.UserAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DataDomain {

    private var users = emptyList<Users>()
    private var userData = emptyList<Users>()
    var dataUsers = emptyList<Users>()

    private var i = 1


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
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    val newUserList = userResponse?.data
                    if (newUserList?.isNotEmpty() == true) {
                        i += 1
                        users = users + newUserList
                        fetchData(i, perPage, userAdapter, apiService, context)
                        users = users.shuffled()
                    } else {
                        i = 1
                        userAdapter.userList = users
                        userAdapter.notifyDataSetChanged()
                    }
                } else {
                    // Handle API error
                    Toast.makeText(
                        context,
                        context?.getString(R.string.api_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (message: Exception) {
                // Handle network or other exceptions
                Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

//    fun fetchOnlyData(
//        page: Int,
//        perPage: Int,
//        userAdapter: UserAdapter,
//        apiService: ApiService,
//        context: Context?
//    ) {
//        MainScope().launch {
//            try {
//                val response = apiService.getUsers(page, perPage)
//                if (response.isSuccessful) {
//                    val userResponse = response.body()
//                    val newUserList = userResponse?.data
//                    if (newUserList?.isNullOrEmpty() == false) {
//                        i += 1
//                        userData = userData + newUserList
//                        fetchOnlyData(i, perPage, userAdapter, apiService, context)
//                    } else {
//                        i = 1
//                        userAdapter.userList = userData
//                        userAdapter.notifyDataSetChanged()
//                    }
//
//                } else {
//                    // Handle API error
//                    Toast.makeText(
//                        context,
//                        context?.getString(R.string.api_error),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            } catch (message: Exception) {
//                // Handle network or other exceptions
//                Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    fun emptyUserList() {
        users = emptyList()
    }

//    fun getUserList() : List<Users> {
//        return users
//    }

//    fun getUsers(): List<Users> {
//        Log.d("Users in Data", usersData.toString())
//        return usersData
//    }
//
//    private fun setUsers(user: List<Users>) {
//        Log.d("Users", user.toString())
//        usersData = user
//    }
}
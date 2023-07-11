package com.example.suitmedia.ThirdPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController

import androidx.recyclerview.widget.RecyclerView
import com.example.suitmedia.FirstPage.FirstFragment
import com.example.suitmedia.Model.Users
import com.example.suitmedia.R
import com.example.suitmedia.SecondPage.SecondFragment
import com.squareup.picasso.Picasso

class UserAdapter(var userList: List<Users>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
//        var context = holder.itemView.context

        holder.itemView.setOnClickListener {

            val name = user.first_name + " " + user.last_name
            Log.d("User",name)

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewAvatar: ImageView = itemView.findViewById(R.id.imageViewAvatar)
        private val textEmail: TextView = itemView.findViewById(R.id.email)
        private val textName: TextView = itemView.findViewById(R.id.name)

        fun bind(user: Users) {
            // Load the image using a library like Picasso or Glide
            Picasso.get().load(user.avatar).into(imageViewAvatar)

            textEmail.text = user.email
            textName.text = user.first_name + " " + user.last_name

            // Bind other user details to their respective views
        }

        init {
            itemView.setOnClickListener {
                val name = textName.text
                var context = itemView.context
                Toast.makeText(context, name, Toast.LENGTH_SHORT)
            }
        }
    }

}
package com.example.suitmedia.ThirdPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.suitmedia.Model.Users
import com.example.suitmedia.R
import com.squareup.picasso.Picasso

class UserAdapter(var userList: List<Users>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)

        holder.itemView.setOnClickListener {view ->
            val id = user.id
            val name = user.first_name + " " + user.last_name
            val email = user.email
            val avatar = user.avatar

            val bundle = Bundle()
            bundle.putInt(view.context.getString(R.string.id), id)
            bundle.putString(view.context.getString(R.string.email), email)
            bundle.putString(view.context.getString(R.string.username), name)
            bundle.putString(view.context.getString(R.string.avatar2), avatar)

            view.findNavController().navigate(R.id.action_ThirdFragment_to_SecondFragment, bundle)
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
            Picasso.get().load(user.avatar).into(imageViewAvatar)
            textEmail.text = user.email
            textName.text = user.first_name + " " + user.last_name
        }
    }

}
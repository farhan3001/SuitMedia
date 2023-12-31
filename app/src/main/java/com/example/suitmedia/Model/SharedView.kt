package com.example.suitmedia.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedView : ViewModel() {
    private val _name = MutableLiveData<String>("")
    val name: LiveData<String> = _name

    private val _avatar = MutableLiveData<String>("")
    val avatar: LiveData<String> = _avatar

    fun setName(name: String) {
        _name.value = name
    }

    fun getName(): String? {
        return _name.value
    }

    fun setAvatar(avatar: String) {
        _avatar.value = avatar
    }

    fun getAvatar(): String? {
        return _avatar.value
    }
}
package com.example.suitmedia.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedView : ViewModel() {
    private val _name = MutableLiveData<String>("")
    val name: LiveData<String> = _name

    fun setName(name: String) {
        _name.value = name
    }

    fun getName(): String? {
        return _name.value
    }
}
package com.example.suitmedia

class Helpers {
    fun isPalindromeString(inputStr: String): Boolean {
        val str = inputStr.filter { it.isLetterOrDigit() }.lowercase()
        return str == str.reversed()
    }
}
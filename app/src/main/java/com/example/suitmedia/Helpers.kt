package com.example.suitmedia

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import android.util.Log

class Helpers {
    fun isPalindromeString(inputStr: String): Boolean {
        val str = inputStr.filter { it.isLetterOrDigit() }.lowercase()
        return str == str.reversed()
    }
}
package com.example.listadesuper.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class SharedPreferencesUtils() {
    companion object {
        fun getItemsSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences("lista_de_super", AppCompatActivity.MODE_PRIVATE)
        }
    }
}

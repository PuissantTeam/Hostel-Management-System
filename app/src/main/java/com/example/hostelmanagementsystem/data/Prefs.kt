package com.example.hostelmanagementsystem.data

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    var status : Int
        get() = preferences.getInt("status", -1)
        set(value) = preferences.edit().putInt("status", value).apply()

    var userType: String?
        get() = preferences.getString("userType", "none")
        set(value) = preferences.edit().putString("userType", value).apply()

}
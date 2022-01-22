package com.example.hostelmanagementsystem.data

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class Prefs (context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    var status : Int
        get() = preferences.getInt("status", -1)
        set(value) = preferences.edit().putInt("status", value).apply()

    var userType: String?
        get() = preferences.getString("userType", "none")
        set(value) = preferences.edit().putString("userType", value).apply()


    var lastVisitedDateTime: Int
        get() = preferences.getInt("lastVisitedDateTime", getPreviousDate())
        set(value) = preferences.edit().putInt("lastVisitedDateTime", value).apply()

    private fun getPreviousDate():Int{
        val cal = Calendar.getInstance()
        var currentDate: Int =  cal.get(Calendar.DATE)
        cal.add(Calendar.DATE,-1)
        val dayFormat = SimpleDateFormat("dd",Locale("In"))
        return dayFormat.format(cal.time).toString().toInt()
    }
}
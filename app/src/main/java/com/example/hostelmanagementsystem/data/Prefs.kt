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

    var currentDateAttendanceId: String?
        get() = preferences.getString("currentDateAttendanceId", "")
        set(value) = preferences.edit().putString("currentDateAttendanceId", value).apply()

    var currentDateCheckInTime: String?
        get() = preferences.getString("currentDateCheckInTime", "")
        set(value) = preferences.edit().putString("currentDateCheckInTime", value).apply()

    var checkedIn: Boolean
        get() = preferences.getBoolean("CheckedIn", false)
        set(value) = preferences.edit().putBoolean("CheckedIn", value).apply()

    var checkedOut: Boolean
        get() = preferences.getBoolean("CheckedOut", true)
        set(value) = preferences.edit().putBoolean("CheckedOut", value).apply()

    var present: Boolean
        get() = preferences.getBoolean("present", false)
        set(value) = preferences.edit().putBoolean("present", value).apply()

    var locationPermission: Boolean
        get() = preferences.getBoolean("locationPermission", false)
        set(value) = preferences.edit().putBoolean("locationPermission", value).apply()

    var absent: Boolean
        get() = preferences.getBoolean("absent", false)
        set(value) = preferences.edit().putBoolean("absent", value).apply()

    private fun getPreviousDate():Int{
        val cal = Calendar.getInstance()
        var currentDate: Int =  cal.get(Calendar.DATE)
        cal.add(Calendar.DATE,-1)
        val dayFormat = SimpleDateFormat("dd",Locale("In"))
        return dayFormat.format(cal.time).toString().toInt()
    }
}
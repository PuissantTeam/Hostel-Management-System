package com.example.hostelmanagementsystem.Data

data class Register(
    var name: String = "",
    var uid: String = "",
    var sid: String = "",
    var email: String = "",
    var state: String= "",
    var district: String= "",
    var distance: Int = 0,
    var status: String = ""
)
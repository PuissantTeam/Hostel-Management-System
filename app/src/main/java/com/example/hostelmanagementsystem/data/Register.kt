package com.example.hostelmanagementsystem.data

data class Register(
    var name: String = "",
    var sid: String = "",
    var email: String = "",
    var state: String= "",
    var district: String= "",
    var distance: Int = 0,
    var status: String = ""
)
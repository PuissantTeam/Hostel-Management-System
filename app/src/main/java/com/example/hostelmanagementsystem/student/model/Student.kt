package com.example.hostelmanagementsystem.student.model

data class Student(
    var userType: String = "student",
    var name: String = "",
    var uid: String = "",
    var sid: String = "",
    var email: String = "",
    var state: String= "",
    var district: String= "",
    var roomNo: String = ""
)

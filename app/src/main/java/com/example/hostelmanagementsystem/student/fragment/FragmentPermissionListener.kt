package com.example.hostelmanagementsystem.student.fragment

interface PermissionListener {
    fun   shouldShowRationaleInfo()
    fun   isPermissionGranted(isGranted : Boolean)
}
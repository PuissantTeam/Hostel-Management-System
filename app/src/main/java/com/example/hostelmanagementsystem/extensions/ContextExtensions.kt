package com.example.hostelmanagementsystem.extensions

import android.content.Context

import android.widget.Toast

fun Context?.toast(
    stringId: Int,
    length: Int = Toast.LENGTH_LONG
) {
    this?.let {
        Toast.makeText(this, stringId, length)
            .show()
    }
}

fun Context?.toast(
    text: String,
    length: Int = Toast.LENGTH_LONG
) {
    this?.let {
        Toast.makeText(this, text, length)
            .show()
    }
}

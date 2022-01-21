package com.example.hostelmanagementsystem.extensions

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Fragment.closeKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}


fun View.closeKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun showSnackBar(activity: Activity, message: String?, anchorView: View) {
    val rootView = activity.window.decorView.findViewById<View>(android.R.id.content)
    val snackbar = Snackbar.make(rootView, message!!, Snackbar.LENGTH_SHORT)
    snackbar.anchorView = anchorView
    snackbar.show()
}

fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
}

fun showSnackBarWithAction(
    activity: Activity,
    message: String?,
    @StringRes actionRes: Int,
    color: Int? = null,
    anchorView: View,
    listener: (View) -> Unit
) {
    val rootView = activity.window.decorView.findViewById<View>(android.R.id.content)
    val snackbar = Snackbar.make(rootView, message!!, Snackbar.LENGTH_SHORT)
    snackbar.setAction(actionRes, listener)
    snackbar.anchorView = anchorView
    snackbar.show()
}

fun Snackbar.action(@StringRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(ContextCompat.getColor(context, color)) }
}

fun <T> Context.openActivity(it: Class<T>) {
    val intent = Intent(this, it)
    startActivity(intent)
}



package com.example.hostelmanagementsystem.utils.dialog

import android.view.View

sealed class AppDialogs(
  val title: Int?,
  val message: Int?,
  val positiveMessage: Int,
  val negativeMessage: Int?,
  val cancelable: Boolean = true,
  val icon: Int? = null,
  val neutralMessage: Int? = null,
  val getView: (() -> View)? = null
) {
  interface HasBodyFormatArgs {
    val args: List<Any>
  }
}

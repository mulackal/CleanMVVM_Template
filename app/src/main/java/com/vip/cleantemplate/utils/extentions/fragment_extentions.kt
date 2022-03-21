package com.eduvy.demo.utils.extentions

import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String,) {
    activity?.showToast(message)
}

fun Fragment.showToast(message: Int) {
    activity?.showToast(getString(message))
}
package com.eduvy.demo.utils.extentions

import android.app.Activity
import android.widget.Toast


fun Activity.showToast(message: String) {
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Activity.showToast(message: Int ) {
    Toast.makeText(this,getString(message),Toast.LENGTH_SHORT).show()
}
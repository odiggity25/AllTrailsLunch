package com.orrie.alltrailslunch.shared

import com.orrie.alltrailslunch.ATApplication

fun toast(message: String, duration: Int = android.widget.Toast.LENGTH_SHORT) {
    android.widget.Toast.makeText(ATApplication.context, message, duration)
}
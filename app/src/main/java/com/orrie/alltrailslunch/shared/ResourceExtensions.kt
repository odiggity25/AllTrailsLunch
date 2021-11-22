package com.orrie.alltrailslunch.shared

import androidx.core.content.ContextCompat
import com.orrie.alltrailslunch.ATApplication

fun Int.resourceString(): String = ATApplication.context.getString(this)

fun Int.resourceString(vararg placeholders: Any): String = ATApplication.context.getString(this, *placeholders)

fun Int.resourceColor(): Int = ContextCompat.getColor(ATApplication.context, this)
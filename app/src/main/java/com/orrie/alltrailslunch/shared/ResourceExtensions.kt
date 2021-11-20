package com.orrie.alltrailslunch.shared

import android.graphics.drawable.Drawable
import com.orrie.alltrailslunch.ATApplication

fun Int.resourceString(): String = ATApplication.context.getString(this)

fun Int.resourceString(vararg placeholders: Any): String = ATApplication.context.getString(this, placeholders)
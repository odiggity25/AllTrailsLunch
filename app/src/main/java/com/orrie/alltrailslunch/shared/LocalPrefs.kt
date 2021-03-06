package com.orrie.alltrailslunch.shared

import android.content.Context
import com.orrie.alltrailslunch.ATApplication

private const val localPrefsKey = "ATLocalPrefs"

/**
 * Simple wrapper for shared prefs. Would add additional functions for additional data types when needed
 */
class LocalPrefs {

    private val localPrefs = ATApplication.context.getSharedPreferences(localPrefsKey, Context.MODE_PRIVATE)

    fun putSet(key: String, value: Set<String>) {
        localPrefs.edit().putStringSet(key, value).apply()
    }

    fun getSet(key: String): Set<String>? {
        return if (localPrefs.contains(key)) {
            localPrefs.getStringSet(key, setOf())
        } else null
    }
}
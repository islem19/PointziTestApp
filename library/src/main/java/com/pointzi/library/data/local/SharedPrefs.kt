package com.pointzi.library.data.local

import android.content.Context
import android.content.SharedPreferences

object SharedPrefs {
    private lateinit var  prefs : SharedPreferences
    // our shared pref key holding the date of installation
    private const val SHARED_PREFS_KEY_INSTALLED_ON = "INSTALLED_ON"

    /**
     * init the shared preferences instance
     */
    fun init(context: Context){
        prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    /**
     * set the date of installation
     */
    fun setInstalledOn(date : String) = prefs.edit().putString(SHARED_PREFS_KEY_INSTALLED_ON,date).apply()

    /**
     * get the date of installation otherwise return null (if it doesn't exist)
     */
    fun getInstalledOn() : String? = prefs.getString(SHARED_PREFS_KEY_INSTALLED_ON,null)

}
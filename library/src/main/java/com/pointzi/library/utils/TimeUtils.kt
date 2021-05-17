package com.pointzi.library.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object {
        /**
         * get the current date formatted as DAY MONTH YEAR
         */
        fun getDate(): String = SimpleDateFormat("dd MMM yyyy").format(Calendar.getInstance().time)
    }
}
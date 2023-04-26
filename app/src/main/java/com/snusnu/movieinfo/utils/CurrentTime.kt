package com.snusnu.movieinfo.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return dateFormat.format(Date()).toString()
}
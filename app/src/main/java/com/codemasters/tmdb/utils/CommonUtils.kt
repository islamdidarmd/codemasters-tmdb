package com.codemasters.tmdb.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.toDateFormat(currentFormat: String, requiredFormat: String): String {
    val formatterOld = SimpleDateFormat(currentFormat, Locale.getDefault())
    val formatterNew = SimpleDateFormat(requiredFormat, Locale.getDefault())

    val date: Date?
    try {
        date = formatterOld.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
        return this
    }

    return formatterNew.format(date!!)
}
package com.sales.android.projecttms.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertLongToTime(time: Long?): String {
    val date = time?.let { Date(it) }
    val format = SimpleDateFormat("dd/M/yyyy")
    return date?.let { format.format(it) } ?: ""
}
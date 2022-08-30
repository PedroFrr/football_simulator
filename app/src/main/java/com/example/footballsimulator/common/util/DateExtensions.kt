package com.example.footballsimulator.common.util

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

const val YYYY_MM_DD = "yyyy-MM-dd"

fun String.formatToDate(inputFormat: String): Date {
    val dateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    val pos = ParsePosition(0)
    return dateFormat.parse(this, pos) ?: Date()
}

fun LocalDate.formatToPattern(outputFormat: String): String {
    return this.format(DateTimeFormatter.ofPattern(outputFormat))
}

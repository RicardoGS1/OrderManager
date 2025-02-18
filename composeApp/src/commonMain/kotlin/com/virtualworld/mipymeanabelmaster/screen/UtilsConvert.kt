package com.virtualworld.mipymeanabelmaster.screen

import kotlin.math.pow
import kotlin.math.round

fun convertMillisToDate(millis: Long): String {
    val daysInMonth = intArrayOf(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    var days = millis / 86400000 // Milisegundos en un día
    var year = 1970
    while (true) {
        val daysInYear = if (isLeapYear(year)) 366 else 365
        if (days >= daysInYear) {
            days -= daysInYear
            year++
        } else {
            break
        }
    }
    var month = 1
    while (true) {
        val daysInCurrentMonth = if (month == 2 && isLeapYear(year)) 29 else daysInMonth[month]
        if (days >= daysInCurrentMonth) {
            days -= daysInCurrentMonth
            month++
        } else {
            break
        }
    }
    val day = days + 1
    return "${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/$year"
}

fun isLeapYear(year: Int): Boolean {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
}


fun Float.roundToDecimals(decimals: Int): Float {
    val multiplier = 10.0f.pow(decimals)
    return round(this * multiplier) / multiplier
}
package com.app.yelpproject.lib.utils

import java.util.*

object DateUtils {
    fun getDayOfWeekFromCurrentDateIndex() =
        Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

    fun getCurrentDayOfWeek() =
        when (getDayOfWeekFromCurrentDateIndex()) {
            Calendar.SUNDAY -> "Sunday"
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> INVALID_DAY_OF_WEEK
        }

    fun getDayOfWeekFromIndex(index: Int, startWithZero: Boolean = true): String {

        val currentIndex: Int = if (startWithZero) index + 1
        else index
        return when (currentIndex) {
            Calendar.SUNDAY -> "Sunday"
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> INVALID_DAY_OF_WEEK
        }
    }

    private const val INVALID_DAY_OF_WEEK = ""
}
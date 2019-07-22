package com.app.yelpproject.lib.utils

fun String.time24Hrsto12HrsFormat(withSeconds: Boolean? = true): String {
    var hours = (this[0] - '0') * 10 + (this[1] - '0')
    val meridien = if (hours < 12) "AM" else "PM"
    hours %= 12
    var time: String = (if (hours == 0) "12" else hours.toString())
    for (i in 2..(if (withSeconds!!) 8 else 4)) {
        time += this[i]
    }
    return "$time $meridien"
}

fun String.militaryTimeTo12HrsFormat(withSeconds: Boolean? = true) =
    militaryTimeTo24HrsFormat().time24Hrsto12HrsFormat(withSeconds)

fun String.militaryTimeTo24HrsFormat(): String {
    if (this.length == 4) {
        return "${this[0]}${this[1]}:${this[2]}${this[3]}:00"
    } else if (this.length == 6) {
        return "${this[0]}${this[1]}:${this[2]}${this[3]}:${this[4]}${this[5]}"
    }
    return this
}

object TimeUtils {


}
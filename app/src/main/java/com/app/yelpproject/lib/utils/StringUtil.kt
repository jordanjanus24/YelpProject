package com.app.yelpproject.lib.utils

import java.text.DecimalFormat

fun Number.delimeterByThousands(separator: String = ",") = StringUtil.delimeterByThousands(this, separator)

fun String?.nullCheck(): String =
    this ?: ""

object StringUtil {
    fun formatWithSuffix(number: Double, unit: String = "m"): String {
        val format = DecimalFormat("0.#")
        val exp = (Math.log(number) / Math.log(1000.0)).toInt()
        val value = format.format(number / Math.pow(1000.0, exp.toDouble()))
        if (number < 1000) return "$value$unit"
        return String.format("%s%c", value, "kMBTPE"[exp - 1]) + unit
    }

    fun delimeterByThousands(number: Number, separator: String = ",") = String.format("%${separator}d", number)

}
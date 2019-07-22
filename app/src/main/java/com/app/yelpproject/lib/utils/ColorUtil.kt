package com.app.yelpproject.lib.utils

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getColorFromRes(@ColorRes color: Int) = ColorUtil.getFromRes(this, color)

object ColorUtil {
    fun interpolate(fraction: Float, startValue: Int, endValue: Int): Int {
        val startA = startValue shr 24 and 0xff
        val startR = startValue shr 16 and 0xff
        val startG = startValue shr 8 and 0xff
        val startB = startValue and 0xff
        val endA = endValue shr 24 and 0xff
        val endR = endValue shr 16 and 0xff
        val endG = endValue shr 8 and 0xff
        val endB = endValue and 0xff
        return startA + (fraction * (endA - startA)).toInt() shl 24 or
                (startR + (fraction * (endR - startR)).toInt() shl 16) or
                (startG + (fraction * (endG - startG)).toInt() shl 8) or
                startB + (fraction * (endB - startB)).toInt()
    }

    fun interpolateColorsFromRes(context: Context, slideOffset: Float, startValue: Int, endValue: Int): Int {
        val from = ContextCompat.getColor(context, startValue)
        val to = ContextCompat.getColor(context, endValue)
        return interpolate(slideOffset, from, to)
    }

    fun getFromRes(context: Context, @ColorRes color: Int) = ContextCompat.getColor(context, color)
}
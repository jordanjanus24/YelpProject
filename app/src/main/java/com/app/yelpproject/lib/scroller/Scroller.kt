package com.app.yelpproject.lib.scroller

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class Scroller @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttrs: Int = 0
) : NestedScrollView(context, attrs, defStyleAttrs) {
    var isScrollable = true

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return isScrollable && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return isScrollable && super.onInterceptTouchEvent(ev)
    }
}
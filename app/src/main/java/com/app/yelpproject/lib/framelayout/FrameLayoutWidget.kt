package com.app.yelpproject.lib.framelayout


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.core.util.ObjectsCompat

class FrameLayoutWidget  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs) {

    private var mLastInsets: Any? = null
    init {

    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        if (!ObjectsCompat.equals(mLastInsets, insets)) {
            mLastInsets = insets
            requestLayout()
        }
        return insets.consumeSystemWindowInsets()
    }

    override fun fitSystemWindows(insets: Rect): Boolean {
        if (!ObjectsCompat.equals(mLastInsets, insets)) {
            if (mLastInsets == null) {
                mLastInsets = Rect(insets)
            } else {
                (mLastInsets as Rect).set(insets)
            }
            requestLayout()
        }
        return true
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mLastInsets != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                val wi = mLastInsets as WindowInsets?
                val childCount = childCount
                for (i in 0 until childCount) {
                    val child = getChildAt(i)
                    if (child.visibility != View.GONE) {
                        child.dispatchApplyWindowInsets(wi)
                    }
                }
            } else {
                super.fitSystemWindows(Rect(mLastInsets as Rect?))
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}
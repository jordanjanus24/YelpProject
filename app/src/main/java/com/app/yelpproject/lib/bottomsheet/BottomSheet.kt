package com.app.yelpproject.lib.bottomsheet

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

fun BottomSheetBehavior<out View>.addCallback(
    slideChange: (Float) -> Unit,
    stateChange: ((Int) -> Unit)? = null
): BottomSheetBehavior<out View> {
    this.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            slideChange.invoke(slideOffset)
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            stateChange?.invoke(newState)
        }
    })
    return this
}

fun BottomSheetBehavior<out View>.expandSheet() {
    this.state = BottomSheetBehavior.STATE_EXPANDED
}

fun BottomSheetBehavior<out View>.collapseSheet() {
    this.state = BottomSheetBehavior.STATE_COLLAPSED
}

fun BottomSheetBehavior<out View>.isExpanded(): Boolean = this.state == BottomSheetBehavior.STATE_EXPANDED
fun BottomSheetBehavior<out View>.isCollapsed(): Boolean = this.state == BottomSheetBehavior.STATE_COLLAPSED
fun BottomSheetBehavior<out View>.isDragging():Boolean = this.state == BottomSheetBehavior.STATE_DRAGGING

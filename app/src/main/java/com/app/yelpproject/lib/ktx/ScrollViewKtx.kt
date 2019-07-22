package com.app.yelpproject.lib.ktx

import androidx.core.widget.NestedScrollView
import com.app.yelpproject.lib.animator.animateOfIntProperty


fun NestedScrollView.smoothScrollYAnimate(scrollY: Int) =
    animateOfIntProperty("scrollY", scrollY, 300)

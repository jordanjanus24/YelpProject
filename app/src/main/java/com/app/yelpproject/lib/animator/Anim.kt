package com.app.yelpproject.lib.animator

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils

fun Activity.createAnim(animId: Int, onEndAnim: (() -> Unit)? = null) = applicationContext.createAnim(animId, onEndAnim)
fun Context.createAnim(animId: Int, onEndAnim: (() -> Unit)? = null): Animation =
    Anim.create(this, animId, onEndAnim)
fun <T:View> T.execAnimation(animId: Int, onEndAnim: (T.() -> Unit)? = null):T {
    startAnimation(context.createAnim(animId) {
        onEndAnim?.invoke(this)
    })
    return this
}

object Anim {
    fun create(context: Context, animId: Int, onEndAnim: (() -> Unit)? = null): Animation =
        AnimationUtils.loadAnimation(context, animId).apply {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    onEndAnim?.invoke()
                }

                override fun onAnimationStart(animation: Animation?) {
                }

            })
        }
}
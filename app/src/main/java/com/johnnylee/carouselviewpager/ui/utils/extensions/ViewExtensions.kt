package com.johnnylee.carouselviewpager.ui.utils.extensions

import android.content.ContextWrapper
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

/**
 * Extension function for retrieving parentActivity from a view
 */
fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

/**
 * Extension function for setup background color of a view using only @ColorRes Id
 *
 * @param colorId @ColorRes Id
 */
fun View.setBackgroundColorFromId(@ColorRes colorId: Int) {
    this.setBackgroundColor(ContextCompat.getColor(this.context, colorId))
}

/**
 * Extension function for setting up toolbar text color using only @ColorRes Id
 *
 * @param colorId @ColorRes Id
 */
fun Toolbar.setTitleTextColorFromId(@ColorRes colorId: Int) {
    this.setTitleTextColor(ContextCompat.getColor(this.context, colorId))
}
/**
 * Extension function for setting up toolbar text color using only @ColorRes Id
 *
 * @param drawableId @ColorRes Id
 */
fun ImageView.setDrawableFromId(@DrawableRes drawableId: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(this.context, drawableId))
}
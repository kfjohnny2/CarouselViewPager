package com.johnnylee.carouselviewpager.dialogs.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.view.View
import android.view.WindowManager

/**
 * Copyright (c) $today.year.
 * Created by Kevin Crimi as part of the ToolTipDialog library published for free usage as determined by the Apache 2.0 license.
 * https://github.com/kcrimi/ToolTipDialog
**/
internal object ScreenUtils {

    private val density = Resources.getSystem().displayMetrics.density

    fun getPixels(dp: Float) = (dp * density + 0.5f).toInt()

    fun getScreenHeight(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        wm.defaultDisplay.getSize(size)
        return size.y
    }

    fun bitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(canvas)
        return bitmap
    }

    fun getSoftMenuHeight(context: Context): Int {
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }
}
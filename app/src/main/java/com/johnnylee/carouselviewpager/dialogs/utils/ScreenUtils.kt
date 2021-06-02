package com.johnnylee.carouselviewpager.dialogs.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.view.View

internal object ScreenUtils {

    private val density = Resources.getSystem().displayMetrics.density

    fun getPixels(dp: Float) = (dp * density).toInt()

    fun bitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(canvas)
        return bitmap
    }

    fun getViewRect(view: View): Rect {
        val point = getViewPoint(view)
        return Rect(point.x, point.y, point.x + view.measuredWidth,point.y + view.measuredHeight)
    }

    private fun getViewPoint(view: View): Point {
        val position = IntArray(2)
        view.getLocationInWindow(position)
        return Point(position[0], position[1])
    }

}
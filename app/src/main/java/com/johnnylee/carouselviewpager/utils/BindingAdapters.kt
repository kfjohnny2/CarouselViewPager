package com.johnnylee.carouselviewpager.utils

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.johnnylee.carouselviewpager.R

/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 5/3/2021.
 */
/**
 * Binding adapters for rounding view Borders Shape without the need of creating a new drawable res
 * You can pass individual borders corner radius for each side of the view and define a backgroundColor
 * Example: on the xml view, you can call it like that:
 *                                                      app:cornerBottomRight="@{20}"
 *                                                      app:cornerBottomLeft="@{20}"
 *                                                      app:cornerTopLeft="@{20}"
 *                                                      app:cornerTopRight="@{20}"
 *                                                      app:backgroundRoundedColor="@{@color/white}"
 * @param cornerBottomLeft Bottom Left Corner value
 * @param cornerBottomRight Bottom Right Corner value
 * @param cornerTopLeft Top Left Corner value
 * @param cornerTopRight Top Right Corner value
 * @param backgroundRoundedColor The background resource color
 */
@BindingAdapter(
    value = ["cornerTopRight", "cornerTopLeft", "cornerBottomRight", "cornerBottomLeft", "backgroundRoundedColor"],
    requireAll = false
)
fun View.setRoundedBackground(
    cornerTopRight: Float = 0f, cornerTopLeft: Float = 0f,
    cornerBottomRight: Float = 0f, cornerBottomLeft: Float = 0f,
    backgroundRoundedColor : Int = ContextCompat.getColor(context, R.color.white)) {
    val shape = ShapeDrawable(
        RoundRectShape(
            floatArrayOf(
        cornerTopLeft, cornerTopLeft, cornerTopRight, cornerTopRight,
        cornerBottomRight, cornerBottomRight, cornerBottomLeft, cornerBottomLeft), null, null)
    )
    shape.paint.color = backgroundRoundedColor
    this.background = shape
}
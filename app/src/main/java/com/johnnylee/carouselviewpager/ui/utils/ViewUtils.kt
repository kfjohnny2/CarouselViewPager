package com.johnnylee.carouselviewpager.ui.utils

import android.content.res.Resources
import androidx.viewpager2.widget.ViewPager2
import com.johnnylee.carouselviewpager.R
import kotlin.math.abs
import kotlin.math.max

/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
fun pageTransformer(resources : Resources): ViewPager2.PageTransformer {
    val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
    val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()
    return ViewPager2.PageTransformer { page, position ->
        val myOffset: Float = position * -(pageOffset * 2 + pageMargin)
        when {
            position < -1 -> {
                page.translationX = -myOffset
            }
            position <= 1 -> {
                val scaleFactor = max(0.7f, 1f - abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            }
            else -> {
                page.alpha = 0f
                page.translationX = myOffset
            }
        }
    }
}
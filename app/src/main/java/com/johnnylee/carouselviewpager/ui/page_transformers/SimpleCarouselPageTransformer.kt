package com.johnnylee.carouselviewpager.ui.page_transformers

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.annotation.DimenRes
import androidx.viewpager2.widget.ViewPager2
import com.johnnylee.carouselviewpager.R
import kotlin.math.abs
import kotlin.math.min

private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

class SimpleCarouselPageTransformer(
    private val sideItemVisibilityWidth: Float,
    private val currentItemHorizontalMargins: Float
) : ViewPager2.PageTransformer {

    private val pageTranslationX get() = sideItemVisibilityWidth + currentItemHorizontalMargins

    constructor(
        context: Context,
        @DimenRes sideItemVisibilityWidth: Int,
        @DimenRes currentItemHorizontalMargins: Int
    ) : this(
        context.resources.getDimension(sideItemVisibilityWidth),
        context.resources.getDimension(currentItemHorizontalMargins)
    )

    override fun transformPage(page: View, position: Float) {
        page.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 1 -> { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    //val scaleFactor = max(MIN_SCALE, 1 - abs(position))
                    val scaleFactor = 1 - min(0.25f, (0.25f * abs(position)))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                    translationX = if (position < 0) {
                        horzMargin - vertMargin / 2
                    } else {
                        horzMargin + vertMargin / 2
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // Fade the page relative to its size.
                    alpha = (MIN_ALPHA + (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
            page.translationX = -pageTranslationX * position
        }
    }
}
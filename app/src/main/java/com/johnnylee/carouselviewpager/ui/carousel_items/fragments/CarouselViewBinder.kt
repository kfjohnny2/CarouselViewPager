package com.johnnylee.carouselviewpager.ui.carousel_items.fragments

import android.view.View
import androidx.annotation.LayoutRes

/**
 * ViewBinder of FRAGMENTS carousel
 */
interface CarouselViewBinder<T> {

    /**
     * Get desired layout resource
     */
    @LayoutRes
    fun getLayoutRes(position: Int): Int

    /**
     * Draws the UI. Equivalent to RecyclerView.Adapter's onBindViewHolder
     * @param view  target view
     * @param item  Most updated item
     */
    fun drawUI(view: View, item: T, position: Int)

}
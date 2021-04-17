package com.johnnylee.carouselviewpager.ui.carousel_items.views

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

interface ICarouselViewBinder<T, VH: RecyclerView.ViewHolder> {

    /**
     * Get desired layout resource
     */
    @LayoutRes
    fun getLayoutRes(viewType: Int): Int

    /**
     * Draws the UI. Equivalent to RecyclerView.Adapter's onBindViewHolder
     * @param view  target view
     * @param item  Most updated item
     */
    fun drawUI(holder: VH, item: T, position: Int)

    /**
     * Instantiate view holder
     * @param view View
     * @return subclass of [RecyclerView.ViewHolder]
     */
    fun instantiateViewHolder(view: View): VH

}
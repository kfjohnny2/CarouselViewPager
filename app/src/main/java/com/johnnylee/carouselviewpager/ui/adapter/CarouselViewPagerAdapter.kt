package com.johnnylee.carouselviewpager.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnnylee.carouselviewpager.ui.carousel_items.views.ICarouselViewBinder

const val ARG_OBJECT_ID = "object"

/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class CarouselViewPagerAdapter<T, VH: RecyclerView.ViewHolder>(private val context: Context, private val carouselItemsList: MutableList<T>, private val viewBinder: ICarouselViewBinder<T, VH>) : RecyclerView.Adapter<VH>() {

    override fun getItemCount() = carouselItemsList.size

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): VH {
        val view: View = LayoutInflater.from(context).inflate(viewBinder.getLayoutRes(viewType), parent, false)
        return viewBinder.instantiateViewHolder(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        viewBinder.drawUI(holder, carouselItemsList[position], position)
    }

    //---------------------- Commands ----------------------

    fun hideItem(position: Int) {
        if (position < itemCount) {
            carouselItemsList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun addItem(position: Int = 0, item: T) {
        if (position <= itemCount) {
            carouselItemsList.add(position, item)
            notifyItemInserted(position)
        }
    }

    fun updateItem(position: Int, updateItem: T) {
        if (position < itemCount) {
            carouselItemsList[position] = updateItem
            notifyDataSetChanged()
        }
    }

    //-----------------------------------------------------
}
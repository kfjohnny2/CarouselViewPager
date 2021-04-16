package com.johnnylee.carouselviewpager.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnnylee.carouselviewpager.R
import com.johnnylee.carouselviewpager.ui.utils.extensions.setBackgroundColorFromId


const val ARG_OBJECT_ID = "object"

/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class CarouselViewPagerAdapter(private val context: Context) : RecyclerView.Adapter<CarouselViewPagerViewHolder>() {
    override fun getItemCount() = 10

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarouselViewPagerViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_carousel_selected, parent, false)
        return CarouselViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CarouselViewPagerViewHolder,
        position: Int
    ) {
        holder.text1.text = position.toString()

        if (position % 2 == 0) {
            holder.cardView.setBackgroundColorFromId(R.color.colorPrimary)
        } else {
            holder.cardView.setBackgroundColorFromId(R.color.colorSecondary);
        }
    }
}
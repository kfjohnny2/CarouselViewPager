package com.johnnylee.carouselviewpager.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.johnnylee.carouselviewpager.R

class CarouselViewPagerViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
    val text1: TextView = itemView.findViewById(R.id.text1)
    val cardView: MaterialCardView = itemView.findViewById(R.id.cardItem)
}

package com.johnnylee.carouselviewpager.ui.adapter.with_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.johnnylee.carouselviewpager.ui.adapter.ARG_OBJECT_ID
import com.johnnylee.carouselviewpager.ui.carousel_items.fragments.CarouselViewBinder
import com.johnnylee.carouselviewpager.ui.carousel_items.PagerItemFragment

/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class CarouselFragmentViewPagerAdapter<T>(fragment: Fragment, private val carouselItemsList: MutableList<T>, private val viewBinder: CarouselViewBinder<T>) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = carouselItemsList.size

    private val pageIds = carouselItemsList.map { it.hashCode().toLong() }

    override fun createFragment(position: Int): Fragment {
        val fragment = PagerItemFragment(carouselItemsList[position], viewBinder)
        fragment.arguments = Bundle(1).apply {
            putInt(ARG_OBJECT_ID, position)
        }
        return fragment
    }

    //------------------ Commands ------------------

    fun hideItem(position: Int) {
        carouselItemsList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(position: Int = 0, item: T) {
        carouselItemsList.add(position, item)
        notifyItemInserted(position)
    }

    fun updateItem(position: Int, updateItem: T) {
        carouselItemsList[position] = updateItem
        notifyDataSetChanged()
    }

    //---------------- End of Commands ---------------

    override fun getItemId(position: Int) = carouselItemsList[position].hashCode().toLong()

    override fun containsItem(itemId: Long) = pageIds.contains(itemId)
}
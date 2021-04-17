package com.johnnylee.carouselviewpager.ui.adapter.with_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.johnnylee.carouselviewpager.ui.adapter.ARG_OBJECT_ID

/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class CarouselFragmentViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 10

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = CarouselItemSelectedFragment()
        fragment.arguments = Bundle(1).apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT_ID, position + 1)
        }
        return fragment
    }
}
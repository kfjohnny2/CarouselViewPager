package com.johnnylee.carouselviewpager.ui.adapter.with_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.johnnylee.carouselviewpager.R
import com.johnnylee.carouselviewpager.ui.adapter.CarouselViewPagerAdapter
import com.johnnylee.carouselviewpager.ui.utils.pageTransformer

/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class CarouselPagerFragment: Fragment() {

    private lateinit var carouselFragmentViewPagerAdapter: CarouselFragmentViewPagerAdapter
    private lateinit var viewPagerFragment: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.carousel_pager_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupFragmentCarouselAdapter(view)
    }

    private fun setupFragmentCarouselAdapter(view : View){
            carouselFragmentViewPagerAdapter = CarouselFragmentViewPagerAdapter(this)
            viewPagerFragment = view.findViewById(R.id.pager_fragment)
            viewPagerFragment.adapter = carouselFragmentViewPagerAdapter
            val pageTransformer = pageTransformer(resources)
            viewPagerFragment.setPageTransformer(pageTransformer)
    }
}
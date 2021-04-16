package com.johnnylee.carouselviewpager

import android.app.ActionBar
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.johnnylee.carouselviewpager.ui.adapter.CarouselViewPagerAdapter
import com.johnnylee.carouselviewpager.ui.utils.pageTransformer


/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var carouselViewPagerAdapter: CarouselViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCarouselAdapter()
    }

    private fun setupCarouselAdapter() {
        carouselViewPagerAdapter = CarouselViewPagerAdapter(this)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = carouselViewPagerAdapter
        val pageTransformer = pageTransformer(resources)
        viewPager.setPageTransformer(pageTransformer)
    }

}
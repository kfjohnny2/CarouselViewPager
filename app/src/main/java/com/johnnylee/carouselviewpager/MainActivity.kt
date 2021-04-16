package com.johnnylee.carouselviewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.johnnylee.carouselviewpager.ui.adapter.CarouselViewPagerAdapter
import kotlin.math.abs
import kotlin.math.max

/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class MainActivity : AppCompatActivity() {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    private lateinit var carouselViewPagerAdapter: CarouselViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCarouselAdapter()
    }

    fun setupCarouselAdapter() {
        carouselViewPagerAdapter = CarouselViewPagerAdapter(this)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = carouselViewPagerAdapter
        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

        viewPager.setPageTransformer { page, position ->
            val myOffset: Float = position * -(2 * pageOffset + pageMargin)
            when {
                position < -1 -> {
                    page.translationX = -myOffset
                }
                position <= 1 -> {
                    val scaleFactor = max(0.7f, 1f - abs(position - 0.14285715f))
                    page.translationX = myOffset
                    page.scaleY = scaleFactor
                    page.alpha = scaleFactor
                }
                else -> {
                    page.alpha = 0f
                    page.translationX = myOffset
                }
            }
        }
    }
}
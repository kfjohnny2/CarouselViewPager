package com.johnnylee.carouselviewpager.ui.carousel_items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.johnnylee.carouselviewpager.ui.adapter.ARG_OBJECT_ID
import com.johnnylee.carouselviewpager.ui.carousel_items.fragments.CarouselViewBinder

class PagerItemFragment<T>(var item: T, private val viewBinder: CarouselViewBinder<T>): Fragment() {

    private lateinit var rootView: View

    private var position: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        arguments?.takeIf { it.containsKey(ARG_OBJECT_ID) }?.apply {
            position = getInt(ARG_OBJECT_ID)
        }
        return inflater.inflate(viewBinder.getLayoutRes(position), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rootView = view
        if (position >= 0) {
            viewBinder.drawUI(view, item, position)
        }
    }
}
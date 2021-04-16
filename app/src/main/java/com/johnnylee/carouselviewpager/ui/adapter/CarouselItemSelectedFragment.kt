package com.johnnylee.carouselviewpager.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.johnnylee.carouselviewpager.R

/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class CarouselItemSelectedFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.item_carousel_selected, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT_ID) }?.apply {
            val textView: TextView = view.findViewById(R.id.text1)
            textView.text = getInt(ARG_OBJECT_ID).toString()
        }
    }
}
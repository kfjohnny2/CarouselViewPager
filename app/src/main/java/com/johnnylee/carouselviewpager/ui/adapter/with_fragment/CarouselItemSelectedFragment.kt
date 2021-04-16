package com.johnnylee.carouselviewpager.ui.adapter.with_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.johnnylee.carouselviewpager.R
import com.johnnylee.carouselviewpager.ui.adapter.ARG_OBJECT_ID
import com.johnnylee.carouselviewpager.ui.utils.extensions.setBackgroundColorFromId

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
            val position = getInt(ARG_OBJECT_ID)
            val textView: TextView = view.findViewById(R.id.text1)
            val cardView: MaterialCardView = view.findViewById(R.id.cardItem)
            textView.text = position.toString()

            if (position % 2 == 0) {
                cardView.setBackgroundColorFromId(R.color.colorPrimary)
            } else {
                cardView.setBackgroundColorFromId(R.color.colorSecondary);
            }
        }
    }
}
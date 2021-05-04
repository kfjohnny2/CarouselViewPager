package com.johnnylee.carouselviewpager.ui.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.johnnylee.carouselviewpager.R

class BottomSheetHostFragment: BottomSheetDialogFragment() {

    var displayFragment: Fragment? = null

    companion object {
        @JvmStatic
        fun newInstance(fragment: Fragment? = null) = BottomSheetHostFragment().apply { displayFragment = fragment }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.screen_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {

        displayFragment?.let { fragment ->
            childFragmentManager
                .beginTransaction()
                .replace(R.id.bottomSheetHostFrameLayout, fragment)
                .addToBackStack(null)
                .commit()
        }

    }
}
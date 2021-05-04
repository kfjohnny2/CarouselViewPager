package com.johnnylee.carouselviewpager.ui.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.johnnylee.carouselviewpager.R

class BottomSheetHostFragment private constructor(private val displayFragment: Fragment, private val runnableOnCommit: Runnable = Runnable {  }): BottomSheetDialogFragment() {

    companion object {
        @JvmStatic
        @JvmOverloads
        fun newInstance(fragment: Fragment, runnable: Runnable = Runnable {  }) = BottomSheetHostFragment(fragment, runnable)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.screen_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setContentFragment()
    }

    private fun setContentFragment() {
        displayFragment.let { fragment ->
            childFragmentManager
                .beginTransaction()
                .replace(R.id.bottomSheetHostFrameLayout, fragment)
                .runOnCommit(runnableOnCommit)
                .commit()
        }
    }
}
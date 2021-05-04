package com.johnnylee.carouselviewpager.ui.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.johnnylee.carouselviewpager.R
import com.johnnylee.carouselviewpager.databinding.ScreenBottomsheetBinding

class BottomSheetHostFragment private constructor(private val displayFragment: Fragment, private val runnableOnCommit: Runnable = Runnable {  }): BottomSheetDialogFragment() {
    private lateinit var binding : ScreenBottomsheetBinding

    companion object {
        @JvmStatic
        @JvmOverloads
        fun newInstance(fragment: Fragment, runnable: Runnable = Runnable {  }) = BottomSheetHostFragment(fragment, runnable)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.screen_bottomsheet, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
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
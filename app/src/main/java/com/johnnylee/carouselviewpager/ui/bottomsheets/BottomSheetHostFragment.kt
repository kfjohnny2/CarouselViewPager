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

class BottomSheetHostFragment constructor(private val displayFragment: Fragment, private val builder: Builder? = null): BottomSheetDialogFragment() {

    private lateinit var binding : ScreenBottomsheetBinding

    var hasCloseButton: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.screen_bottomsheet, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // style to remove default background
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setBehaviour()
        setContentFragment()
        isCancelable = false
    }

    // --------------------- Helper methods ---------------------

    private fun setContentFragment() {
        displayFragment.let { fragment ->
            childFragmentManager
                .beginTransaction()
                .replace(R.id.bottomSheetHostFrameLayout, fragment)
                .commit()
        }
    }

    private fun setBehaviour() {
        binding.close.setOnClickListener { dismiss() }

        binding.close.visibility = if (hasCloseButton) View.VISIBLE else View.GONE
    }

    fun setIsCancelable(boolean: Boolean): BottomSheetHostFragment {
        isCancelable = boolean
        return this
    }

    fun setHasCloseButton(boolean: Boolean): BottomSheetHostFragment {
        hasCloseButton = boolean
        return this
    }

    // -------------------- Builder -------------------

    class Builder {

        var hasCloseButton: Boolean = false
        var cancelable: Boolean = true

        fun setHasCloseButton(boolean: Boolean): Builder {
            hasCloseButton = boolean
            return this
        }

        fun setIsCancelable(boolean: Boolean): Builder {
            cancelable = boolean
            return this
        }
    }
}
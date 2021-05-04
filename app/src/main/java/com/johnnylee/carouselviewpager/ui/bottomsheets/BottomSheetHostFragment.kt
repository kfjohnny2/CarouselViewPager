package com.johnnylee.carouselviewpager.ui.bottomsheets

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.johnnylee.carouselviewpager.R
import com.johnnylee.carouselviewpager.databinding.ScreenBottomsheetBinding

private const val DEFAULT_CORNER_TOP_RIGHT = 50F
private const val DEFAULT_CORNER_TOP_LEFT = 50F
private const val DEFAULT_CORNER_BOTTOM_RIGHT = 0F
private const val DEFAULT_CORNER_BOTTOM_LEFT = 0F
private const val DEFAULT_COLOR = R.color.purple_500

class BottomSheetHostFragment constructor(private val displayFragment: Fragment): BottomSheetDialogFragment() {

    private lateinit var binding : ScreenBottomsheetBinding

    var hasCloseButton: Boolean = false
    var onDismissCallback: GenericCallback? = null
    var customBackground = BackgroundParameters(DEFAULT_COLOR, DEFAULT_CORNER_TOP_LEFT, DEFAULT_CORNER_TOP_RIGHT, DEFAULT_CORNER_BOTTOM_LEFT, DEFAULT_CORNER_BOTTOM_RIGHT)

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onDismissCallback?.callback()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.screen_bottomsheet, container, false)
        // Assure the dialog height is full content when showing
        dialog?.setOnShowListener {
            BottomSheetBehavior.from(binding.root.parent as View).peekHeight = binding.parentCL.measuredHeight
        }
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
        binding.bsFragment = this
        binding.close.setOnClickListener { dismiss() }
        binding.close.visibility = if (hasCloseButton) View.VISIBLE else View.GONE
    }

    // --------------------  Internal Builder --------------------

    /**
     * Set [isCancelable] state
     * True to allow BottomSheet to be dragged and closed using the normal BottomSheet's behaviours
     * False to make the BottomSheet static. If this happens, the bottomsheet
     * can only be dismissed through a manual [dismiss]
     * @param canCancel isCancelable final state
     * @return current [BottomSheetHostFragment] instance
     */
    fun setIsCancelable(canCancel: Boolean): BottomSheetHostFragment {
        isCancelable = canCancel
        return this
    }

    /**
     * Set Close icon visibility
     * @param show True if the close icon will be visible, False otherwise
     * @return current [BottomSheetHostFragment] instance
     */
    fun setHasCloseIcon(show: Boolean): BottomSheetHostFragment {
        hasCloseButton = show
        return this
    }

    /**
     * Set On Dismiss listener
     * @param listener Listener
     * @return current [BottomSheetHostFragment] instance
     */
    fun withOnDismissListener(listener: GenericCallback): BottomSheetHostFragment {
        onDismissCallback = listener
        return this
    }

    /**
     * Set custom BottomSheet background
     * @param color             Desired color
     * @param cornerTopLeft     Top Left corner radius
     * @param cornerTopRight    Top Right corner radius
     * @return current [BottomSheetHostFragment] instance
     */
    fun withCustomBackground(color: Int, cornerTopLeft: Float = 50F, cornerTopRight: Float = 50F): BottomSheetHostFragment {
        customBackground = BackgroundParameters(color, cornerTopLeft, cornerTopRight,0f,0f)
        return this
    }

    //-------------------- End of Internal Builder --------------------

    data class BackgroundParameters(val color: Int, val  cornerTopLeft: Float, val  cornerTopRight: Float, val cornerBottomLeft: Float, val cornerBottomRight: Float)

}
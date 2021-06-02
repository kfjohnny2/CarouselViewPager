package com.johnnylee.carouselviewpager.dialogs

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.johnnylee.carouselviewpager.R
import com.johnnylee.carouselviewpager.dialogs.utils.ScreenUtils

/**
 * Copyright (c) $today.year.
 * Created by Rafael Ramos e Johnnylee Rocha
 *
 * A dialog with a dialog box and an arrow to be used to point out different parts of the underlying
 * UI. PeekThroughViews can be passed in from the underlying view as well in order to draw them
 * onto a background bitmap of the the Dialog which gives the appearance of those views "peeking
 * through" the bg shade.
 * A custom layout can be inserted into this Dialog, however it must have the following IDs
 * tooltip_background_view - The outer section of the dialog. It must be a RelativeLayout;
 * tooltip_dialog_container - The parent view of the Dialog itself;
 * tooltip_title - The title TextView;
 * tooltip_description - The description TextView;
 * tooltip_top_arrow - The Arrow ImageView that will be used when pointing upwards;
 * tooltip_bottom_arrow - The Arrow ImageView that will be used when pointing downwards;
 *
 * Example Usage:
 *  val viewRect = ScreenUtils.getViewRect(view)
 *  ToolTipDialog(getActivity())
 *      .pointToView(viewRect)
 *      .description(R.id.my_example_description_resource
 *      .addPeekThroughView(targetView)
 *      .addPeekThroughView(otherView)
 *      .runOnDismiss { Toast.makeText(context, "Dialog being dismissed", Toast.LENGHT_LONG).show() }
 *      .show();
 */
class ToolTipDialog(context: Context, @LayoutRes layoutResource: Int = R.layout.tootip_dialog, themeStyleRes: Int = R.style.TooltipDialogTheme) : Dialog(context, themeStyleRes) {

    enum class Position { AUTO, ABOVE, BELOW }

    private var arrowWidth = ScreenUtils.getPixels(15f)
    private var contentView : RelativeLayout
    private var container : ViewGroup
    private var upArrow : ImageView
    private var downArrow : ImageView
    private var titleText : TextView
    private var descriptionText : TextView
    private var peekThroughViews = ArrayList<View>()

    private var canCancel: Boolean = true

    private val displayMetrics get() = Resources.getSystem().displayMetrics

    private val windowHeight get() = displayMetrics.heightPixels
    private val windowWidth get() = displayMetrics.widthPixels

    private var modifyViewFunc: ((View) -> Unit)? = null
    private var restoreViewFunc: ((View) -> Unit)? = null

    // General tooltip view. Can be used for extra control
    val tooltipView get() = window?.decorView

    init {

        setContentView(layoutResource)
        contentView = findViewById(R.id.tooltip_background_view)
        container = findViewById(R.id.tooltip_dialog_container)
        upArrow = findViewById(R.id.tooltip_top_arrow)
        downArrow = findViewById(R.id.tooltip_bottom_arrow)
        titleText = findViewById(R.id.tooltip_title)
        descriptionText = findViewById(R.id.tooltip_description)

        if (canCancel) {
            container.setOnClickListener { dismiss() }
            contentView.setOnClickListener { dismiss() }
        }

        // Make Dialog window span the entire screen
        window?.run {
            setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

    }

    override fun show() {
        if (peekThroughViews.isNotEmpty()) drawPeekingViews() else drawShade()
        super.show()
    }

    //---------------------------- Helper methods ----------------------------

    private fun drawPeekingViews() {
        val bitmap = Bitmap.createBitmap(windowWidth, windowHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(ContextCompat.getColor(context, R.color.tooltip_background_shade_dark))
        peekThroughViews.forEach { view ->
            // Call function to modify the View
            modifyViewFunc?.invoke(view)
            val viewBitmap = ScreenUtils.bitmapFromView(view)
            val rect = ScreenUtils.getViewRect(view)
            // Call function to restore the View
            restoreViewFunc?.invoke(view)
            canvas.drawBitmap(viewBitmap,null, rect,null)
        }
        contentView.background = BitmapDrawable(context.resources, bitmap)
    }

    private fun drawShade() {
        val bitmap = Bitmap.createBitmap(windowWidth, windowHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(ContextCompat.getColor(context, R.color.tooltip_background_shade_default))
        contentView.background = BitmapDrawable(context.resources, bitmap)
    }

    private fun pointArrowTo(arrow: ImageView, x: Int) {
        val arrowParams = arrow.layoutParams as RelativeLayout.LayoutParams
        if (x >= windowWidth / 2) {
            arrowParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            arrowParams.rightMargin = windowWidth - x - arrowWidth / 2
        } else {
            arrowParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            arrowParams.leftMargin = x - arrowWidth / 2
        }
        arrow.layoutParams = arrowParams
        arrow.visibility = View.VISIBLE
    }

    private fun adjustContainerMargin(x: Int) {
        var leftMargin = context.resources.getDimension(R.dimen.tooltip_dialog_activity_margin)
        var rightMargin = leftMargin
        if(x > windowWidth - windowWidth / 3) {
            leftMargin = 30f
            rightMargin = 0f
        } else if (x < windowWidth / 3) {
            leftMargin = 0f
            rightMargin = 30f
        }
        val params = container.layoutParams as RelativeLayout.LayoutParams
        params.leftMargin = ScreenUtils.getPixels(leftMargin)
        params.rightMargin = ScreenUtils.getPixels(rightMargin)
        container.layoutParams = params
    }

    //------------------------------ Fluent interface ------------------------------

    /**
     * Point arrow to a given rect. If [position] is set to [Position.BELOW], the Dialog will show
     * up below the target rect, pointing to the Bottom part of the rect. If the [position] is set
     * to [Position.ABOVE], the Dialog will show up above the target rect, pointing to the Top part
     * of the rect. If no [Position], or [Position.AUTO] is selected the Dialog position will be
     * shown according to the rect's position on the screen.
     * @param rect      Target rect that will be pointed to
     * @param position  [Position] that can be used to force the Dialog to be shown above or below target
     * @return  Instance of [ToolTipDialog]
     */
    fun pointToView(rect: Rect, position: Position = Position.AUTO): ToolTipDialog {
        val params = container.layoutParams as RelativeLayout.LayoutParams
        adjustContainerMargin(rect.centerX())

        if (position == Position.BELOW || ((rect.centerY() < windowHeight / 2) && position == Position.AUTO)) {
            // When the view is above the mid point OR when Position.BELOW was forced
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP)
            params.topMargin = rect.bottom
            if (rect.centerX() >= 0) {
                pointArrowTo(upArrow, rect.centerX())
            }
        } else {
            // When the view in below the mid point OR when Position.ABOVE was forced
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            params.bottomMargin = windowHeight - rect.top
            if (rect.centerX() >= 0) {
                pointArrowTo(downArrow, rect.centerX())
            }
        }

        container.layoutParams = params
        return this
    }

    /**
     * Sets the y position of the top of the dialog box. This will not use any arrow pointers
     * @param y Position that will correspond to the top of the dialog
     * @return  Instance of [ToolTipDialog]
     */
    fun setYPosition(y: Int) : ToolTipDialog {
        val params = container.layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        params.topMargin = y
        return this
    }

    /**
     * Add views that will be drawn onto the dialog shade
     * @param view  Add view to the peek through views' list
     * @return  Instance of [ToolTipDialog]
     */
    fun addPeekThroughView(view : View) : ToolTipDialog {
        peekThroughViews.add(view)
        return this
    }

    /**
     * Allow/Forbid Dialog to be cancelled by clicking on it or outside
     * @param isCancelable True to be cancelable, False otherwise
     * @return  Instance of [ToolTipDialog]
     */
    fun setIsCancelable(isCancelable: Boolean): ToolTipDialog {
        canCancel = isCancelable
        return this
    }

    fun title(@StringRes title: Int): ToolTipDialog {
        with (titleText) {
            setText(title)
            visibility = View.VISIBLE
        }
        return this
    }

    fun description(@StringRes description: Int): ToolTipDialog {
        with (descriptionText) {
            setText(description)
            visibility = View.VISIBLE
        }
        return this
    }

    /**
     * Run on Dialog dismissed
     * @param onDismissFunc Function that will be ran on dialog dismissed
     * @return  Instance of [ToolTipDialog]
     */
    fun runOnDismiss(onDismissFunc: () -> Unit): ToolTipDialog {
        setOnDismissListener { onDismissFunc() }
        return this
    }

    /**
     * Modify the view before creating the bitmap, that will be shown in front of the actual View
     * @param modifyViewFunc    Function that will be ran before creating the bitmap
     * @return  Instance of [ToolTipDialog]
     */
    fun modifyView(modifyViewFunc: (View) -> Unit): ToolTipDialog {
        this.modifyViewFunc = modifyViewFunc
        return this
    }

    /**
     * Restore the view before creating the bitmap, that will be shown in front of the actual View
     * @param restoreViewFunc    Function that will be ran before creating the bitmap
     * @return  Instance of [ToolTipDialog]
     */
    fun restoreView(restoreViewFunc: (View) -> Unit): ToolTipDialog {
        this.restoreViewFunc = restoreViewFunc
        return this
    }
}

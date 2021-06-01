package com.johnnylee.carouselviewpager

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.johnnylee.carouselviewpager.databinding.ActivityMain2Binding
import com.johnnylee.carouselviewpager.dialogs.utils.ScreenUtils
import com.kcrimi.tooltipdialog.ToolTipDialog


/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class MainActivity : AppCompatActivity(), LifecycleOwner, View.OnClickListener {
    private lateinit var binding : ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        binding.button.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.button5.setOnClickListener(this)
        binding.button6.setOnClickListener(this)

    }

    //--------------- Helper methods ------------------

    private fun toast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        v?.run {
            val location = intArrayOf(0, 0)
            v.getLocationInWindow(location)

            ToolTipDialog(this@MainActivity)
                .title("Dialog can point up!")
                .pointTo(
                    location[0] + v.width / 2,
                    location[1] + (v.height / 2) + ScreenUtils.getSoftMenuHeight(this@MainActivity),
                    ToolTipDialog.Position.ABOVE
                )
                .content("This is pointing up to the button you just clicked")
                .addPeekThroughView(v)
                .subtitle("Tooltip with arrow")
                .show()
        }
    }
}
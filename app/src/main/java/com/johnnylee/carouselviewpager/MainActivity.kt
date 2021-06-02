package com.johnnylee.carouselviewpager

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.johnnylee.carouselviewpager.databinding.ActivityMain2Binding
import com.johnnylee.carouselviewpager.dialogs.utils.ScreenUtils
import com.johnnylee.carouselviewpager.dialogs.ToolTipDialog


/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class MainActivity : AppCompatActivity(), LifecycleOwner, View.OnClickListener {
    private lateinit var binding : ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        actionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        binding.button.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.button5.setOnClickListener(this)
        binding.button6.setOnClickListener(this)
        binding.button7.setOnClickListener(this)
        binding.button8.setOnClickListener(this)
        binding.button9.setOnClickListener(this)
        binding.button10.setOnClickListener(this)
        binding.button11.setOnClickListener(this)
        binding.button12.setOnClickListener(this)
        binding.button13.setOnClickListener(this)
        binding.button14.setOnClickListener(this)
        binding.button15.setOnClickListener(this)
    }

    //--------------- Helper methods ------------------

    private fun toast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        v?.run {

            val screenUtils = ScreenUtils
            val rect = screenUtils.getViewRect(v)

            ToolTipDialog(this@MainActivity, R.layout.activity_main2)
                .title(R.string.title)
                .subtitle(R.string.sub_title)
                .description(R.string.description)
                .addPeekThroughView(v)
                .runOnDismiss { toast("Dialog being dismissed") }
                .setIsCancelable(false)
                .pointToView(rect)
                .show()
        }
    }
}
package com.johnnylee.carouselviewpager

import android.graphics.Rect
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.johnnylee.carouselviewpager.databinding.ActivityMain2Binding
import com.kcrimi.tooltipdialog.ToolTipDialog


/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class MainActivity : AppCompatActivity(), LifecycleOwner {
    private lateinit var binding : ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        binding.button.setOnClickListener {

            val rect = Rect()
            it.getGlobalVisibleRect(rect)

            ToolTipDialog(this, this)
                .title("Example title")
                .pointTo()
                .setYPosition(rect.bottom + 50)
                .addPeekThroughView(it)
                .show()
        }


    }

    //--------------- Helper methods ------------------

    private fun toast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    }
}
package com.johnnylee.carouselviewpager

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.johnnylee.carouselviewpager.databinding.ActivityMainBinding
import com.johnnylee.carouselviewpager.ui.adapter.CarouselViewPagerAdapter
import com.johnnylee.carouselviewpager.ui.adapter.CarouselViewPagerViewHolder
import com.johnnylee.carouselviewpager.ui.adapter.with_fragment.CarouselPagerFragment
import com.johnnylee.carouselviewpager.ui.bottomsheets.BottomSheetHostFragment
import com.johnnylee.carouselviewpager.ui.carousel_items.views.ICarouselViewBinder
import com.johnnylee.carouselviewpager.ui.page_transformers.SimpleCarouselPageTransformer
import com.johnnylee.carouselviewpager.ui.utils.extensions.setBackgroundColorFromId


/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class MainActivity : AppCompatActivity(), LifecycleOwner {
    private lateinit var binding : ActivityMainBinding

    private val position
        get() = if (binding.editText.text.toString().isEmpty()) 0 else binding.editText.text.toString().toInt()

    private val viewBinder = object: ICarouselViewBinder<String, CarouselViewPagerViewHolder> {

        override fun getLayoutRes(viewType: Int) = R.layout.item_carousel_selected

        override fun drawUI(holder: CarouselViewPagerViewHolder, item: String, position: Int) {
            holder.text1.text = "Pos on loaded: $position"
            holder.textString.text = item

            if (position % 2 == 0) {
                holder.cardView.setBackgroundColorFromId(R.color.colorPrimary)
            } else {
                holder.cardView.setBackgroundColorFromId(R.color.colorSecondary);
            }
        }

        override fun instantiateViewHolder(view: View) = CarouselViewPagerViewHolder(view)
    }

    private lateinit var carouselViewPagerAdapter: CarouselViewPagerAdapter<String, CarouselViewPagerViewHolder>
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setButtonListeners()
        setupCarouselAdapter()
        setBottomSheet()
    }

    private fun setBottomSheet() {
        supportFragmentManager.let {
            val myBuilder = BottomSheetHostFragment.Builder()
            myBuilder
                .setHasCloseButton(false)
                .setIsCancelable(false)

            val mFragment =
                BottomSheetHostFragment(CarouselPagerFragment())
                    .setHasCloseButton(false)
                    .setIsCancelable(false)
                    .show(it, "MY TAG")
            //.apply { show(it, tag)  }
        }
    }

    private fun setButtonListeners() {
        binding.hideBtn.setOnClickListener {
            carouselViewPagerAdapter.hideItem(position)
            toast("Removing fragment at position $position")
        }
        binding.addBtn.setOnClickListener {
            carouselViewPagerAdapter.addItem(position, "Test fragment...")
            toast("Adding fragment at position $position")
        }
        binding.updateBtn.setOnClickListener {
            carouselViewPagerAdapter.updateItem(position, "changed string")
            toast("Updated fragment at position $position")
        }
    }

    private fun setupCarouselAdapter() {

        //------------------ Input try ------------------

        val displayList = mutableListOf("Frag zero", "Frag one", "Frag two", "Frag three", "Frag four", "Frag five", "Frag six", "Frag seven", "Frag eight", "Frag nine", "Frag ten")

        //------------------ --------- ------------------

        carouselViewPagerAdapter = CarouselViewPagerAdapter(this, displayList, viewBinder)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = carouselViewPagerAdapter
        viewPager.offscreenPageLimit = 1

        val pageTransformer = SimpleCarouselPageTransformer(this, R.dimen.viewpager_default_side_item_visibility_width, R.dimen.viewpager_current_item_horizontal_margins)
        viewPager.setPageTransformer(pageTransformer)
    }

    //--------------- Helper methods ------------------

    private fun toast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    }
}
package com.johnnylee.carouselviewpager.ui.adapter.with_fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.johnnylee.carouselviewpager.R
import com.johnnylee.carouselviewpager.ui.carousel_items.fragments.CarouselViewBinder
import com.johnnylee.carouselviewpager.ui.page_transformers.SimpleCarouselPageTransformer
import com.johnnylee.carouselviewpager.ui.utils.extensions.setBackgroundColorFromId

/**
 * @author Created by Johnnylee Rocha (kfjohnny2) on 4/16/2021.
 */
class CarouselPagerFragment: Fragment() {

    private lateinit var carouselFragmentViewPagerAdapter: CarouselFragmentViewPagerAdapter<String>
    private lateinit var viewPagerFragment: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.carousel_pager_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //------------------ UI FOR TESTS ------------------
        view.findViewById<Button>(R.id.hideBtn).setOnClickListener {
            val position = view.findViewById<EditText>(R.id.editText).text.toString()
            carouselFragmentViewPagerAdapter.hideItem(position.toInt())
            toast("Removing fragment at position $position")
        }
        view.findViewById<Button>(R.id.addBtn).setOnClickListener {
            val position = view.findViewById<EditText>(R.id.editText).text.toString()
            carouselFragmentViewPagerAdapter.addItem(position.toInt(), "Test fragment...")
            toast("Adding fragment at position $position")
        }
        view.findViewById<Button>(R.id.updateBtn).setOnClickListener {
            val position = view.findViewById<EditText>(R.id.editText).text.toString()
            carouselFragmentViewPagerAdapter.updateItem(position.toInt(), "changed string")
            toast("Updated fragment at position $position")
        }
        //------------------ ------------ ------------------

        setupFragmentCarouselAdapter(view)
    }

    private fun setupFragmentCarouselAdapter(view : View) {
        //------------------ Input try ------------------

        val displayList = mutableListOf("Frag zero", "Frag one", "Frag two", "Frag three","Frag four","Frag five","Frag six","Frag seven","Frag eight","Frag nine","Frag ten")

        //------------------ --------- ------------------

        viewPagerFragment = view.findViewById(R.id.pager_fragment)
        carouselFragmentViewPagerAdapter = CarouselFragmentViewPagerAdapter(this, displayList, object:
            CarouselViewBinder<String> {
            override fun getLayoutRes(position: Int) = R.layout.item_carousel_selected

            override fun drawUI(view: View, item: String, position: Int) {
                val textView: TextView = view.findViewById(R.id.text1)
                val textStringView: TextView = view.findViewById(R.id.textString)
                val cardView: MaterialCardView = view.findViewById(R.id.cardItem)
                textView.text = position.toString()
                textStringView.text = item

                if (position % 2 == 0) {
                    cardView.setBackgroundColorFromId(R.color.colorPrimary)
                } else {
                    cardView.setBackgroundColorFromId(R.color.colorSecondary)
                }
            }
        })
        viewPagerFragment.adapter = carouselFragmentViewPagerAdapter
        formatCarousel(view.context)
    }

    private fun formatCarousel(context: Context) {
        viewPagerFragment.offscreenPageLimit = 1
        //TODO("When we create the layout components, maybe we should create multiple variants for the different types of page transformers? Or create just 1 and allow the user to input the desired transformer")
        //TODO("Additionally, we should pass direct values instead of the resources, to ease user's manipulation of these values")
        viewPagerFragment.setPageTransformer(SimpleCarouselPageTransformer(context, R.dimen.viewpager_default_side_item_visibility_width, R.dimen.viewpager_current_item_horizontal_margins))
        //TODO("Same with the Item decorator inputs below")
        val itemDecoration = HorizontalMarginItemDecoration(context, R.dimen.viewpager_current_item_horizontal_margins)
        viewPagerFragment.addItemDecoration(itemDecoration)
    }

    /**
     * Adds margin to the left and right sides of the RecyclerView item.
     * Adapted from https://stackoverflow.com/a/27664023/4034572
     * @param horizontalMarginInDp the margin resource, in dp.
     */
    private class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) : RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int = context.resources.getDimension(horizontalMarginInDp).toInt()

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }
    }

    //----------------- Helper methods ----------------

    private fun toast(content: String) {
        Toast.makeText(activity, content, Toast.LENGTH_LONG).show()
    }
}


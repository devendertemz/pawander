package com.thetemz.pawnder.Adapter






import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.thetemz.pawnder.R
import com.thetemz.pawnder.model.SliderDataItem

class OnboardingViewPagerAdapter(
    var context: Context,
    var sliderDataItemList: ArrayList<SliderDataItem>
) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_item_view_pager, container, false)

        val itemImage = view.findViewById(R.id.img_slider) as ImageView
        val textBelowLogo = view.findViewById(R.id.tv_image_slider_text) as TextView
        textBelowLogo.text = sliderDataItemList.get(position).textBelowImage
        itemImage.setImageDrawable(context.getDrawable(sliderDataItemList.get(position).imageItem))
        val viewPager = container as ViewPager
        viewPager.addView(view, 0)
        return view
    }

    override fun getCount(): Int {
        return sliderDataItemList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)

    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1 as View
    }

}

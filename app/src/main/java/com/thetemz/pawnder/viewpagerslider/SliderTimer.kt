package com.example.mymftcustomer.viewpagerslider

import android.app.Activity
import android.content.Context

import androidx.viewpager.widget.ViewPager
import java.util.*


class SliderTimer(private val viewPager: ViewPager, private val size: Int, private val activity: Activity) : TimerTask() {
    override fun run() {

        activity.runOnUiThread {
            if (viewPager.currentItem < size - 1) {
                viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            } else {

                viewPager.setCurrentItem(0, true)
            }
        }


    }
}
package com.example.mymftcustomer.utils

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class SpacingItemDecorator(private val padding: Int) : RecyclerView.ItemDecoration()
{
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    )
    {
        super.getItemOffsets(outRect, view, parent, state)
       // outRect.top = padding
        outRect.bottom = padding
        outRect.left = padding
        outRect.right = padding
        if (parent.getChildLayoutPosition(view) == 0) {
        outRect.top = padding;
    } else {
        outRect.top = 0;
    }

    }
}

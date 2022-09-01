package com.thetemz.pawnder.Adapter

import AccountSubTypeResModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.thetemz.pawnder.R

class AccountSubTypeSpinnerAdapter (val context: Context, var dataSource: AccountSubTypeResModel) : BaseAdapter() {


    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.state_layout, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = dataSource.data.get(position).accountSubTypeName
/*

        val id = context.resources.getIdentifier(dataSource.get(position).url, "drawable", context.packageName)
        vh.img.setBackgroundResource(id)
*/

        return view
    }

    override fun getItem(position: Int): Any? {
        return dataSource.data[position];
    }

    override fun getCount(): Int {
        return dataSource.data.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val label: TextView

        init {
            label = row?.findViewById(R.id.coinName) as TextView
        }
    }

}
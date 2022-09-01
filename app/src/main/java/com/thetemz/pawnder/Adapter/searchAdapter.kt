package com.thetemz.pawnder.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thetemz.pawnder.R
import com.thetemz.pawnder.databinding.AllpetlistAdapterBinding
import com.thetemz.pawnder.databinding.ItemSearchBinding
import com.thetemz.pawnder.model.CategoryData
import com.thetemz.pawnder.model.Data1
import java.util.*
import kotlin.collections.ArrayList

 class   searchAdapter(private var countryList: ArrayList<CategoryData>,
                       private  var click:searchClickInterface

 ) :
RecyclerView.Adapter<searchAdapter.CountryViewHolder>(), Filterable {

    var countryFilterList = ArrayList<CategoryData>()
     private lateinit var clickon: searchClickInterface
     private lateinit var mContext: Context

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view)
     lateinit var adapter: searchAdapter

    init {
        countryFilterList = countryList
        clickon = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        val sch = CountryViewHolder(v)
        mContext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val nametv =
            holder.itemView.findViewById<TextView>(R.id.tv_title)
        nametv.setBackgroundColor(Color.TRANSPARENT)

        val ll_Click =
            holder.itemView.findViewById<LinearLayout>(R.id.ll_click)
        nametv.setText(countryFilterList[position].categoryName)
        ll_Click.setOnClickListener{
            clickon. onSelectedClick(countryFilterList[position])
        }

        /*selectCountryTextView.setTextColor(Color.WHITE)
        selectCountryTextView.text = countryFilterList[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, DetailsActivity::class.java)
            intent.putExtra("passselectedcountry", countryFilterList[position])
            mContext.startActivity(intent)
            Log.d("Selected:", countryFilterList[position])
        }*/
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = countryList
                } else {
                    val resultList = ArrayList<CategoryData>()
                    for (row in countryList) {
                        if (row.categoryName?.lowercase(Locale.ROOT)
                                ?.contains(charSearch.lowercase(Locale.ROOT))!!
                        ) {
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList


                  /*  val resultList = ArrayList<CategoryData>()
                    for (row in countryList) {
                        if (row.categoryName
                                !!.contains(charSearch)
                        ) {
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList*/
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<CategoryData>
                notifyDataSetChanged()
            }

        }
    }

     interface searchClickInterface {
         fun onSelectedClick(petId : CategoryData)
     }
}
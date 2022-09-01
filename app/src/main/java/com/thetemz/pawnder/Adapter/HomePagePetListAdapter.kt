package com.thetemz.pawnder.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thetemz.pawnder.R
import com.thetemz.pawnder.databinding.AllpetlistAdapterBinding
import com.thetemz.pawnder.databinding.CustomPetlistLayoutBinding
import com.thetemz.pawnder.model.allpetlistdata
import com.thetemz.pawnder.model.petListResModel

class HomePagePetListAdapter: RecyclerView.Adapter<HomePagePetListAdapter.ViewHolder>() {


    private lateinit var binding: AllpetlistAdapterBinding
    var mContext : Context? = null
    private var PetList = ArrayList<allpetlistdata>()
    private lateinit var click: PetClickInterface


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        binding = AllpetlistAdapterBinding.inflate(inflater,parent,false)
        return ViewHolder(binding.root)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val PetList = PetList[position]
        binding.petnameTV.text = "Pet Name: "+PetList.petName
        binding.agetv.text = "Pet Age: "+PetList.petAge
        binding.gendertv.text = "pet Gender: "+PetList.petGender
        binding.locationTv.text = "pet Location: "+PetList.address
        binding.cateoriestv.text = "pet Category: "+PetList.pet_category_name


        if(PetList.file!=null){

            Glide.with(mContext ?: return)
                .load("https://miraanrai.com/pet_application/storage/app/"+PetList.file)
                .error(R.drawable.nofound)
                .placeholder(R.drawable.nofound)
                .into(binding.foodIV!!)

        }


        binding.cardView.setOnClickListener {
            click.onPetMoreClick(PetList)
        }
    }


    override fun getItemCount(): Int {
        return PetList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

    }

    fun setData(data :  ArrayList<allpetlistdata>){
        PetList.clear()
        for(notification in data){
            PetList.add(notification)
        }
        notifyDataSetChanged()
    }

    fun setPetListItemClickListener(callback : PetClickInterface){
        click = callback
    }
    interface PetClickInterface {
        fun onPetMoreClick(petId : allpetlistdata)
    }
}
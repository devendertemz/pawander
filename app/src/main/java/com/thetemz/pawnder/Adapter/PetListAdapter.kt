package com.thetemz.pawnder.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thetemz.pawnder.R
import com.thetemz.pawnder.databinding.CustomPetlistLayoutBinding
import com.thetemz.pawnder.model.Data1
import com.thetemz.pawnder.model.petListResModel

class PetListAdapter: RecyclerView.Adapter<PetListAdapter.ViewHolder>() {


    private lateinit var binding: CustomPetlistLayoutBinding
    var mContext : Context? = null
    private var PetList = ArrayList<Data1>()
    private lateinit var click: PetClickInterface


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        binding = CustomPetlistLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding.root)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val PetList = PetList[position]
        binding.productName.text = "Name: "+PetList.petName
        binding.contactnumber.text = "Age: "+PetList.petAge
        binding.address.text = "Category: "+PetList.pet_category_name


        if(PetList.file!=null){

            Glide.with(mContext ?: return)
                .load("https://miraanrai.com/pet_application/storage/app/"+PetList.file)
                .error(R.drawable.nofound)
                .placeholder(R.drawable.nofound)
                .into(binding.location!!)

        }

        binding.removeTV.setOnClickListener {
            click.onDeletePetClick(PetList.id.toString())
        }
        binding.EditTV.setOnClickListener {
            click.onEditPetClick(PetList)
        }
    }


    override fun getItemCount(): Int {
        return PetList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

    }

    fun setData(data :  ArrayList<Data1>){
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
        fun onEditPetClick(petId : Data1)
        fun onDeletePetClick(petId : String)
    }
}
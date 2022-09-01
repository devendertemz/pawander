package com.thetemz.pawnder.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.mymftcustomer.preference.PreferenceHelper
import com.thetemz.pawnder.Adapter.HomePagePetListAdapter
import com.thetemz.pawnder.R
import com.thetemz.pawnder.databinding.FragmentHomeBinding
import com.thetemz.pawnder.databinding.FragmentPetFulldetailsBinding
import com.thetemz.pawnder.model.CategoryData
import com.thetemz.pawnder.model.Data1
import com.thetemz.pawnder.model.allpetlistdata
import com.thetemz.pawnder.viewModel.GetAllPetListHomepageViewModel

class PetFulldetailsFragment : Fragment() {


    lateinit var binding: FragmentPetFulldetailsBinding

    var authorizationToken = ""
    private var helper: PreferenceHelper? = null
    private var data: allpetlistdata? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPetFulldetailsBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helper = PreferenceHelper.getPreference(requireContext())
        authorizationToken =
            "Bearer " + helper?.getLoginModelObject(requireContext())?.data?.accessToken
        // authorizationToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMGZlZmI1Yjk4NDc5ZTM1ZTI4ZGZlOTQ5YWI2ZDliYTlhZTgwMGMxYzBlMjI0NzU5YTljOTUzMDk4YThhNGYwZjg1YzBiYjhlMTdkNjkzNzQiLCJpYXQiOjE2NjAzMDM3MDIsIm5iZiI6MTY2MDMwMzcwMiwiZXhwIjoxNjkxODM5NzAyLCJzdWIiOiIxMCIsInNjb3BlcyI6W119.qc-UaEiMNkKNcH_m2Uwo8pyk0UqQh7aGVhTQqWigRNzoUhJUS8mb3BIsyj9T8oN2e2YwrJo46pWuojDIi_o7gSMGx8GtDOR5B8hiSxEtDXO7lBwryrGmo9HEKXbtxZbmDia8NEcuXvLJG83pxIKGq34TFlGY0GAZJMquM8qoAwk3pK5B3AImYr_I8KAjphBadcdjPbeJF1Fzz3JCNXlzFcLABy7nTGdDb6CRn4DdGSeJUyxOgvZjjVHG_FmNDE54FXNune-ONZKYpu8uKuUb1UXToF-TtTpfg0S1hYX5bncEyAo5BxcD2i4PVX9tUg8RMkc275euRuml0vOf0CuEDE7AZqqQeLL_4B908E0v7OQEp7r_KnfewA5Fm633JssoIcE_2cPKaYlE-Ghd0de2vXJKxrFSAAvbMU0p1dvWethBSw-JGvQvUwY0YwBDTjpPyLh-_6E9KUFXa-nuOhBg1q-EO_IFtE0nXHXRO1A71AZO0HlPJ6h8n1DFZX4Bso1QleHleg5AXwD8HPeF_AVy4npq-aOko-ig0Yze2feIwrefxwHgE6Z1RuEER9n3DLzzQWQw0EPlT9S61995l2QBuMNiKb0Dk7HOMkP7tkFXe5gWAPbOw1lv2xCiIRopArWYe3cbR8XSlxO4kgtufzhe-qURQg36pAss4Cewjbn-B14"


        val b = arguments

        if (b != null) {


            data = b!!.getSerializable("id") as allpetlistdata?
            Toast.makeText(requireActivity(), data?.id.toString(), Toast.LENGTH_LONG).show()

            Glide.with(requireActivity() ?: return)
                .load("https://miraanrai.com/pet_application/storage/app/" + data!!.file)
                .error(R.drawable.nofound)
                .placeholder(R.drawable.nofound)
                .into(binding.foodIV!!)



            binding.petnameTV.text = "Pet Name: " + data!!.petName
            binding.agetv.text = "Pet Age: " + data!!.petAge
            binding.gendertv.text = "pet Gender: " + data!!.petGender
            binding.locationTv.text = "pet Location: " + data!!.address
            binding.cateoriestv.text = "pet Category: " + data!!.pet_category_name
            binding.NameTv.text = data!!.name
            binding.mumberTv.text = data!!.phone

            binding.mumberTv.setOnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:"+data!!.phone)
                startActivity(dialIntent)
            }


            binding.ivArrowBack.setOnClickListener{

                requireActivity().onBackPressed()


            }

        } else {


            Toast.makeText(requireActivity(), data?.id.toString(), Toast.LENGTH_LONG).show()
        }


    }

}
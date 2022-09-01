package com.thetemz.pawnder.fragment

import android.content.ContentValues
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mymftcustomer.preference.PreferenceHelper
import com.thetemz.pawnder.Adapter.HomePagePetListAdapter
import com.thetemz.pawnder.Adapter.PetListAdapter
import com.thetemz.pawnder.R
import com.thetemz.pawnder.databinding.FragmentHomeBinding
import com.thetemz.pawnder.databinding.FragmentPetListBinding
import com.thetemz.pawnder.model.CategoryData
import com.thetemz.pawnder.model.allpetlistdata
import com.thetemz.pawnder.viewModel.GetAllPetListHomepageViewModel
import com.thetemz.pawnder.viewModel.petListViewModel

class HomeFragment : Fragment() , HomePagePetListAdapter.PetClickInterface {



    lateinit var binding: FragmentHomeBinding
    private val petListViewModell: petListViewModel by viewModels()

    private val petListViewModel: GetAllPetListHomepageViewModel by viewModels()
    var authorizationToken = ""
    private var helper: PreferenceHelper? = null

    private var HomePagePetListAdapter: HomePagePetListAdapter? = null
    private var data: CategoryData? = null
    var categoryid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helper = PreferenceHelper.getPreference(requireContext())
        HomePagePetListAdapter= HomePagePetListAdapter();
        authorizationToken = "Bearer " + helper?.getLoginModelObject(requireContext())?.data?.accessToken
        // authorizationToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMGZlZmI1Yjk4NDc5ZTM1ZTI4ZGZlOTQ5YWI2ZDliYTlhZTgwMGMxYzBlMjI0NzU5YTljOTUzMDk4YThhNGYwZjg1YzBiYjhlMTdkNjkzNzQiLCJpYXQiOjE2NjAzMDM3MDIsIm5iZiI6MTY2MDMwMzcwMiwiZXhwIjoxNjkxODM5NzAyLCJzdWIiOiIxMCIsInNjb3BlcyI6W119.qc-UaEiMNkKNcH_m2Uwo8pyk0UqQh7aGVhTQqWigRNzoUhJUS8mb3BIsyj9T8oN2e2YwrJo46pWuojDIi_o7gSMGx8GtDOR5B8hiSxEtDXO7lBwryrGmo9HEKXbtxZbmDia8NEcuXvLJG83pxIKGq34TFlGY0GAZJMquM8qoAwk3pK5B3AImYr_I8KAjphBadcdjPbeJF1Fzz3JCNXlzFcLABy7nTGdDb6CRn4DdGSeJUyxOgvZjjVHG_FmNDE54FXNune-ONZKYpu8uKuUb1UXToF-TtTpfg0S1hYX5bncEyAo5BxcD2i4PVX9tUg8RMkc275euRuml0vOf0CuEDE7AZqqQeLL_4B908E0v7OQEp7r_KnfewA5Fm633JssoIcE_2cPKaYlE-Ghd0de2vXJKxrFSAAvbMU0p1dvWethBSw-JGvQvUwY0YwBDTjpPyLh-_6E9KUFXa-nuOhBg1q-EO_IFtE0nXHXRO1A71AZO0HlPJ6h8n1DFZX4Bso1QleHleg5AXwD8HPeF_AVy4npq-aOko-ig0Yze2feIwrefxwHgE6Z1RuEER9n3DLzzQWQw0EPlT9S61995l2QBuMNiKb0Dk7HOMkP7tkFXe5gWAPbOw1lv2xCiIRopArWYe3cbR8XSlxO4kgtufzhe-qURQg36pAss4Cewjbn-B14"
       HomePagePetListAdapter!!.setPetListItemClickListener(this)


        val b = arguments

        if (b != null) {


            data = b!!.getSerializable("id") as CategoryData?
            binding.serchFoodTv.setText(data?.categoryName)

            categoryid=data?.id.toString()

        } else {


            Toast.makeText(requireActivity(), data?.id.toString(), Toast.LENGTH_LONG).show()
        }

        petListViewModell.petList(authorizationToken);
        setpetListObserverr()

        binding.serchFoodTv.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }

    private fun setpetListObserverr()  {

        petListViewModell.getProgressObserver.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarPetlist.visibility = View.VISIBLE
                binding.petlisRv.visibility=View.GONE
            } else {
                binding.progressBarPetlist.visibility = View.GONE
                binding.petlisRv.visibility=View.VISIBLE
            }
        }

        petListViewModell.getpetList.observe(viewLifecycleOwner) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.e(ContentValues.TAG, "setpetListViewModelObserver: ${it.data}")
            //   Toast.makeText(activity, "${it.message}", Toast.LENGTH_LONG).show()


            if (it.status == true) {

                if (it.data.size > 0) {

                    petListViewModel.allPetListdata(authorizationToken,categoryid);
                    Log.e("setpetListObserver",authorizationToken);
                    setpetListObserver()
                    Toast.makeText(activity, data?.id.toString(), Toast.LENGTH_LONG).show()

                   } else {

                    findNavController().navigate(R.id.nav_graph_petlist)



                }


            } else {
                //   Toast.makeText(this, it, Toast.LENGTH_LONG).show()

            }


        }

        petListViewModell.getMessageObserver.observe(viewLifecycleOwner)
        {

            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()


        }

    }
    private fun setpetListObserver() {

        petListViewModel.getProgressObserver.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarPetlist.visibility = View.VISIBLE
                binding.petlisRv.visibility=View.GONE
            } else {
                binding.progressBarPetlist.visibility = View.GONE
                binding.petlisRv.visibility=View.VISIBLE
            }
        }

        petListViewModel.getallPetListdata.observe(viewLifecycleOwner) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.e(ContentValues.TAG, "setpetListViewModelObserver: ${it.allpetlistdata}")
              Toast.makeText(activity, "${it.message}", Toast.LENGTH_LONG).show()


            if (it.status == true) {

                if (it.allpetlistdata.size>0)
                {
                    HomePagePetListAdapter?.setData(it.allpetlistdata)
                    binding.petlisRv.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.petlisRv.adapter = HomePagePetListAdapter
                }else{

                }




            }else{
                //   Toast.makeText(this, it, Toast.LENGTH_LONG).show()

            }



        }

        petListViewModel.getMessageObserver.observe(viewLifecycleOwner)
        {

          //  Toast.makeText(activity, it, Toast.LENGTH_LONG).show()

            binding.tvCall.visibility = View.VISIBLE
            binding.petlisRv.visibility=View.GONE
        }

    }

    override fun onPetMoreClick(petId: allpetlistdata) {
        var data = Bundle()
        data.putSerializable("id", petId)
        findNavController().navigate(R.id.petFulldetailsFragment, data)






    }

}
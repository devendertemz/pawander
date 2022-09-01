package com.thetemz.pawnder.fragment

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
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
import com.example.mymftcustomer.preference.PreferenceHelper
import com.thetemz.pawnder.Adapter.PetListAdapter
import com.thetemz.pawnder.R
import com.thetemz.pawnder.databinding.FragmentPetListBinding
import com.thetemz.pawnder.model.Data1
import com.thetemz.pawnder.viewModel.petListViewModel

class PetListFragment : Fragment(), PetListAdapter.PetClickInterface {


    lateinit var binding: FragmentPetListBinding
    private val petListViewModel: petListViewModel by viewModels()
    var authorizationToken = ""
    private var helper: PreferenceHelper? = null

    private var petListAdapter: PetListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPetListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helper = PreferenceHelper.getPreference(requireContext())
        petListAdapter = PetListAdapter();
        authorizationToken =
            "Bearer " + helper?.getLoginModelObject(requireContext())?.data?.accessToken
        // authorizationToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMGZlZmI1Yjk4NDc5ZTM1ZTI4ZGZlOTQ5YWI2ZDliYTlhZTgwMGMxYzBlMjI0NzU5YTljOTUzMDk4YThhNGYwZjg1YzBiYjhlMTdkNjkzNzQiLCJpYXQiOjE2NjAzMDM3MDIsIm5iZiI6MTY2MDMwMzcwMiwiZXhwIjoxNjkxODM5NzAyLCJzdWIiOiIxMCIsInNjb3BlcyI6W119.qc-UaEiMNkKNcH_m2Uwo8pyk0UqQh7aGVhTQqWigRNzoUhJUS8mb3BIsyj9T8oN2e2YwrJo46pWuojDIi_o7gSMGx8GtDOR5B8hiSxEtDXO7lBwryrGmo9HEKXbtxZbmDia8NEcuXvLJG83pxIKGq34TFlGY0GAZJMquM8qoAwk3pK5B3AImYr_I8KAjphBadcdjPbeJF1Fzz3JCNXlzFcLABy7nTGdDb6CRn4DdGSeJUyxOgvZjjVHG_FmNDE54FXNune-ONZKYpu8uKuUb1UXToF-TtTpfg0S1hYX5bncEyAo5BxcD2i4PVX9tUg8RMkc275euRuml0vOf0CuEDE7AZqqQeLL_4B908E0v7OQEp7r_KnfewA5Fm633JssoIcE_2cPKaYlE-Ghd0de2vXJKxrFSAAvbMU0p1dvWethBSw-JGvQvUwY0YwBDTjpPyLh-_6E9KUFXa-nuOhBg1q-EO_IFtE0nXHXRO1A71AZO0HlPJ6h8n1DFZX4Bso1QleHleg5AXwD8HPeF_AVy4npq-aOko-ig0Yze2feIwrefxwHgE6Z1RuEER9n3DLzzQWQw0EPlT9S61995l2QBuMNiKb0Dk7HOMkP7tkFXe5gWAPbOw1lv2xCiIRopArWYe3cbR8XSlxO4kgtufzhe-qURQg36pAss4Cewjbn-B14"
        petListAdapter!!.setPetListItemClickListener(this)

        petListViewModel.petList(authorizationToken);
        Log.e("setpetListObserver", authorizationToken);

        binding.btnAddress.setOnClickListener {

            findNavController().navigate(R.id.addNewPetFragment)

        }

        setpetListObserver()

    }


    private fun setpetListObserver() {

        petListViewModel.getProgressObserver.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarPetlist.visibility = View.VISIBLE
                binding.addressRecyclerView.visibility = View.GONE
            } else {
                binding.progressBarPetlist.visibility = View.GONE
                binding.addressRecyclerView.visibility = View.VISIBLE
            }
        }

        petListViewModel.getpetList.observe(viewLifecycleOwner) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.e(ContentValues.TAG, "setpetListViewModelObserver: ${it.data}")
            //   Toast.makeText(activity, "${it.message}", Toast.LENGTH_LONG).show()


            if (it.status == true) {

                if (it.data.size > 0) {
                    petListAdapter?.setData(it.data)
                    binding.addressRecyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.addressRecyclerView.adapter = petListAdapter
                } else {

                }


            } else {
                //   Toast.makeText(this, it, Toast.LENGTH_LONG).show()

            }


        }

        petListViewModel.getMessageObserver.observe(viewLifecycleOwner)
        {

            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()


        }

    }

    override fun onEditPetClick(petId: Data1) {


        var data = Bundle()
        data.putSerializable("id", petId)
        findNavController().navigate(R.id.addNewPetFragment, data)

        // var directions = PetListFragmentDirections.actionNavGraphPetlistToAddNewPetFragment().setPetid(petId)
        //    findNavController().navigate(directions)


    }

    override fun onDeletePetClick(petId: String) {

        dialog(petId)
        Toast.makeText(activity, petId, Toast.LENGTH_LONG).show()
    }


    private fun dialog(petId: String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        //set title for alert dialog
        builder.setTitle("Delete Pet")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to delete this pet?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            //Toast.makeText(requireContext(), "clicked yes", Toast.LENGTH_LONG).show()
            petListViewModel.Deletepet(authorizationToken,petId);
            GetPetDeleteObserver()

        }
        //performing cancel action
        /*   builder.setNeutralButton("Cancel"){dialogInterface , which ->
               Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
           }*/
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->

            // Toast.makeText(requireContext(), "clicked No", Toast.LENGTH_LONG).show()


        }
        // Create the AlertDialog
        val alertDialog: androidx.appcompat.app.AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()


    }
    private fun GetPetDeleteObserver() {


        petListViewModel.getProgressObserver.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarPetlist.visibility = View.VISIBLE
                binding.addressRecyclerView.visibility = View.GONE
            } else {
                binding.progressBarPetlist.visibility = View.GONE
                binding.addressRecyclerView.visibility = View.VISIBLE
            }
        }

        petListViewModel.getpetDelte.observe(viewLifecycleOwner) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.e(ContentValues.TAG, "setpetListViewModelObserver: ${it.data}")
            Toast.makeText(activity, "${it.message}", Toast.LENGTH_LONG).show()
            petListViewModel.petList(authorizationToken);

        }
        petListViewModel.getMessageObserver.observe(viewLifecycleOwner)
        {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()

        }

    }

}
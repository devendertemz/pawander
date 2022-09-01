package com.thetemz.pawnder.fragment

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymftcustomer.preference.PreferenceHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thetemz.pawnder.Activity.OnboardingPageActivity
import com.thetemz.pawnder.Adapter.HomePagePetListAdapter
import com.thetemz.pawnder.R
import com.thetemz.pawnder.databinding.BottomSheetDialogLoginBinding
import com.thetemz.pawnder.databinding.ChangepasswordbottomBinding
import com.thetemz.pawnder.databinding.FragmentHomeBinding
import com.thetemz.pawnder.databinding.FragmentProfileBinding
import com.thetemz.pawnder.viewModel.GetAllPetListHomepageViewModel
import com.thetemz.pawnder.viewModel.ProfileDetailsViewModel

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private lateinit var binding_updatepassword: ChangepasswordbottomBinding
    var dialogupdatepassword: BottomSheetDialog? = null

    private val profileDetailsViewModel: ProfileDetailsViewModel by viewModels()
    var authorizationToken = ""
    private var helper: PreferenceHelper? = null

    lateinit var Oldpassword: String
    lateinit var password: String
    lateinit var Confirmpassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_profile, container, false)

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding_updatepassword = ChangepasswordbottomBinding.inflate(layoutInflater)

        helper = PreferenceHelper.getPreference(requireContext())

        authorizationToken =
            "Bearer " + helper?.getLoginModelObject(requireContext())?.data?.accessToken
        profileDetailsViewModel.getProfile(authorizationToken);
        GetProfiletObserver();

        // authorizationToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMGZlZmI1Yjk4NDc5ZTM1ZTI4ZGZlOTQ5YWI2ZDliYTlhZTgwMGMxYzBlMjI0NzU5YTljOTUzMDk4YThhNGYwZjg1YzBiYjhlMTdkNjkzNzQiLCJpYXQiOjE2NjAzMDM3MDIsIm5iZiI6MTY2MDMwMzcwMiwiZXhwIjoxNjkxODM5NzAyLCJzdWIiOiIxMCIsInNjb3BlcyI6W119.qc-UaEiMNkKNcH_m2Uwo8pyk0UqQh7aGVhTQqWigRNzoUhJUS8mb3BIsyj9T8oN2e2YwrJo46pWuojDIi_o7gSMGx8GtDOR5B8hiSxEtDXO7lBwryrGmo9HEKXbtxZbmDia8NEcuXvLJG83pxIKGq34TFlGY0GAZJMquM8qoAwk3pK5B3AImYr_I8KAjphBadcdjPbeJF1Fzz3JCNXlzFcLABy7nTGdDb6CRn4DdGSeJUyxOgvZjjVHG_FmNDE54FXNune-ONZKYpu8uKuUb1UXToF-TtTpfg0S1hYX5bncEyAo5BxcD2i4PVX9tUg8RMkc275euRuml0vOf0CuEDE7AZqqQeLL_4B908E0v7OQEp7r_KnfewA5Fm633JssoIcE_2cPKaYlE-Ghd0de2vXJKxrFSAAvbMU0p1dvWethBSw-JGvQvUwY0YwBDTjpPyLh-_6E9KUFXa-nuOhBg1q-EO_IFtE0nXHXRO1A71AZO0HlPJ6h8n1DFZX4Bso1QleHleg5AXwD8HPeF_AVy4npq-aOko-ig0Yze2feIwrefxwHgE6Z1RuEER9n3DLzzQWQw0EPlT9S61995l2QBuMNiKb0Dk7HOMkP7tkFXe5gWAPbOw1lv2xCiIRopArWYe3cbR8XSlxO4kgtufzhe-qURQg36pAss4Cewjbn-B14"


        binding.petlistLL.setOnClickListener {
            findNavController().navigate(R.id.nav_graph_petlist)

        }
        binding.logoutLL.setOnClickListener {
            Logoutdialog()
        }
        binding.passwordLL.setOnClickListener {
            setUpBottomDialogUpdatpassword(requireContext())

        }
        binding.editprofileTv.setOnClickListener {


            profileDetailsViewModel.UpdateProfile(authorizationToken,binding.name.text.toString(),binding.address.text.toString());
            GetProfiletObserver()


        }
    }


    private fun GetProfiletObserver() {

        profileDetailsViewModel.getProgressObserver.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarPetlist.visibility = View.VISIBLE
            } else {
                binding.progressBarPetlist.visibility = View.GONE
            }
        }

        profileDetailsViewModel.getProfile.observe(viewLifecycleOwner) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.e(ContentValues.TAG, "setpetListViewModelObserver: ${it.data}")
            Toast.makeText(activity, "${it.message}", Toast.LENGTH_LONG).show()


            if (it.status == true) {

                with(binding) { name.setText(it.data?.name) }
                with(binding) { address.setText(it.data?.address) }
                binding.email.text = it.data?.phone

            } else {
                //   Toast.makeText(this, it, Toast.LENGTH_LONG).show()

            }


        }

        profileDetailsViewModel.getMessageObserver.observe(viewLifecycleOwner)
        {

            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()


        }

    }


    private fun GetPasswordUpdateObserver() {

        profileDetailsViewModel.getProgressObserver.observe(requireActivity()) {
            if (it) {
                binding_updatepassword.progressBar.visibility = View.VISIBLE
            } else {
                binding_updatepassword.progressBar.visibility = View.GONE
            }
        }

        profileDetailsViewModel.getUpdateapssword.observe(viewLifecycleOwner) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.e(ContentValues.TAG, "setpetListViewModelObserver: ${it.data}")
            Toast.makeText(activity, "${it.message}", Toast.LENGTH_LONG).show()
            dialogupdatepassword?.dismiss()


        }

        profileDetailsViewModel.getMessageObserver.observe(viewLifecycleOwner)
        {

            Toast.makeText(activity, "Old Password Not Matched !!!", Toast.LENGTH_LONG).show()


        }

    }


    private fun setUpBottomDialogUpdatpassword(mContext: Context) {


        if (binding_updatepassword.root != null) {
            //binding_updatepassword.root.maxHeight
            dialogupdatepassword =
                BottomSheetDialog(requireContext(), R.style.BottomSheetStyleTheme)
            binding_updatepassword = ChangepasswordbottomBinding.inflate(layoutInflater)

            dialogupdatepassword?.setContentView(binding_updatepassword.root)
            dialogupdatepassword?.show()

            binding_updatepassword.cancelTv.setOnClickListener {
                dialogupdatepassword?.dismiss()

            }
            binding_updatepassword.saveTv.setOnClickListener {

                Oldpassword = binding_updatepassword.oldpasswordET.text.toString();
                password = binding_updatepassword.passwordET.text.toString();
                Confirmpassword = binding_updatepassword.ConfirmPasswordTV.text.toString();


                if (Oldpassword.length < 6) {
                    Toast.makeText(requireContext(), "Please enter Old Password", Toast.LENGTH_LONG)
                        .show()

                } else if (password.length < 6) {
                    Toast.makeText(
                        requireContext(),
                        "Password should be minimum 6 characters",
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else if (!Confirmpassword.equals(password)) {
                    Toast.makeText(
                        requireContext(),
                        "Confirm Password should Same as Password ",
                        Toast.LENGTH_LONG
                    )
                        .show()

                } else {
                    Log.e("setpetListObserver", authorizationToken);

                    profileDetailsViewModel.UpdatePassword(
                        authorizationToken,
                        Oldpassword,
                        password,
                        Confirmpassword
                    )
                    GetPasswordUpdateObserver()
                }


            }


        }


    }

    private fun Logoutdialog() {
        val builder = AlertDialog.Builder(requireContext())
        //set title for alert dialog
        builder.setTitle("Logout")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to Logout?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            //Toast.makeText(requireContext(), "clicked yes", Toast.LENGTH_LONG).show()
            profileDetailsViewModel.userLogout(authorizationToken);
            GetLogoutObserver()
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
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()


    }

    private fun GetLogoutObserver() {

        profileDetailsViewModel.getProgressObserver.observe(requireActivity()) {
            if (it) {
                binding_updatepassword.progressBar.visibility = View.VISIBLE
            } else {
                binding_updatepassword.progressBar.visibility = View.GONE
            }
        }

        profileDetailsViewModel.getUpdateapssword.observe(viewLifecycleOwner) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.e(ContentValues.TAG, "setpetListViewModelObserver: ${it.data}")
            Toast.makeText(activity, "${it.message}", Toast.LENGTH_LONG).show()
            if (helper?.getAlreadyLogedinStatus(requireContext()) == true) {
                helper?.clearLoginData(requireContext())
                helper?.setAlreadyLogedinStatus(requireContext(), false)
                val intent = Intent(requireContext(), OnboardingPageActivity::class.java)
                startActivity(intent)
               activity?.finish()
            }



        }

        profileDetailsViewModel.getMessageObserver.observe(viewLifecycleOwner)
        {

            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()


        }

    }

}
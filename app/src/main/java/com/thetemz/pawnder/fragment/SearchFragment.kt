package com.thetemz.pawnder.fragment

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymftcustomer.preference.PreferenceHelper
import com.thetemz.pawnder.Adapter.searchAdapter
import com.thetemz.pawnder.R
import com.thetemz.pawnder.databinding.FragmentSearchBinding
import com.thetemz.pawnder.model.CategoryData
import com.thetemz.pawnder.viewModel.AddNewPetDetailsViewModel
import java.util.*


class SearchFragment : Fragment(), searchAdapter.searchClickInterface {

    lateinit var binding: FragmentSearchBinding

    var authorizationToken = ""
    private var helper: PreferenceHelper? = null

    private val addNewPetDetailsViewModel: AddNewPetDetailsViewModel by viewModels()
    lateinit var adapter:searchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_search, container, false)

        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helper = PreferenceHelper.getPreference(requireContext())

        authorizationToken = "Bearer " + helper?.getLoginModelObject(requireContext())?.data?.accessToken
        // authorizationToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMGZlZmI1Yjk4NDc5ZTM1ZTI4ZGZlOTQ5YWI2ZDliYTlhZTgwMGMxYzBlMjI0NzU5YTljOTUzMDk4YThhNGYwZjg1YzBiYjhlMTdkNjkzNzQiLCJpYXQiOjE2NjAzMDM3MDIsIm5iZiI6MTY2MDMwMzcwMiwiZXhwIjoxNjkxODM5NzAyLCJzdWIiOiIxMCIsInNjb3BlcyI6W119.qc-UaEiMNkKNcH_m2Uwo8pyk0UqQh7aGVhTQqWigRNzoUhJUS8mb3BIsyj9T8oN2e2YwrJo46pWuojDIi_o7gSMGx8GtDOR5B8hiSxEtDXO7lBwryrGmo9HEKXbtxZbmDia8NEcuXvLJG83pxIKGq34TFlGY0GAZJMquM8qoAwk3pK5B3AImYr_I8KAjphBadcdjPbeJF1Fzz3JCNXlzFcLABy7nTGdDb6CRn4DdGSeJUyxOgvZjjVHG_FmNDE54FXNune-ONZKYpu8uKuUb1UXToF-TtTpfg0S1hYX5bncEyAo5BxcD2i4PVX9tUg8RMkc275euRuml0vOf0CuEDE7AZqqQeLL_4B908E0v7OQEp7r_KnfewA5Fm633JssoIcE_2cPKaYlE-Ghd0de2vXJKxrFSAAvbMU0p1dvWethBSw-JGvQvUwY0YwBDTjpPyLh-_6E9KUFXa-nuOhBg1q-EO_IFtE0nXHXRO1A71AZO0HlPJ6h8n1DFZX4Bso1QleHleg5AXwD8HPeF_AVy4npq-aOko-ig0Yze2feIwrefxwHgE6Z1RuEER9n3DLzzQWQw0EPlT9S61995l2QBuMNiKb0Dk7HOMkP7tkFXe5gWAPbOw1lv2xCiIRopArWYe3cbR8XSlxO4kgtufzhe-qURQg36pAss4Cewjbn-B14"
        addNewPetDetailsViewModel.CategorylistData(authorizationToken)
        setCategoriesListObserver();

        binding.edSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do Nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().toLowerCase(Locale.getDefault())
                adapter.filter.filter(query)

                Toast.makeText(requireActivity(), query, Toast.LENGTH_LONG).show()
            }

            override fun afterTextChanged(s: Editable?) {
                // Do Nothing
            }
        })
    /*    binding.edSearch.setOnEditorActionListener { textView, action, keyEvent ->

            if(action == EditorInfo.IME_ACTION_SEARCH) {
                //searchViewModel.getSearchItem(binding.etSearch.text.toString().trim()

                Toast.makeText(requireActivity(), binding.edSearch.text.toString().trim(), Toast.LENGTH_LONG).show()

                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)

            }
            true
        }*/


        binding.ivArrowBack.setOnClickListener {

            requireActivity().onBackPressed()

        }
    }

    private fun setCategoriesListObserver() {

        addNewPetDetailsViewModel.getProgressObserver.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarPetlist.visibility = View.VISIBLE
            } else {
                binding.progressBarPetlist.visibility = View.GONE
            }
        }

        addNewPetDetailsViewModel.getliveDataCategoriesList.observe(viewLifecycleOwner) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.d(ContentValues.TAG, "getliveDataCategoriesList: ${it.data}")

            if (it.data.size > 0) {
                val aaraylistCategories = ArrayList<CategoryData>()

                for (x in 1..it.data.size-1) {

                    aaraylistCategories.add(
                        CategoryData(
                            it.data.get(x).id,
                            it.data.get(x).categoryName,
                            it.data.get(x).createdAt,
                            it.data.get(x).updatedAt

                        )
                    )
                }
                binding.recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = searchAdapter(aaraylistCategories,this)
               binding.recyclerView.adapter = adapter

                //     binding.recyclerView.adapter = searchAdapter


/*

                val customDropDownAdapter = CateoriesLsitSpinnerAdapter(requireActivity(), it)

                binding.CateoriesTypeSP.adapter = customDropDownAdapter
                binding.CateoriesTypeSP.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {


                            categoryET = it.data.get(p2).id.toString()


                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }

                    }

                if (data!!.petCategory!=null) {
                    for (x in 0..it.data.size-1) {



                        if (it.data.get(x).id.toString().equals(data!!.petCategory,true))
                        {
                            Log.d("checlll",x.toString())
                            Log.d("petCategory",data!!.petCategory.toString())
                            Log.d("petCategory",data!!.petCategory.toString())
                            binding.CateoriesTypeSP.setSelection(x)


                        }
                    }


                    */
/*aaraylistCategories.clear()
                    for (x in 1..it.data.size-1) {

                        aaraylistCategories.add(
                            CategoryData(
                                it.data.get(x).id,
                                it.data.get(x).categoryName,
                                it.data.get(x).createdAt,
                                it.data.get(x).updatedAt

                            ))*//*

                }
*/


            }


        }

        addNewPetDetailsViewModel.getMessageObserver.observe(viewLifecycleOwner)
        {

            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()


        }
    }

    override fun onSelectedClick(petId: CategoryData) {
      // Toast.makeText(requireActivity(), petId.categoryName, Toast.LENGTH_LONG).show()
        var data = Bundle()
        data.putSerializable("id", petId)
        findNavController().navigate(R.id.nav_graph_home, data)
    }

}
package com.thetemz.pawnder.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.icu.number.IntegerWidth
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mymftcustomer.preference.PreferenceHelper
import com.thetemz.pawnder.Adapter.AccountTypeSpinnerAdapter
import com.thetemz.pawnder.Adapter.CateoriesLsitSpinnerAdapter
import com.thetemz.pawnder.R
import com.thetemz.pawnder.databinding.FragmentAddNewPetBinding
import com.thetemz.pawnder.model.CategoryData
import com.thetemz.pawnder.model.CategorylistData
import com.thetemz.pawnder.model.Data1
import com.thetemz.pawnder.model.SliderDataItem
import com.thetemz.pawnder.utils.AppUtils
import com.thetemz.pawnder.viewModel.AddNewPetDetailsViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*

class AddNewPetFragment : Fragment() {

    lateinit var binding: FragmentAddNewPetBinding

    var authorizationToken = ""
    private var helper: PreferenceHelper? = null

    //val args: AddNewPetFragmentArgs by navArgs()
    private val PICK_PHOTO_FOR_AVATAR = 1
    private var uri: Uri? = null

    var permissionStatus = false
    var file: File? = null
    private var radioButtonSelection = ""
    private var updateOrAdd = ""
    private var categoryET = ""
    private var data: Data1? = null
    private val addNewPetDetailsViewModel: AddNewPetDetailsViewModel by viewModels()
    var aaraylistCategories: ArrayList<CategoryData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewPetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppUtils.checkAndRequestPermissions(requireActivity())
        helper = PreferenceHelper.getPreference(requireContext())
        authorizationToken =
            "Bearer " + helper?.getLoginModelObject(requireContext())?.data?.accessToken

        Log.e("setpetListObserver", authorizationToken);
        addNewPetDetailsViewModel.CategorylistData(authorizationToken)
        setCategoriesListObserver();
//
        val b = arguments

        if (b != null) {

            updateOrAdd = "update"

            data = b!!.getSerializable("id") as Data1?
            Toast.makeText(requireActivity(), data?.id.toString(), Toast.LENGTH_LONG).show()
            binding.AddNewPetTV.text = "Update Pet"
            binding.petnameET.setText(data!!.petName)
            binding.dobET.setText(data!!.petAge)

            Glide.with(requireActivity() ?: return)
                .load("https://miraanrai.com/pet_application/storage/app/" + data!!.file)
                .error(R.drawable.nofound)
                .placeholder(R.drawable.nofound)
                .into(binding.ivProfilePhoto!!)

         /*   for (x in 1..aaraylistCategories.size) {
                if (data!!.petCategory?.equals(aaraylistCategories.get(x).id) == true)
                {
                    binding.CateoriesTypeSP.setSelection(x);

                }
            }*/


                //  binding.categoryET.setText(data!!.petCategory)


            if (data!!.petGender.equals("Male", ignoreCase = true)) {
                radioButtonSelection = "Male"

                binding.radioGroup.check(R.id.radioButtonMale)


            } else if (data!!.petGender.equals("Female", ignoreCase = true)) {

                radioButtonSelection = "Female"




                binding.radioGroup.check(R.id.radioButtonfemale)

                //binding.radioButtonfemale.setChecked(true)

            }


        } else {


            updateOrAdd = "add"
            binding.AddNewPetTV.text = "Add Pet"
            Toast.makeText(requireActivity(), data?.id.toString(), Toast.LENGTH_LONG).show()
        }




        binding.OpenGalleryLL.setOnClickListener {
            galleryPicker()
        }
        binding.ivArrowBack.setOnClickListener {

            requireActivity().onBackPressed()
        }
        binding.AddNewPetTV.setOnClickListener {

            AddNewPet();
        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, iD ->
            when (iD) {
                R.id.radioButtonMale -> {
                    radioButtonSelection = "Male"
                }
                R.id.radioButtonfemale -> {
                    radioButtonSelection = "Female"
                }
            }

        }

        // args.petid


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            AppUtils.PERMISSION_REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                    permissionStatus = true
                } else {
                    permissionStatus = false
                    val msg = "Please Allow Permission to share."
                    customAlert(msg)
                }
                return
            }
        }
    }

    private fun customAlert(msg: String) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setMessage(msg)
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() }).show()
    }


    private fun galleryPicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === PICK_PHOTO_FOR_AVATAR && resultCode === Activity.RESULT_OK) {
            if (data == null)
                return
            uri = data.getData()
            println("urii  " + uri + " " + uri!!.getPath())
            val path = getPath(uri)
            println("urii path $path")
            Glide.with(requireActivity()).load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivProfilePhoto)
            if (path != null && path != "") {
                file = File(path)
                //uploadImage(file!!)
            }
        }
    }

    private fun uploadImage(
        petname: String,
        pet_age: String,
        pet_gender: String,
        pet_category: String,
        file: File
    ) {

        Log.e(
            "sfafd",
            petname + pet_age + pet_gender + pet_category + file + "\n" + authorizationToken
        )

        /* helper?.saveCartProductId(PrefKeys.KEY_CART_PRODUCT_ID, it.productId)*/

        /*   val requestFile: RequestBody =
               RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
           val profileImage: MultipartBody.Part =
               MultipartBody.Part.createFormData("file", file.name, requestFile)*/

        val requestFile =
            file.asRequestBody(requireContext().contentResolver.getType(uri!!)?.toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)


        val petname: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            petname.toString()
        )
        val petid: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            data?.id.toString()
        )

        val pet_age: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            pet_age.toString()
        )
        val pet_gender: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            pet_gender.toString()
        )
        val pet_category: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            pet_category.toString()
        )

        if (updateOrAdd.equals("update", ignoreCase = true)) {
            addNewPetDetailsViewModel.UpdatePetDetails(
                authorizationToken,
                petid,
                petname,
                pet_age,
                pet_gender,
                pet_category,
                body
            )
        } else {

            addNewPetDetailsViewModel.getUploadImage(
                authorizationToken,
                petname,
                pet_age,
                pet_gender,
                pet_category,
                body
            )
        }
        setUpObserver()


        /*  productDetailViewModel.getUploadImage(productId, body)

          setUpObserver()*/
    }

    private fun setUpObserver() {

        addNewPetDetailsViewModel.getProgressObserver.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarPetlist.visibility = View.VISIBLE
                binding.dataLL.visibility = View.GONE
            } else {
                binding.progressBarPetlist.visibility = View.GONE
                binding.dataLL.visibility = View.VISIBLE
            }
        }

        addNewPetDetailsViewModel.getUploadAddPetDetails.observe(viewLifecycleOwner) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.d(ContentValues.TAG, "setLoginWithPasswordObserver: ${it.data}")

            requireActivity().onBackPressed()

            Toast.makeText(activity, "${it.message}", Toast.LENGTH_LONG).show()
//            nav_graph_petlist


        }

        addNewPetDetailsViewModel.getMessageObserver.observe(viewLifecycleOwner)
        {

            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()


        }
    }

    private fun setCategoriesListObserver() {

        addNewPetDetailsViewModel.getProgressObserver.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarPetlist.visibility = View.VISIBLE
                binding.dataLL.visibility = View.GONE
            } else {
                binding.progressBarPetlist.visibility = View.GONE
                binding.dataLL.visibility = View.VISIBLE
            }
        }

        addNewPetDetailsViewModel.getliveDataCategoriesList.observe(viewLifecycleOwner) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.d(ContentValues.TAG, "getliveDataCategoriesList: ${it.data}")

            if (it.data.size > 0) {

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

                if (data?.petCategory!=null) {
                    for (x in 0..it.data.size-1) {



                        if (it.data.get(x).id.toString().equals(data!!.petCategory,true))
                        {
                            Log.d("checlll",x.toString())
                            Log.d("petCategory",data!!.petCategory.toString())
                            Log.d("petCategory",data!!.petCategory.toString())
                            binding.CateoriesTypeSP.setSelection(x)


                        }
                    }


                    /*aaraylistCategories.clear()
                    for (x in 1..it.data.size-1) {

                        aaraylistCategories.add(
                            CategoryData(
                                it.data.get(x).id,
                                it.data.get(x).categoryName,
                                it.data.get(x).createdAt,
                                it.data.get(x).updatedAt

                            ))*/
                }


            }


        }

        addNewPetDetailsViewModel.getMessageObserver.observe(viewLifecycleOwner)
        {

            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()


        }
    }

    //method to get the file path from uri
    @SuppressLint("Range")
    fun getPath(uri: Uri?): String? {
        var cursor: Cursor =
            requireActivity().getContentResolver().query(uri!!, null, null, null, null)!!
        cursor.moveToFirst()
        var document_id: String = cursor.getString(0)
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
        cursor.close()
        cursor = requireActivity().getContentResolver().query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null
        )!!
        cursor.moveToFirst()
        val path: String = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        cursor.close()
        return path
    }


    private fun AddNewPet() {

        val petname: String = binding.petnameET.text.toString()
        val petage: String = binding.dobET.text.toString()

        if (petname.isEmpty()) {
            Toast.makeText(activity, "Please enter your pet name", Toast.LENGTH_LONG)
                .show()
        } else if (petage.isEmpty()) {
            Toast.makeText(activity, "Please enter your pet age", Toast.LENGTH_LONG)
                .show()
        } else if (radioButtonSelection.isEmpty()) {
            Toast.makeText(
                activity,
                "Please selecting  pet gender type",
                Toast.LENGTH_LONG
            ).show()
        } else if (categoryET.isEmpty()) {
            Toast.makeText(
                activity, "Please Select your Pet category/breed", Toast.LENGTH_LONG
            ).show()
        } else if (file == null) {
            Toast.makeText(
                activity, "Please Select Pet Image", Toast.LENGTH_LONG
            ).show()
        } else {
            uploadImage(petname, petage, radioButtonSelection, categoryET, file!!)

        }

    }
}


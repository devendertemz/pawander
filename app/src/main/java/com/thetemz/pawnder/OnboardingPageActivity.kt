package com.thetemz.pawnder.Activity

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thetemz.pawnder.Adapter.OnboardingViewPagerAdapter
import com.thetemz.pawnder.Modal.SliderDataItem
import com.thetemz.pawnder.R
import com.thetemz.pawnder.databinding.ActivityOnboardingPageBinding
import com.thetemz.pawnder.databinding.BottomSheetDialogLoginBinding
import com.thetemz.pawnder.databinding.BottomSheetDialogRegisterBinding
import com.thetemz.pawnder.viewpagerslider.SpeedSlowScroller
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Field
import java.sql.Array
import java.util.*
import java.util.regex.Pattern


class OnboardingPageActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding_login: BottomSheetDialogLoginBinding
    private lateinit var binding_register: BottomSheetDialogRegisterBinding

    private lateinit var bindingActivity: ActivityOnboardingPageBinding
    private var timer: Timer = Timer()
    lateinit var adapter: OnboardingViewPagerAdapter
    var currentIndex: Int = 0
    var android_id: String? = null
    var txtOtpReceive: String? = null
    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    lateinit var listViews: ArrayList<SliderDataItem>
    var dialoglogin: BottomSheetDialog? = null
    var dialogregister: BottomSheetDialog? = null

    val RC_SIGN_IN: Int = 1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_login = BottomSheetDialogLoginBinding.inflate(layoutInflater)
        binding_register = BottomSheetDialogRegisterBinding.inflate(layoutInflater)

        bindingActivity = ActivityOnboardingPageBinding.inflate(layoutInflater)
        setContentView(bindingActivity.root)

        android_id = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )


        var deviceToken = android_id
        Log.d(TAG, "onCreateDeviceToken:$deviceToken")
        initUI()
        bindingActivity.cardViewGetStarted.setOnClickListener {

            setUpBottomDialogLogin(this)
        }
        //setUpBottomDialogLogin(this)
        //  setUpBottomDialogRegister(this)
    }

    override fun onStart() {

        super.onStart()

    }


    private fun initUI() {

        listViews = ArrayList<SliderDataItem>()

        listViews.add(
            SliderDataItem(
                1,
                R.drawable.onboardingcake,
                "Customize your bouquet on the\ngo with great ease"
            )
        )
        listViews.add(
            SliderDataItem(
                2,
                R.drawable.onboardingcake,
                "Customize your bouquet on the\ngo with great ease"
            )
        )
        listViews.add(
            SliderDataItem(
                3,
                R.drawable.onboardingcake,
                "Cake for Ever Occasion\nExpress Delivery"
            )
        )

        adapter = OnboardingViewPagerAdapter(this, listViews)
        bindingActivity.viewPager.adapter = adapter

        try {

            val mScroller: Field = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.setAccessible(true)
            val scroller = SpeedSlowScroller(this)
            mScroller.set(bindingActivity.viewPager, scroller)
            var viewpos: Int
            viewpos = bindingActivity.viewPager.currentItem
            val handler = Handler()
            val Update = Runnable {
                if (viewpos == listViews.size) {
                    viewpos = 0
                }
                bindingActivity.viewPager.setCurrentItem(
                    viewpos++,
                    true
                )
            }
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    handler.post(Update)
                }
            }, 2000, 4000)

        } catch (ignored: Exception) {

        }

        addPageIndicators()

        bindingActivity.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                updatePageIndicator(position)
            }

        })

    }

    private fun updatePageIndicator(position: Int) {

        var imageView: ImageView
        var lp = LinearLayout.LayoutParams(25, 25)
        lp.setMargins(5, 0, 5, 0)
        for (i in 0 until bindingActivity.lytPageIndicator.getChildCount()) {
            imageView = bindingActivity.lytPageIndicator.getChildAt(i) as ImageView
            imageView.setLayoutParams(lp)
            if (position == i) {
                lp = LinearLayout.LayoutParams(48, 8)
                lp.setMargins(5, 0, 5, 0)
                imageView.setLayoutParams(lp)
                imageView.setImageResource(R.drawable.active)
            } else {
                lp = LinearLayout.LayoutParams(8, 8)
                lp.setMargins(5, 0, 5, 0)
                imageView.setLayoutParams(lp)
                imageView.setImageResource(R.drawable.inactive)
            }
        }

    }

    private fun addPageIndicators() {

        bindingActivity.lytPageIndicator.removeAllViews()

        for (i in listViews.indices) {
            var view = ImageView(this)
            view.setImageResource(R.drawable.inactive)
            bindingActivity.lytPageIndicator.addView(view)
        }

        updatePageIndicator(currentIndex)

    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    private fun setUpBottomDialogLogin(mContext: Context) {


        if (binding_login.root != null) {

            binding_login.root.maxHeight
            dialoglogin = BottomSheetDialog(this, R.style.BottomSheetStyleTheme)
            binding_login = BottomSheetDialogLoginBinding.inflate(layoutInflater)

            dialoglogin?.setContentView(binding_login.root)
            dialoglogin?.show()
            dialogregister?.dismiss()

            binding_login.tvSkip.setOnClickListener {
                /* val intent = Intent(this, MainActivity::class.java)
                 startActivity(intent)
                 finish()*/
            }

            binding_login.cardViewLogin.setOnClickListener {
                if (binding_login.etMailId.text.toString().isEmpty()) {
                    Toast.makeText(this, "Please enter email id", Toast.LENGTH_LONG).show()
                } else if (!binding_login.etMailId.text.toString()
                        .matches(emailPattern.toRegex())
                ) {
                    Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val emailId = binding_login.etMailId.text.toString()
                    /*   preferenceHelper?.saveEmailId(PrefKeys.KEY_EMAIL_ID, emailId)
                       checkCustomerId.checkCustomerMailId(binding_login.etMailId.text.toString())
                       checkMailIdObserver(emailId)*/
                }
            }

            binding_login.tvregister.setOnClickListener {
                Toast.makeText(this, "Please", Toast.LENGTH_LONG).show()

                setUpBottomDialogRegister(mContext)
            }


        }


    }

    private fun setUpBottomDialogRegister(mContext: Context) {

        if (binding_register.root != null) {
            var Accounttype: String? = null
            var Petadoptiontype: String? = null

            val AccountType =
                arrayOf("Select Account Type", "Breeding", "Pet adoption", "Dog sitter finder")

            binding_register.root.maxHeight
            dialogregister = BottomSheetDialog(this, R.style.BottomSheetStyleTheme)
            binding_register = BottomSheetDialogRegisterBinding.inflate(layoutInflater)
            dialogregister?.setContentView(binding_register.root)
            dialogregister?.show()
            dialoglogin?.dismiss()

            val arrayAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, AccountType)
            binding_register.AccountTypeSP.adapter = arrayAdapter
            binding_register.AccountTypeSP.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (AccountType[p2].equals("Pet adoption")) {
                            binding_register.petadoptionLL.visibility = View.VISIBLE

                        } else {
                            binding_register.petadoptionLL.visibility = View.GONE

                        }

                        Accounttype = AccountType[p2]


                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                }
            binding_register.radioGroup.setOnCheckedChangeListener { radioGroup, iD ->

                when (iD) {
                    R.id.radioButtonindividual -> {
                        Petadoptiontype = "individual"

                        showToast("individual")

                    }
                    R.id.radioButtonorganization -> {
                        Petadoptiontype = "organization"
                        showToast("organization")

                    }
                }

            }

            binding_register.cardViewSignUp.setOnClickListener {
                if (binding_register.etMail.text.toString().isEmpty()) {
                    Toast.makeText(this, "Please enter email id", Toast.LENGTH_LONG).show()
                } else if (!binding_register.etMail.text.toString()
                        .matches(emailPattern.toRegex())
                ) {
                    Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_LONG)
                        .show()
                } else if (binding_register.etName.text.toString().isEmpty()) {
                    Toast.makeText(this, "Please enter Name ", Toast.LENGTH_LONG)
                        .show()
                } else if (binding_register.etMobile.text.toString().length < 10) {
                    Toast.makeText(this, "Please enter valid number  ", Toast.LENGTH_LONG)
                        .show()
                } else if (binding_register.etPassword.text.toString().length < 6) {
                    Toast.makeText(
                        this,
                        "Password should be minimum 6 characters",
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else if (!binding_register.etPassword.text.toString()
                        .equals(binding_register.etConfirmpassword.text.toString())
                ) {
                    Toast.makeText(
                        this,
                        "Confirm Password should Same as Password ",
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else if (Accounttype.toString().equals("Select Account Type")) {

                    Toast.makeText(this, "Please Select Account Type", Toast.LENGTH_LONG)
                        .show()
                } else if (Accounttype.toString()
                        .equals("Pet adoption") && Petadoptiontype.toString().equals(null)
                ) {


                    Toast.makeText(this, "Please Pet adoption Type", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val emailId = binding_login.etMailId.text.toString()
                    /*   preferenceHelper?.saveEmailId(PrefKeys.KEY_EMAIL_ID, emailId)
                       checkCustomerId.checkCustomerMailId(binding_login.etMailId.text.toString())
                       checkMailIdObserver(emailId)*/
                }

            }



            binding_register.screenLogin.setOnClickListener {
                setUpBottomDialogLogin(mContext)

            }


        }

    }
}











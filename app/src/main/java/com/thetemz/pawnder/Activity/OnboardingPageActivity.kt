package com.thetemz.pawnder.Activity

import UserSendOTPViewModel
import UserVerifiyOTPViewModel
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.mymftcustomer.preference.PreferenceHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thetemz.pawnder.Adapter.AccountSubTypeSpinnerAdapter
import com.thetemz.pawnder.Adapter.AccountTypeSpinnerAdapter
import com.thetemz.pawnder.Adapter.OnboardingViewPagerAdapter
import com.thetemz.pawnder.MainActivity
import com.thetemz.pawnder.R
import com.thetemz.pawnder.databinding.ActivityOnboardingPageBinding
import com.thetemz.pawnder.databinding.BottomSheetDialogLoginBinding
import com.thetemz.pawnder.databinding.BottomSheetDialogRegisterBinding
import com.thetemz.pawnder.databinding.BottomSheetOtpverificationBinding
import com.thetemz.pawnder.model.LoginWithPasswordResModel
import com.thetemz.pawnder.model.SliderDataItem
import com.thetemz.pawnder.viewModel.AccountSubTypeViewModel
import com.thetemz.pawnder.viewModel.AccountTypeViewModel
import com.thetemz.pawnder.viewModel.LoginWithPasswordViewModel
import com.thetemz.pawnder.viewModel.ReigsterViewModel
import com.thetemz.pawnder.viewpagerslider.SpeedSlowScroller
import java.lang.reflect.Field
import java.util.*
import java.util.regex.Pattern


class OnboardingPageActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding_login: BottomSheetDialogLoginBinding
    private lateinit var binding_register: BottomSheetDialogRegisterBinding
    private lateinit var binding_otp: BottomSheetOtpverificationBinding
    private lateinit var bindingActivity: ActivityOnboardingPageBinding

//    private val loginWithPassword = ViewModelProvider(this).get(LoginWithPasswordViewModel::class.java)

    private val loginWithPassword: LoginWithPasswordViewModel by viewModels()
    private val userSendOTP: UserSendOTPViewModel by viewModels()
    private val UserVerifyviewmodel: UserVerifiyOTPViewModel by viewModels()
    private val registerViewModel: ReigsterViewModel by viewModels()
    private val accountTypeViewModel: AccountTypeViewModel by viewModels()
    private val accountSubTypeViewModel: AccountSubTypeViewModel by viewModels()

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
    private var preferenceHelper: PreferenceHelper? = null

    lateinit var listViews: ArrayList<SliderDataItem>
    var dialoglogin: BottomSheetDialog? = null
    var dialogregister: BottomSheetDialog? = null

    val RC_SIGN_IN: Int = 1
    var Accounttype: String? = null
    var AccountSubtype: String? = null

    lateinit var email: String
    lateinit var Name: String
    lateinit var Mobile: String
    lateinit var Password: String
    lateinit var Confirmpassword: String
    lateinit var Address: String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceHelper = PreferenceHelper.getPreference(this)
        binding_login = BottomSheetDialogLoginBinding.inflate(layoutInflater)
        binding_register = BottomSheetDialogRegisterBinding.inflate(layoutInflater)
        binding_otp = BottomSheetOtpverificationBinding.inflate(layoutInflater)

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
                if (binding_login.phoneet.text.toString().isEmpty()) {
                    Toast.makeText(this, "The phone field is required.", Toast.LENGTH_LONG).show()
                } else if (binding_login.editPassword.text.toString().isEmpty()) {
                    Toast.makeText(this, "The password field is required.", Toast.LENGTH_LONG)
                        .show()
                } else {

                    loginWithPassword.loginWithPassword(
                        binding_login.phoneet.text.toString(),
                        binding_login.editPassword.text.toString()

                    )
                    setLoginWithPasswordObserver()


                }

            }

            binding_login.tvregister.setOnClickListener {
                Toast.makeText(this, "Please", Toast.LENGTH_LONG).show()
                setUpBottomDialogRegister(mContext)
            }


        }


    }

    private fun setLoginWithPasswordObserver() {

        loginWithPassword.getProgressObserver.observe(this) {
            if (it) {
                binding_login.progressBarLogin.visibility = View.VISIBLE
            } else {
                binding_login.progressBarLogin.visibility = View.GONE
            }
        }

        loginWithPassword.getLoginWithPassword.observe(this) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.d(TAG, "setLoginWithPasswordObserver: ${it.data}")
            Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()


            if (it.status == true) {
                it.data?.let { it1 ->
                    it1?.accessToken?.let { it2 ->
                        preferenceHelper?.saveLoginTokenValue(
                            this,
                            it2
                        )
                    }
                }


                preferenceHelper!!.setAlreadyLogedinStatus(this, true)
                preferenceHelper?.saveLoginResponseDataModel(this, it)
                val testLoginData: LoginWithPasswordResModel =
                    preferenceHelper?.getLoginModelObject(this)!!
                Log.e("LoginData=", testLoginData.data.toString())

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }else{
             //   Toast.makeText(this, it, Toast.LENGTH_LONG).show()

            }



        }
/*

        loginWithPassword.getLoginWithPassword.observe(this) {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            if (it.status == true) {

                //Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }

        }
*/

        loginWithPassword.getMessageObserver.observe(this)
        {

            Toast.makeText(this, it, Toast.LENGTH_LONG).show()


        }

    }

    private fun setRegisterObserver() {

        registerViewModel.getProgressObserver.observe(this) {
            if (it) {

                binding_otp.progressBarOtp.visibility = View.VISIBLE
            } else {
                binding_otp.progressBarOtp.visibility = View.GONE
            }
        }

        registerViewModel.getLoginWithPassword.observe(this) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.d(TAG, "setLoginWithPasswordObserver: ${it.data}")
//            Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()

            it.data?.let { it1 ->
                it1?.accessToken?.let { it2 ->
                    preferenceHelper?.saveLoginTokenValue(
                        this,
                        it2
                    )
                }
            }

            if (it.status == true) {

                preferenceHelper!!.setAlreadyLogedinStatus(this, true)
                preferenceHelper?.saveLoginResponseDataModel(this, it)
            }


            val testLoginData: LoginWithPasswordResModel =
                preferenceHelper?.getLoginModelObject(this)!!
            Log.e("LoginData=", testLoginData.data.toString())
        }

        registerViewModel.getLoginWithPassword.observe(this) {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            if (it.status == true) {

              //  Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

        registerViewModel.getMessageObserver.observe(this)
        {

            Toast.makeText(this, it, Toast.LENGTH_LONG).show()


        }

    }

    private fun setAccontTYpeObserver() {

        accountTypeViewModel.getProgressObserver.observe(this) {
            if (it) {

                binding_register.progress.visibility = View.VISIBLE
            } else {
                binding_register.progress.visibility = View.GONE
            }
        }

        accountTypeViewModel.getliveDataaccountTyperepo.observe(this) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.e(TAG, "getliveDataaccountTyperepo: ${it.data}")
            Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
            if (it.data.size > 0) {

                val customDropDownAdapter = AccountTypeSpinnerAdapter(this, it)
                binding_register.AccountTypeSP.adapter = customDropDownAdapter
                binding_register.AccountTypeSP.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {


                            Accounttype = it.data.get(p2).id.toString()

                            accountSubTypeViewModel.getAccountSubType(Accounttype.toString())
                            setAccontSubTypeObserver();
                            // showToast(it.data.get(p2).accountName.toString())
/*

                            if (AccountType[p2].equals("Pet adoption")) {
                                binding_register.petadoptionLL.visibility = View.VISIBLE

                            } else {
                                binding_register.petadoptionLL.visibility = View.GONE

                            }
*/


                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }

                    }

            }


        }

        accountTypeViewModel.getMessageObserver.observe(this)
        {

            Toast.makeText(this, it, Toast.LENGTH_LONG).show()


        }

    }

    private fun setAccontSubTypeObserver() {

        accountSubTypeViewModel.getProgressObserver.observe(this) {
            if (it) {

                binding_register.progress.visibility = View.VISIBLE
            } else {
                binding_register.progress.visibility = View.GONE
            }
        }

        accountSubTypeViewModel.getliveDataaccountTyperepo.observe(this) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.e(TAG, "getliveDataaccountTyperepo: ${it.data}")
            if (it.status == true) {


                if (it.data.size > 0) {

                    binding_register.petadoptionLL.visibility = View.VISIBLE
                    binding_register.AccountSubTypeSP.visibility = View.VISIBLE

                    val customDropDownAdapter = AccountSubTypeSpinnerAdapter(this, it)
                    binding_register.AccountSubTypeSP.adapter = customDropDownAdapter
                    binding_register.AccountSubTypeSP.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {


                                AccountSubtype = it.data.get(p2).id.toString()


                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }

                        }

                } else {
                    AccountSubtype=""
                    binding_register.petadoptionLL.visibility = View.GONE
                    binding_register.AccountSubTypeSP.visibility = View.GONE

                }

            } else {

                AccountSubtype=""
                binding_register.petadoptionLL.visibility = View.GONE
                binding_register.AccountSubTypeSP.visibility = View.GONE


            }


        }

        accountTypeViewModel.getMessageObserver.observe(this)
        {

            Toast.makeText(this, it, Toast.LENGTH_LONG).show()


        }

    }

    private fun setUsersendotpObserver() {

        userSendOTP.getProgressObserver.observe(this) {
            if (it) {
                binding_register.progress.visibility = View.VISIBLE
            } else {
                binding_register.progress.visibility = View.GONE
            }
        }

        userSendOTP.getUserSendOTP.observe(this) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            Log.d(TAG, "setSendOTPObserver: ${it.toString()}")
            Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
            if (it.status == true) {
                setUpOtpObserver();
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                /*val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()*/
            }

        }



        userSendOTP.getMessageObserver.observe(this)
        {

            Toast.makeText(this, it, Toast.LENGTH_LONG).show()


        }

    }

    private fun setUserVerifiyOTPObserver() {

        UserVerifyviewmodel.getProgressObserver.observe(this) {
            if (it) {
                binding_otp.progressBarOtp.visibility = View.VISIBLE
            } else {

                binding_otp.progressBarOtp.visibility = View.GONE
            }
        }

        UserVerifyviewmodel.getVerifiyOTP.observe(this) {
            //  preferenceHelper = PreferenceHelper.getPreference(this)
            if (it.status == true) {
                Log.e(TAG, "registerViewModel:$Name,$Mobile,$email,$Address,${Accounttype.toString()},$AccountSubtype,$Password,$Confirmpassword")


                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                Accounttype?.let { it1 ->
                    AccountSubtype?.let { it2 ->
                        registerViewModel.register(
                            Name,
                            Mobile,
                            email,
                            Address,
                            it1,
                            it2,
                            Password,
                            Confirmpassword
                        )
                    }
                }

                setRegisterObserver()

                /*val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()*/
            }

        }



        UserVerifyviewmodel.getMessageObserver.observe(this)
        {

            Toast.makeText(this, it, Toast.LENGTH_LONG).show()


        }

    }

    private fun setUpBottomDialogRegister(mContext: Context) {

        if (binding_register.root != null) {
            accountTypeViewModel.getHomeCategories()

            setAccontTYpeObserver()


            /*  val AccountType =
                  arrayOf("Select Account Type", "Breeding", "Pet adoption", "Dog sitter finder")*/

            binding_register.root.maxHeight
            dialogregister = BottomSheetDialog(this, R.style.BottomSheetStyleTheme)
            binding_register = BottomSheetDialogRegisterBinding.inflate(layoutInflater)
            dialogregister?.setContentView(binding_register.root)
            dialogregister?.show()
            dialoglogin?.dismiss()
/*
            val arrayAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, AccountType)*/



            binding_register.cardViewSignUp.setOnClickListener {
                email = binding_register.etMail.text.toString();
                Name = binding_register.etName.text.toString();
                Mobile = binding_register.etMobile.text.toString();
                Password = binding_register.etPassword.text.toString();
                Confirmpassword = binding_register.etConfirmpassword.text.toString();
                Address = binding_register.addressET.text.toString();


                if (email.isEmpty()) {
                    Toast.makeText(this, "Please enter email id", Toast.LENGTH_LONG).show()
                } else if (!email
                        .matches(emailPattern.toRegex())
                ) {
                    Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_LONG)
                        .show()
                } else if (Name.isEmpty()) {
                    Toast.makeText(this, "Please enter Name ", Toast.LENGTH_LONG)
                        .show()
                } else if (Mobile.length < 10) {
                    Toast.makeText(this, "Please enter valid number  ", Toast.LENGTH_LONG)
                        .show()
                } else if (Password.length < 6) {
                    Toast.makeText(
                        this,
                        "Password should be minimum 6 characters",
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else if (!Password
                        .equals(Confirmpassword)
                ) {
                    Toast.makeText(
                        this,
                        "Confirm Password should Same as Password ",
                        Toast.LENGTH_LONG
                    )
                        .show()
                } /*else if (Accounttype.toString().equals("Select Account Type")) {

                    Toast.makeText(this, "Please Select Account Type", Toast.LENGTH_LONG)
                        .show()
                } else if (Accounttype.toString()
                        .equals("Pet adoption") && Petadoptiontype.toString().equals(null)
                ) {


                    Toast.makeText(this, "Please Pet adoption Type", Toast.LENGTH_LONG)
                        .show()
                }*/
                else if (Address.isEmpty()) {
                    Toast.makeText(this, "Please enter Address ", Toast.LENGTH_LONG)
                        .show()
                } else {


                    userSendOTP.UserSendOTP(binding_register.etMobile.text.toString())

                    setUsersendotpObserver();


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

    private fun setUpOtpObserver() {


        if (binding_otp.root != null) {
            dialogregister?.dismiss()

            binding_otp = BottomSheetOtpverificationBinding.inflate(layoutInflater)
            binding_otp.tvMailId.text = Mobile
//                    setTimerCall()
            val dialogOtp = BottomSheetDialog(this, R.style.BottomSheetStyleTheme)
            dialogOtp.setContentView(binding_otp.root)
            dialogOtp.show()

            getOtpVerifyByUserEnter()
        }


    }

    private fun getOtpVerifyByUserEnter() {

        binding_otp.otpEditBox1.addTextChangedListener(
            GenericTextWatcher(
                binding_otp.otpEditBox1,
                binding_otp.otpEditBox2
            )
        )
        binding_otp.otpEditBox2.addTextChangedListener(
            GenericTextWatcher(
                binding_otp.otpEditBox2,
                binding_otp.otpEditBox3
            )
        )
        binding_otp.otpEditBox3.addTextChangedListener(
            GenericTextWatcher(
                binding_otp.otpEditBox3,
                binding_otp.otpEditBox4
            )
        )
        binding_otp.otpEditBox4.addTextChangedListener(
            GenericTextWatcher(
                binding_otp.otpEditBox4,
                binding_otp.otpEditBox5
            )
        )
        binding_otp.otpEditBox5.addTextChangedListener(
            GenericTextWatcher(
                binding_otp.otpEditBox5,
                binding_otp.otpEditBox6
            )
        )
        binding_otp.otpEditBox6.addTextChangedListener(
            GenericTextWatcher(
                binding_otp.otpEditBox6,
                null
            )
        )

        binding_otp.otpEditBox1.setOnKeyListener(GenericKeyEvent(binding_otp.otpEditBox1, null))
        binding_otp.otpEditBox2.setOnKeyListener(
            GenericKeyEvent(
                binding_otp.otpEditBox2,
                binding_otp.otpEditBox1
            )
        )

        binding_otp.otpEditBox3.setOnKeyListener(
            GenericKeyEvent(
                binding_otp.otpEditBox3,
                binding_otp.otpEditBox2
            )
        )

        binding_otp.otpEditBox4.setOnKeyListener(
            GenericKeyEvent(
                binding_otp.otpEditBox4,
                binding_otp.otpEditBox3
            )
        )

        binding_otp.otpEditBox5.setOnKeyListener(
            GenericKeyEvent(
                binding_otp.otpEditBox5,
                binding_otp.otpEditBox4
            )
        )

        binding_otp.otpEditBox6.setOnKeyListener(
            GenericKeyEvent(
                binding_otp.otpEditBox6,
                binding_otp.otpEditBox5
            )
        )

        binding_otp.cardViewConfirm.setOnClickListener {
            if (binding_otp.otpEditBox1.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_LONG).show()
            } else if (binding_otp.otpEditBox2.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_LONG).show()
            } else if (binding_otp.otpEditBox3.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter OTP ", Toast.LENGTH_LONG).show()
            } else if (binding_otp.otpEditBox4.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_LONG).show()
            } else if (binding_otp.otpEditBox5.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_LONG).show()
            } else if (binding_otp.otpEditBox6.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_LONG).show()
            } else {

                var otpOne = binding_otp.otpEditBox1.text.toString()
                var otpTwo = binding_otp.otpEditBox2.text.toString()
                var otpThree = binding_otp.otpEditBox3.text.toString()
                var otpFour = binding_otp.otpEditBox4.text.toString()
                var otpFive = binding_otp.otpEditBox5.text.toString()
                var otpSix = binding_otp.otpEditBox6.text.toString()

                var otpEnterByUser = otpOne + otpTwo + otpThree + otpFour + otpFive + otpSix
                UserVerifyviewmodel.UserSendOTP(Mobile, otpEnterByUser)

                setUserVerifiyOTPObserver()

                //setUpVerifyOtpObserver(otpEnterByUser)
            }
        }

        binding_otp.tvResendOtpTime.setOnClickListener {

            if (binding_otp.tvResendOtpTime.text == "Resend OTP") {
/*                setTimerCall()
                reSendOtpViewModel.sendOtp(binding_login.etMailId.text.toString())
                smsReceiver?.bindListener(this)
                resendOtpObserver()
                Log.d(TAG, "checkMailIdObserver:$smsReceiver")*/
            }

        }


    }

    class GenericTextWatcher internal constructor(
        private val currentView: View,
        private val nextView: View?
    ) : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            val text = editable.toString()
            when (currentView.id) {
                R.id.otp_edit_box1 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.otp_edit_box2 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.otp_edit_box3 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.otp_edit_box4 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.otp_edit_box5 -> if (text.length == 1) nextView!!.requestFocus()
                //  R.id.otp_edit_box6 -> if (text.length == 1) nextView!!.requestFocus()
            }
        }

    }

    class GenericKeyEvent internal constructor(
        private val currentView: EditText,
        private val previousView: EditText?
    ) : View.OnKeyListener {
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.otp_edit_box1 && currentView.text.isEmpty()) {
                previousView!!.text = null
                previousView.requestFocus()
                return true
            }
            return false
        }
    }

}











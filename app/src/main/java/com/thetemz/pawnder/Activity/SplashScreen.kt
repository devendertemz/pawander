package com.thetemz.pawnder.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.thetemz.pawnder.MainActivity
import com.thetemz.pawnder.R
import com.thetemz.pawnder.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding;
    private var mContext : Context? = null
    private var timer : Thread ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_splash_screen)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)




        setContentView(binding.root)


        Handler().postDelayed({
            val intent = Intent(this, OnboardingPageActivity::class.java)
            startActivity(intent)

            /* if(preferenceHelper?.getLoginModelObject(requireContext())!=null ){
                 helper?.clearLoginData(requireContext())
             }*/
          /*  if(preferenceHelper!!.getAlreadyLogedinStatus(this)){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, OnboardingPageActivity::class.java)
                startActivity(intent)
                finish()
            }*/
        }, 2000)

    }

}
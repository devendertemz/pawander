package com.thetemz.pawnder.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thetemz.pawnder.R
import com.thetemz.pawnder.model.ProfileGetModelResponse
import com.thetemz.pawnder.model.UpdatePassswordmodelRepo
import com.thetemz.pawnder.network.APIFactory
import com.thetemz.pawnder.resporities.GetProfileRepository
import com.thetemz.pawnder.utils.Global
import com.thetemz.pawnder.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProfileDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val ProfileRepo = GetProfileRepository(APIFactory.makeServiceAPi())
    private val mContext get() = getApplication<Application>().applicationContext
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

   
    private val liveDataProfile = MutableLiveData<ProfileGetModelResponse>()
    private val liveDataUpdatepassword = MutableLiveData<UpdatePassswordmodelRepo>()

    private val liveDataProgress = SingleLiveEvent<Boolean>()
    private val liveDataStatus = SingleLiveEvent<Boolean>()
    private val liveDataMessage = SingleLiveEvent<String>()


    val getProgressObserver
        get() : LiveData<Boolean>
        = liveDataProgress

    val getMessageObserver
        get() : LiveData<String>
        = liveDataMessage


    val getStatusObserver
        get() : LiveData<Boolean>
        = liveDataStatus

    /*val getLoginWithPassword
        get() : LiveData<LoginWithPassword>
        =   liveDataLoginWithPassword */

    val getProfile
        get() : LiveData<ProfileGetModelResponse>
        = liveDataProfile


   val getUpdateapssword
        get() : LiveData<UpdatePassswordmodelRepo>
        = liveDataUpdatepassword


    @SuppressLint("NullSafeMutableLiveData")
    fun getProfile(
        authorization: String

        ) {
        if (!Global.hasInternet(mContext)) {
            liveDataMessage.postValue(mContext.getString(R.string.pls_check_your_internet_connection))
            return
        }

        scope.launch {

            liveDataProgress.postValue(true)
            var response = ProfileRepo.getProfile(authorization)

            response.let {
                if (it?.status == true){
                    liveDataProfile.postValue(it)
                }else{
                    Log.e("it",response.toString())
                    liveDataMessage.postValue("Login Failed !!! Invalid Login Credentials.")
                }
            }


         
            liveDataProgress.postValue(false)

        }

    }


    @SuppressLint("NullSafeMutableLiveData")
    fun UpdateProfile(
        authorization: String,
        name: String,
        address: String,
        ) {
        if (!Global.hasInternet(mContext)) {
            liveDataMessage.postValue(mContext.getString(R.string.pls_check_your_internet_connection))
            return
        }

        scope.launch {

            liveDataProgress.postValue(true)
            var response = ProfileRepo.updateProfile(authorization,name,address)

            response.let {
                if (it?.status == true){
                    liveDataProfile.postValue(it)
                }else{
                    Log.e("it",response.toString())
                    liveDataMessage.postValue("Invalid User !!!")
                }
            }



            liveDataProgress.postValue(false)

        }

    }

  @SuppressLint("NullSafeMutableLiveData")
    fun UpdatePassword(
      authorization: String,
      old_password: String,
      password: String,
      confirm_password: String,

        ) {
        if (!Global.hasInternet(mContext)) {
            liveDataMessage.postValue(mContext.getString(R.string.pls_check_your_internet_connection))
            return
        }

        scope.launch {

            liveDataProgress.postValue(true)
            var response = ProfileRepo.updatePassword(authorization,old_password,password,confirm_password)

            response.let {
                if (it?.status == true){
                    liveDataUpdatepassword.postValue(it)
                }else{
                    Log.e("it",response.toString())
                    liveDataMessage.postValue("Old Password Not Matched !!!")
                }
            }



            liveDataProgress.postValue(false)

        }

    }


    @SuppressLint("NullSafeMutableLiveData")
    fun userLogout(
      authorization: String,


        ) {
        if (!Global.hasInternet(mContext)) {
            liveDataMessage.postValue(mContext.getString(R.string.pls_check_your_internet_connection))
            return
        }

        scope.launch {

            liveDataProgress.postValue(true)
            var response = ProfileRepo.userLogout(authorization)

            response.let {
                if (it?.status == true){
                    liveDataUpdatepassword.postValue(it)
                }else{
                    Log.e("it",response.toString())
                    liveDataMessage.postValue("Invalid User !!!")
                }
            }



            liveDataProgress.postValue(false)

        }

    }


}
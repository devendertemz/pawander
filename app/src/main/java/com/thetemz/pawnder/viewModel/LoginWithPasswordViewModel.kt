package com.thetemz.pawnder.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thetemz.pawnder.R
import com.thetemz.pawnder.model.LoginWithPasswordResModel
import com.thetemz.pawnder.network.APIFactory
import com.thetemz.pawnder.resporities.LoginWithPasswordRepository
import com.thetemz.pawnder.utils.Global
import com.thetemz.pawnder.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginWithPasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val loginWithPasswordRepo = LoginWithPasswordRepository(APIFactory.makeServiceAPi())
    private val mContext get() = getApplication<Application>().applicationContext
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    //  private val liveDataLoginWithPassword = MutableLiveData<LoginWithPassword>()
    private val liveDataLoginWithPassword = MutableLiveData<LoginWithPasswordResModel>()

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

    val getLoginWithPassword
        get() : LiveData<LoginWithPasswordResModel>
        = liveDataLoginWithPassword


    @SuppressLint("NullSafeMutableLiveData")
    fun loginWithPassword(
        phone: String,
        password: String,

        ) {
        if (!Global.hasInternet(mContext)) {
            liveDataMessage.postValue(mContext.getString(R.string.pls_check_your_internet_connection))
            return
        }

        scope.launch {

            liveDataProgress.postValue(true)
            var response = loginWithPasswordRepo.getLoginWithPassword(phone, password)

            response.let {
                if (it?.status == true){
                    liveDataLoginWithPassword.postValue(it)
                }else{
                    Log.e("it",response.toString())
                    liveDataMessage.postValue("Login Failed !!! Invalid Login Credentials.")
                }
            }


            /*response?.let {
                if (it.status == true) {
                    //   liveDataLoginWithPassword.postValue(it.result)
                    liveDataLoginWithPassword.postValue(it)
                    liveDataMessage.postValue(it.data.toString())
                }
                liveDataMessage.postValue(it.message1?.error.toString())
                Log.e(TAG, "loginWithPasswordBuddy: ${response.message}")

            } ?: liveDataMessage.postValue(response?.message)
            Log.e(TAG, "loginWithPassword: ${response?.message}")*/
            liveDataProgress.postValue(false)

        }

    }


}
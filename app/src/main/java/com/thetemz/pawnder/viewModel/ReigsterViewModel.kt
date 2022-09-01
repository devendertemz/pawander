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
import com.thetemz.pawnder.resporities.RegisterRespository
import com.thetemz.pawnder.utils.Global
import com.thetemz.pawnder.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ReigsterViewModel(application: Application) : AndroidViewModel(application) {

    private val RegisterRepo = RegisterRespository(APIFactory.makeServiceAPi())
    private val mContext get() = getApplication<Application>().applicationContext
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    //  private val liveDataLoginWithPassword = MutableLiveData<LoginWithPassword>()
    private val liveDataRegister = MutableLiveData<LoginWithPasswordResModel>()

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
        = liveDataRegister


    @SuppressLint("NullSafeMutableLiveData")
    fun register(
        name : String,
        phone : String,
        email : String,
        address : String,
        account_type : String,
        account_subtype : String,
        password : String,
        confirm_password : String,

        ) {
        if (!Global.hasInternet(mContext)) {
            liveDataMessage.postValue(mContext.getString(R.string.pls_check_your_internet_connection))
            return
        }

        scope.launch {

            liveDataProgress.postValue(true)
            var response = RegisterRepo.getRegister(name,phone,email,address,account_type,account_subtype,password,confirm_password)
            


            response?.let {
                if (it.status == true) {
                    //   liveDataLoginWithPassword.postValue(it.result)
                    liveDataRegister.postValue(it)
                    liveDataMessage.postValue(it.data.toString())
                }
               // liveDataMessage.postValue(it.message)
                Log.e(TAG, "loginWithPasswordBuddy: ${response.message}")

            } ?: //liveDataMessage.postValue(response?.message)
            Log.e(TAG, "loginWithPassword: ${response?.message}")
            liveDataProgress.postValue(false)

        }

    }


}
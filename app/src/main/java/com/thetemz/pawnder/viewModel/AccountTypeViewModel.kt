package com.thetemz.pawnder.viewModel

import AccountSubType
import AccountTypeResModel
import AccounttypeData
import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thetemz.pawnder.R
import com.thetemz.pawnder.model.Data
import com.thetemz.pawnder.network.APIFactory
import com.thetemz.pawnder.resporities.AccountTypeRepository
import com.thetemz.pawnder.resporities.LoginWithPasswordRepository
import com.thetemz.pawnder.utils.Global
import com.thetemz.pawnder.utils.SingleLiveEvent
import kotlinx.coroutines.*
import kotlin.Result
import kotlin.coroutines.CoroutineContext

class AccountTypeViewModel(application: Application) : AndroidViewModel(application) {

    private val accountTypeRepo = AccountTypeRepository(APIFactory.makeServiceAPi())

    private val mContext get() = getApplication<Application>().applicationContext
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)




    private val liveDataaccountTyperepo = MutableLiveData<AccountTypeResModel>()



    private val liveDataProgress = SingleLiveEvent<Boolean>()
    private val liveDataStatus = SingleLiveEvent<Boolean>()
    private val liveDataMessage = SingleLiveEvent<String>()


    val getliveDataaccountTyperepo
        get() : LiveData<AccountTypeResModel>
        = liveDataaccountTyperepo

    val getProgressObserver
        get() : LiveData<Boolean>
        = liveDataProgress

    val getDataStatusObserver
        get() : LiveData<Boolean>
        = liveDataStatus

    val getMessageObserver
        get() : LiveData<String>
        = liveDataMessage

    @SuppressLint("NullSafeMutableLiveData")
    fun getHomeCategories() {

        if (!Global.hasInternet(mContext)) {
            liveDataMessage.postValue(mContext.getString(R.string.pls_check_your_internet_connection))
            return
        }
        scope.launch {
            liveDataProgress.postValue(true)

            val response = accountTypeRepo.getAccountType()
            Log.d(TAG, "accountTypeRepo:$response ")
            response?.let { responseData ->
                if (responseData.status == true) {
                    liveDataaccountTyperepo.postValue(responseData)

                } else {
                    liveDataMessage.postValue(responseData.message)
                }
            } ?: liveDataMessage.postValue("Oops Something went wrong:-$response")
            liveDataProgress.postValue(false)

        }
    }

}

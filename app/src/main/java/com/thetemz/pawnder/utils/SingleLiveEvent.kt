package com.thetemz.pawnder.utils

import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if(hasActiveObservers()) {
            Log.d(TAG, "Multiple observer registered but only one will be notified of changes. ")
        }
       //Observer the internal MutableLiveData
        super.observe(owner, { t ->
            if(mPending.compareAndSet(true,false)){
              observer.onChanged(t)
            }

        })
    }

    override fun setValue(value: T) {
        mPending.set(true)
        super.setValue(value)
    }


   /* fun call(){
        setValue(null)
    }*/

    companion object {
      private val TAG = "SingleLiveEvent"
    }

}
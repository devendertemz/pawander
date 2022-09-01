package com.thetemz.pawnder.resporities
import AccountTypeResModel
import android.content.ContentValues
import android.util.Log
import com.thetemz.pawnder.network.RetrofitService
import java.lang.Exception

class AccountTypeRepository (private val apiService: RetrofitService) : BaseRepository() {

    suspend fun getAccountType() : AccountTypeResModel? {
        return try {
            safeApiCall(
                call = { apiService.getAccountType().await() },
                error = "Error from server"
            )
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getHomePageCategories: $e")
            null
        }
    }
}
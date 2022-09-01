package com.thetemz.pawnder.resporities
import AccountSubTypeResModel
import AccountTypeResModel
import android.content.ContentValues
import android.util.Log
import com.thetemz.pawnder.network.RetrofitService
import java.lang.Exception

class AccountSubTypeRepository (private val apiService: RetrofitService) : BaseRepository() {

    suspend fun getSubAccountType(
        account_id : String,

        ) : AccountSubTypeResModel? {
        return try {
            safeApiCall(
                call = { apiService.getAccountSubType(account_id).await() },
                error = "Error from server"
            )
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getHomePageCategories: $e")
            null
        }
    }
}
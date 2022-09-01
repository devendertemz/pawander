package com.thetemz.pawnder.resporities
import AccountTypeResModel
import android.content.ContentValues
import android.util.Log
import com.thetemz.pawnder.model.ProfileGetModelResponse
import com.thetemz.pawnder.model.UpdatePassswordmodelRepo
import com.thetemz.pawnder.network.RetrofitService
import java.lang.Exception

class GetProfileRepository (private val apiService: RetrofitService) : BaseRepository() {

    suspend fun getProfile(
        authorization: String
    ) : ProfileGetModelResponse? {
        return try {
            safeApiCall(
                call = { apiService.getProfile(authorization).await() },
                error = "Error from server"
            )
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getHomePageCategories: $e")
            null
        }
    }

    suspend fun updatePassword(
        authorization: String,
        old_password: String,
        password: String,
        confirm_password: String,
    ) : UpdatePassswordmodelRepo? {
        return try {
            safeApiCall(
                call = { apiService.updatepassword(authorization,old_password,password,confirm_password).await() },
                error = "Error from server"
            )
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getHomePageCategories: $e")
            null
        }
    }




    suspend fun userLogout(
        authorization: String,


    ) : UpdatePassswordmodelRepo? {
        return try {
            safeApiCall(
                call = { apiService.userLogout(authorization).await() },
                error = "Error from server"
            )
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getHomePageCategories: $e")
            null
        }
    }


    suspend fun updateProfile(
        authorization: String,
        name: String,
        address: String,
    ) : ProfileGetModelResponse? {
        return try {
            safeApiCall(
                call = { apiService.updateProfile(authorization,name,address).await() },
                error = "Error from server"
            )
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getHomePageCategories: $e")
            null
        }
    }
}
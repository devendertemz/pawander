package com.thetemz.pawnder.resporities
import android.content.ContentValues
import android.util.Log
import com.thetemz.pawnder.model.ProfileGetModelResponse
import com.thetemz.pawnder.model.UpdatePassswordmodelRepo
import com.thetemz.pawnder.model.petListResModel
import com.thetemz.pawnder.network.RetrofitService
import java.lang.Exception

class petListRepository (private val apiService: RetrofitService) : BaseRepository() {

    suspend fun getPetList(
        token : String,

        ) : petListResModel? {
        return try {
            safeApiCall(
                call = { apiService.getpetList(token).await() },
                error = "Error from server"
            )
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "petListResModel: $e")
            null
        }
    }


    suspend fun DeletePet(
        token : String,
        pet_id : String

        ) : UpdatePassswordmodelRepo? {
        return try {
            safeApiCall(
                call = { apiService.DeletePet(token,pet_id).await() },
                error = "Error from server"
            )
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "petListResModel: $e")
            null
        }
    }
}
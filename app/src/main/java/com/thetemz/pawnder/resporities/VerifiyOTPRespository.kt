package com.thetemz.pawnder.resporities

import com.thetemz.pawnder.model.LoginWithPasswordResModel
import com.thetemz.pawnder.model.SucessFullyResModel
import com.thetemz.pawnder.network.RetrofitService
import java.lang.Exception

class VerifiyOTPRespository(private val apiService : RetrofitService) : BaseRepository() {


    suspend fun  getSendOTP(
        phone : String,
        OTP : String

    )  : SucessFullyResModel? {

        return try {

            safeApiCall(
                call = { apiService.UserVerifiyOTP(phone,OTP).await() } ,
                error = "Error from server"
            )

        }catch (e : Exception){
            null
        }

    }

}
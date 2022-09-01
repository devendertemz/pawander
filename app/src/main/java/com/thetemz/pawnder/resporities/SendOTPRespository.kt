package com.thetemz.pawnder.resporities

import com.thetemz.pawnder.model.LoginWithPasswordResModel
import com.thetemz.pawnder.model.SucessFullyResModel
import com.thetemz.pawnder.network.RetrofitService
import java.lang.Exception

class SendOTPRespository(private val apiService : RetrofitService) : BaseRepository() {


    suspend fun  getSendOTP(
        phone : String

    )  : SucessFullyResModel? {

        return try {

            safeApiCall(
                call = { apiService.UserSendOTP(phone).await() } ,
                error = "Error from server"
            )

        }catch (e : Exception){
            null
        }

    }

}
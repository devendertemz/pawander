package com.thetemz.pawnder.resporities

import com.thetemz.pawnder.model.LoginWithPasswordResModel
import com.thetemz.pawnder.network.RetrofitService
import java.lang.Exception

class LoginWithPasswordRepository(private val apiService : RetrofitService) : BaseRepository() {


    suspend fun  getLoginWithPassword(
        phone : String,
        password : String,

    )  : LoginWithPasswordResModel? {

        return try {

            safeApiCall(
                call = { apiService.login_with_password(phone,password).await() } ,
                error = "Error from server"
            )

        }catch (e : Exception){
            null
        }

    }

}
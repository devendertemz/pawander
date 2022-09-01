package com.thetemz.pawnder.resporities

import com.thetemz.pawnder.model.LoginWithPasswordResModel
import com.thetemz.pawnder.network.RetrofitService
import java.lang.Exception

class RegisterRespository(private val apiService : RetrofitService) : BaseRepository() {


    suspend fun  getRegister(
        name : String,
        phone : String,
        email : String,
        address : String,
        account_type : String,
        account_subtype : String,
        password : String,
        confirm_password : String,



    )  : LoginWithPasswordResModel? {

        return try {

            safeApiCall(
                call = { apiService.Userregistration(name,phone,email,address,account_type,account_subtype,password,confirm_password).await() } ,

                error = "Error from server"
            )

        }catch (e : Exception){
            null
        }

    }

}
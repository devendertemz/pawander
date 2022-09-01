package com.thetemz.pawnder.network

import AccountSubTypeResModel
import AccountTypeResModel
import com.thetemz.pawnder.model.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {


    @FormUrlEncoded
    @POST("user/login")
    fun login_with_password(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Deferred<Response<LoginWithPasswordResModel>>


    @FormUrlEncoded
    @POST("user/registration")
    fun Userregistration(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("account_type") account_type: String,
        @Field("account_subtype") account_subtype: String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String
    ): Deferred<Response<LoginWithPasswordResModel>>


    @FormUrlEncoded
    @POST("user/send/otp")
    fun UserSendOTP(
        @Field("phone") phone: String,
    ): Deferred<Response<SucessFullyResModel>>


    @FormUrlEncoded
    @POST("user/verify/otp")
    fun UserVerifiyOTP(
        @Field("phone") phone: String,
        @Field("otp") otp: String,
    ): Deferred<Response<SucessFullyResModel>>


    @GET("user/account/type")
    fun getAccountType(): Deferred<Response<AccountTypeResModel>>

    @GET("user/profile")
    fun getProfile(
        @Header("Authorization") authorization: String

    ): Deferred<Response<ProfileGetModelResponse>>

 @GET("user/pet/category/list")
    fun getCateoriesList(
        @Header("Authorization") authorization: String
    ): Deferred<Response<CategorylistData>>


    @GET("user/pet/list/all")
    fun getAllPetListData(
        @Header("Authorization") authorization: String,
        //@Path("pet_category_id")  id :String
       @Query("pet_category_id") entity_id: String
    ): Deferred<Response<allPetListdataRepo>>


    @GET("user/logout")
    fun userLogout(
        @Header("Authorization") authorization: String
    ): Deferred<Response<UpdatePassswordmodelRepo>>

    @FormUrlEncoded
    @POST("user/account/sub_type")
    fun getAccountSubType(
        @Field("account_id") phone: String,
    ): Deferred<Response<AccountSubTypeResModel>>


    @POST("user/pet/list")
    fun getpetList(
        @Header("Authorization") authorization: String
    ): Deferred<Response<petListResModel>>


    @Multipart
    @POST("user/pet/registration")
    fun AddNewPetDetails(
        @Header("Authorization") authorization: String,
        @Part("pet_name") pet_name: RequestBody?,
        @Part("pet_age") pet_age: RequestBody?,
        @Part("pet_gender") pet_gender: RequestBody?,
        @Part("pet_category") pet_category: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Deferred<Response<addNewPetDetailsResp>>




    @FormUrlEncoded
    @POST("user/profile/update")
    fun updateProfile(
        @Header("Authorization") authorization: String,
        @Field("name") pet_name: String?,
        @Field("address") address: String?,
        ): Deferred<Response<ProfileGetModelResponse>>

    @FormUrlEncoded
    @POST("user/pet/details/delete")
    fun DeletePet(
        @Header("Authorization") authorization: String,
        @Field("pet_id") pet_id: String?,
        ): Deferred<Response<UpdatePassswordmodelRepo>>


    @FormUrlEncoded
    @POST("user/update/password")
    fun updatepassword(
        @Header("Authorization") authorization: String,
        @Field("old_password") old_password: String?,
        @Field("password") password: String?,
        @Field("confirm_password") confirm_password: String?,
        ): Deferred<Response<UpdatePassswordmodelRepo>>


    @Multipart
    @POST("user/pet/details/update")
    fun UpdatePetDetails(
        @Header("Authorization") authorization: String,
        @Part("pet_id") pet_id: RequestBody?,
        @Part("pet_name") pet_name: RequestBody?,
        @Part("pet_age") pet_age: RequestBody?,
        @Part("pet_gender") pet_gender: RequestBody?,
        @Part("pet_category") pet_category: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Deferred<Response<addNewPetDetailsResp>>


    /* @FormUrlEncoded
     @POST("register")
     fun userRegistration(
         @Field("email") emailId: String,
         @Field("password") password: String,
         @Field("mobile") mobile: String,
         @Field("name") name: String,
         @Field("gender") gender: String,
         @Field("device_type") device_type: String,
         @Field("device_token") device_token: String
     ): Deferred<Response<LoginWithPasswordResModel>>

 */


}
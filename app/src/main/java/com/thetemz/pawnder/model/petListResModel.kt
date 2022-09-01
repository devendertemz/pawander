package com.thetemz.pawnder.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class petListResModel(

    @SerializedName("status"  ) var status  : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Data1> = arrayListOf()
)



data class Data1 (

    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("user_id"      ) var userId      : Int?    = null,
    @SerializedName("pet_category" ) var petCategory : String? = null,
    @SerializedName("pet_name"     ) var petName     : String? = null,
    @SerializedName("pet_category_name"      ) var pet_category_name      : String? = null,
    @SerializedName("pet_age"      ) var petAge      : String? = null,
    @SerializedName("pet_gender"   ) var petGender   : String? = null,
    @SerializedName("file"         ) var file        : String? = null,
    @SerializedName("created_at"   ) var createdAt   : String? = null,
    @SerializedName("updated_at"   ) var updatedAt   : String? = null,
    @SerializedName("name"         ) var name        : String? = null,
    @SerializedName("account_name" ) var accountName : String? = null,
    @SerializedName("address"      ) var address     : String? = null

) : Serializable
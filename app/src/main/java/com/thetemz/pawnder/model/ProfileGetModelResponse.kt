package com.thetemz.pawnder.model

import com.google.gson.annotations.SerializedName

data class ProfileGetModelResponse(
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ProfileData?   = ProfileData()
)

data class ProfileData (

    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("name"         ) var name        : String? = null,
    @SerializedName("email"        ) var email       : String? = null,
    @SerializedName("phone"        ) var phone       : String? = null,
    @SerializedName("address"      ) var address     : String? = null,
    @SerializedName("created_at"   ) var createdAt   : String? = null,
    @SerializedName("account_name" ) var accountName : String? = null

)
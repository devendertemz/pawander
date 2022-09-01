package com.thetemz.pawnder.model

import com.google.gson.annotations.SerializedName

data class UpdatePassswordmodelRepo(
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : String?  = null
)

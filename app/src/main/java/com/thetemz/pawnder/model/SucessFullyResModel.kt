package com.thetemz.pawnder.model

import com.google.gson.annotations.SerializedName

data class SucessFullyResModel(

    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("message") var message: String? = null,

)

package com.thetemz.pawnder.model

import com.google.gson.annotations.SerializedName

data class LoginWithPasswordResModel(

    @SerializedName("status") var status: Boolean,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: Data? = Data(),

)
data class Data(

    @SerializedName("user") var user: User? = User(),
    @SerializedName("access_token") var accessToken: String? = null

)

data class User(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("account_type") var accountType: Int? = null,
    @SerializedName("account_subtype") var accountSubtype: Int? = null,
    @SerializedName("created_at") var createdAt: String? = null

)

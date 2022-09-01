package com.thetemz.pawnder.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategorylistData(
    @SerializedName("status"  ) var status  : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<CategoryData> = arrayListOf()
)

data class CategoryData (

    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("category_name" ) var categoryName : String? = null,
    @SerializedName("created_at"    ) var createdAt    : String? = null,
    @SerializedName("updated_at"    ) var updatedAt    : String? = null

) : Serializable
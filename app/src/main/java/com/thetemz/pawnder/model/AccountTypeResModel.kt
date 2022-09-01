import com.google.gson.annotations.SerializedName


data class AccountTypeResModel(

    @SerializedName("status"  ) var status  : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<AccounttypeData> = arrayListOf()

)

data class AccounttypeData (

    @SerializedName("id"               ) var id             : Int?              = null,
    @SerializedName("account_name"     ) var accountName    : String?           = null,
    @SerializedName("account_sub_type" ) var accountSubType : ArrayList<AccountSubType> = arrayListOf(),
    @SerializedName("created_at"       ) var createdAt      : String?           = null,
    @SerializedName("updated_at"       ) var updatedAt      : String?           = null

)

data class AccountSubType (

    @SerializedName("id"                    ) var id                 : Int?    = null,
    @SerializedName("account_sub_type_name" ) var accountSubTypeName : String? = null,
    @SerializedName("created_at"            ) var createdAt          : String? = null,
    @SerializedName("updated_at"            ) var updatedAt          : String? = null

)

import com.google.gson.annotations.SerializedName


data class AccountSubTypeResModel(

    @SerializedName("status"  ) var status  : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<AccountSubtypeData> = arrayListOf()

)

data class AccountSubtypeData (


    @SerializedName("id"                    ) var id                 : Int?    = null,
    @SerializedName("account_type_id"       ) var accountTypeId      : Int?    = null,
    @SerializedName("account_sub_type_name" ) var accountSubTypeName : String? = null,
    @SerializedName("created_at"            ) var createdAt          : String? = null,
    @SerializedName("updated_at"            ) var updatedAt          : String? = null
)

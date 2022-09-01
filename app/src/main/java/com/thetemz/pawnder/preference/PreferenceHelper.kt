package com.example.mymftcustomer.preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.thetemz.pawnder.model.LoginWithPasswordResModel

class PreferenceHelper(val context: Context) {

    private var preference: SharedPreferences? = null


    val PREF_NAME = "onTracPt"
    lateinit var onTracPtPref: SharedPreferences  //context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    lateinit var editor: SharedPreferences.Editor

    init {
        preference = context.getSharedPreferences(PrefKeys.KEY_PREF_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        fun getPreference(context: Context): PreferenceHelper {
            val helper = PreferenceHelper(context)
            return helper
        }
    }


    fun saveFaceBookMailId(key: String, value: String) {
        preference?.edit()?.putString(key, value)
    }

    fun saveFaceBookFirstName(key: String, value: String) {
        preference?.edit()?.putString(key, value)
    }

    fun saveFaceBookSocialId(key: String, value: String) {
        preference?.edit()?.putString(key, value)
    }


    fun save_banner_size(key: String, value: Int) {
        preference?.edit()?.putInt(key, value)?.apply()
    }

    fun saveCartProductId(key: String, value: Int) {
        preference?.edit()?.putInt(key, value)?.apply()
    }

    fun getCartProductId(key: String): Int? {
        return preference?.getInt(key, 0)
    }

    fun saveSuccessLoginToken(key: String, value: String) {
        preference?.edit()?.putString(key, value)?.apply()
    }

    fun getSuccessLoginTokenValue(key: String): String? {
        return preference?.getString(key, "")
    }


    fun get_save_banner_size(key: String): Int? {
        return preference?.getInt(key, 0)
    }

    fun set_product_detail_save_banner_size(key: String, value: Int) {
        preference?.edit()?.putInt(key, value)?.apply()
    }

    fun get_product_detail_save_banner_size(key: String): Int? {
        return preference?.getInt(key, 0)
    }

    fun saveProductId(key: String, value: String) {
        preference?.edit()?.putString(key, value)?.apply()
    }

    fun saveDeviceToken(key: String, value: String) {
        preference?.edit()?.putString(key, value)?.apply()
    }

    fun getDeviceToken(key: String): String? {
        return preference?.getString(key, "")
    }

    fun saveEmailId(key: String, value: String) {
        preference?.edit()?.putString(key, value)?.apply()
    }

    fun getEmailId(key: String): String? {
        return preference?.getString(key, "")
    }

    fun saveCategoryId(key: String, value: String) {
        preference?.edit()?.putString(key, value)?.apply()
    }

    fun saveProductType(key: String, value: String) {
        preference?.edit()?.putString(key, value)?.apply()
    }

    fun saveAddressTypeValue(key: String, value: Int) {
        preference?.edit()?.putInt(key, value)?.apply()
    }

    fun getAddressTypeValue(key: String): Int? {
        return preference?.getInt(key, 0)
    }

    fun savCityId(key: String, value: String) {
        Log.d("value",value);
        preference?.edit()?.putString(key, value)?.apply()
    }

    fun getCityid(key: String): String? {
        return preference?.getString(key, null)
    }



    fun getProductType(key: String): String? {
        return preference?.getString(key, "")
    }

    fun getSaveProductId(key: String): String? {
        return preference?.getString(key, "")
    }

    fun getCategoryId(key: String): String? {
        return preference?.getString(key, "")
    }


    fun getSaveTabPosition(context: Context): Int? {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        var status = onTracPtPref.getInt("tabPosition", 0)
        return status
    }

    fun saveLoginResponseDataModel(
        context: Context,
        it: LoginWithPasswordResModel?
    ) {

        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        val gson = Gson()
        val json = gson.toJson(it)
        editor = onTracPtPref.edit()
        editor.putString("loginResponse", json)
        editor.commit()

    }

    fun saveLoginTokenValue(context: Context, it: String) {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        val gson = Gson()
        val json = gson.toJson(it)
        editor = onTracPtPref.edit()
        editor.putString("loginTokenResponse", json)
        editor.commit()
    }

    fun SavePageViewerPosition(context: Context, it: String) {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        val gson = Gson()
        val json = gson.toJson(it)
        editor = onTracPtPref.edit()
        editor.putString("SavePageViewerPosition", json)
        editor.commit()
    }

    fun getPageViewerPosition(context: Context): String? {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        val gson = Gson()
        val json = onTracPtPref.getString("SavePageViewerPosition", "0")
        return if (json!!.equals(
                "0",
                ignoreCase = true
            )
        ) null else gson.fromJson<String>(
            json, String::class.java
        )
    }


    fun getSaveLoginTokenValue(context: Context): String? {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        val gson = Gson()
        val json = onTracPtPref.getString("loginTokenResponse", "")
        return if (json!!.equals(
                "",
                ignoreCase = true
            )
        ) null else gson.fromJson<String>(
            json, String::class.java
        )
    }

    fun saveCheckLoginType(context: Context, it: String) {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        val gson = Gson()
        val json = gson.toJson(it)
        editor = onTracPtPref.edit()
        editor.putString("saveCheckLoginType", json)
        editor.commit()

    }

    fun getCheckLoginType(context: Context): String? {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        val gson = Gson()
        val json = onTracPtPref.getString("saveCheckLoginType", "")
        return if (json!!.equals(
                "",
                ignoreCase = true
            )
        ) null else gson.fromJson<String>(
            json, String::class.java
        )
    }

    fun saveSeasionKeyToken(context: Context, it: String) {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        val gson = Gson()
        val json = gson.toJson(it)
        editor = onTracPtPref.edit()
        editor.putString("saveSeasionKeyToken", json)
        editor.commit()
    }


    fun getSeasionKeyToken(context: Context): String? {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        val gson = Gson()
        val json = onTracPtPref.getString("saveSeasionKeyToken", "false")
        return if (json!!.equals(
                "false",
                ignoreCase = true
            )
        ) null else gson.fromJson<String>(
            json, String::class.java
        )
    }

    fun setAlreadyLogedinStatus(context: Context, status: Boolean) {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        editor = onTracPtPref.edit()
        editor.putBoolean("isAlreadyLogin", status)
        editor.apply()
    }

    fun getAlreadyLogedinStatus(context: Context): Boolean {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        var status = false
        status = onTracPtPref.getBoolean("isAlreadyLogin", false)
        return status
    }

    fun getLoginModelObject(context: Context): LoginWithPasswordResModel? {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        val gson = Gson()
        val json = onTracPtPref.getString("loginResponse", "")
        return if (json!!.equals(
                "",
                ignoreCase = true
            )
        ) null else gson.fromJson<LoginWithPasswordResModel>(
            json, LoginWithPasswordResModel::class.java
        )
    }

    fun clearLoginData(context: Context) {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        editor = onTracPtPref.edit()
        editor.clear()
        editor.apply()
    }



    fun deleteOrderDetails(context: Context) {
        onTracPtPref = context.getSharedPreferences(PREF_NAME, 0)
        editor = onTracPtPref.edit()
        editor.remove("getOrderDetailsData")
        editor.apply()
    }

}
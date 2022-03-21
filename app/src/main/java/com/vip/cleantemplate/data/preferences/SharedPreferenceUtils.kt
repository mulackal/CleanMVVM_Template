package com.vip.cleantemplate.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


import java.util.ArrayList


class SharedPreferenceUtils(private var mContext: Context) {

    private var mSharedPreferences: SharedPreferences =
        mContext.getSharedPreferences(mContext.applicationContext.packageName, Context.MODE_PRIVATE)


    private var mSharedPreferencesEditor: SharedPreferences.Editor? = null

    init {
        mSharedPreferencesEditor = mSharedPreferences.edit()
    }

    /**
     * Stores String value in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    fun setValue(key: String, value: String) {
        mSharedPreferencesEditor!!.putString(key, value)
        mSharedPreferencesEditor!!.commit()
    }

    /**
     * Stores int value in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    fun setValue(key: String, value: Int) {
        mSharedPreferencesEditor!!.putInt(key, value)
        mSharedPreferencesEditor!!.commit()
    }

    /**
     * Stores Double value in String format in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    fun setValue(key: String, value: Double) {
        setValue(key, java.lang.Double.toString(value))
    }

    /**
     * Stores long value in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    fun setValue(key: String, value: Long) {
        mSharedPreferencesEditor!!.putLong(key, value)
        mSharedPreferencesEditor!!.commit()
    }

    /**
     * Stores boolean value in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    fun setValue(key: String, value: Boolean) {
        mSharedPreferencesEditor!!.putBoolean(key, value)
        mSharedPreferencesEditor!!.commit()
    }

    /**
     * Retrieves String value from preference
     *
     * @param key          key of preference
     * @param defaultValue default value if no key found
     */
    fun getStringValue(key: String, defaultValue: String): String? {
        return mSharedPreferences.getString(key, defaultValue)
    }

    /**
     * Retrieves int value from preference
     *
     * @param key          key of preference
     * @param defaultValue default value if no key found
     */
    fun getIntValue(key: String, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    /**
     * Retrieves long value from preference
     *
     * @param key          key of preference
     * @param defaultValue default value if no key found
     */
    fun getLongValue(key: String, defaultValue: Long): Long {
        return mSharedPreferences.getLong(key, defaultValue)
    }

    /**
     * Retrieves boolean value from preference
     *
     * @param keyFlag      key of preference
     * @param defaultValue default value if no key found
     */
    fun getBoolanValue(keyFlag: String, defaultValue: Boolean): Boolean {
        return mSharedPreferences.getBoolean(keyFlag, defaultValue)
    }

    /**
     * Removes key from preference
     *
     * @param key key of preference that is to be deleted
     */
    fun removeKey(key: String) {
        if (mSharedPreferencesEditor != null) {
            mSharedPreferencesEditor!!.remove(key)
            mSharedPreferencesEditor!!.commit()
        }
    }


    /**
     * Clears all the preferences stored
     */
    fun clear() {
        mSharedPreferencesEditor!!.clear().commit()
    }


    fun setObjectValue(key: String, `object`: Any) {

        val gson = Gson()
        val json = gson.toJson(`object`)
        mSharedPreferencesEditor!!.putString(key, json)
        mSharedPreferencesEditor!!.commit()
    }

    fun getObject(key: String): Any {
        val gson = Gson()
        val json = mSharedPreferences.getString(key, "")
        return gson.fromJson(json, Any::class.java)

    }

    fun saveArrayList(list: ArrayList<String>, key: String) {
        val gson = Gson()
        val json = gson.toJson(list)
        mSharedPreferencesEditor!!.putString(key, json)
        mSharedPreferencesEditor!!.apply()     // This line is IMPORTANT !!!
    }

    fun getArrayList(key: String): ArrayList<String>? {
        val gson = Gson()
        val json = mSharedPreferences.getString(key, null)
        val type = object : TypeToken<ArrayList<String>>() {

        }.type
        return gson.fromJson<ArrayList<String>>(json, type)
    }

}

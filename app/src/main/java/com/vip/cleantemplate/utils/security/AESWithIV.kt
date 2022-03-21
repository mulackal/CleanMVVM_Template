package com.vip.cleantemplate.utils.security

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESWithIV {

    fun encrypt(value: String, KEY: String, IV: String): String? {
        try {
            val iv = IvParameterSpec(IV.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(KEY.toByteArray(charset("UTF-8")), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
            val encrypted = cipher.doFinal(value.toByteArray())
            return Base64.encodeToString(encrypted, 1)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun decrypt(encrypted: String?, KEY: String, IV: String): String? {
        try {
            val iv = IvParameterSpec(IV.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(KEY.toByteArray(charset("UTF-8")), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
            val original = cipher.doFinal(Base64.decode(encrypted, 1))
            return String(original)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    /** this set use in activity or need place**/
  /*  var keys = getAlphaNumericString(16)
    var ivs = getAlphaNumericString(16)
    AESWithIV.encrypt(jsonHead,keys,ivs)

    // function to generate a random string of length n
    fun getAlphaNumericString(n: Int): String {
        // chose a Character random from this String
        val AlphaNumericString = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz")
        // create StringBuffer size of AlphaNumericString
        val sb = StringBuilder(n)
        for (i in 0 until n) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            val index = (AlphaNumericString.length * Math.random()).toInt()
            // add Character one by one in end of sb
            sb.append(AlphaNumericString[index])
        }
        return sb.toString()
    }*/
}
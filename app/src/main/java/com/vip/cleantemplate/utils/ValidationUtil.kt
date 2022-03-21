package com.vip.cleantemplate.utils

import android.content.Context
import android.widget.EditText

import java.util.regex.Pattern


/**
 * Created by vipin on 7/3/17.
 */

object ValidationUtil {


    fun validateUserEmail(editText: String): Boolean {


        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        return if (editText.trim { it <= ' ' }.isEmpty() || editText.isEmpty()) {
             false
        } else editText.matches(emailPattern.toRegex())
    }

    fun validatePassword(editText: EditText): Boolean {

        val passwrd = editText.text.toString().trim { it <= ' ' }

        val letter = Pattern.compile("[a-zA-z]")
        val digit = Pattern.compile("[0-9]")
        val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")

        val hasLetter = letter.matcher(passwrd)
        val hasDigit = digit.matcher(passwrd)
        val hasSpecial = special.matcher(passwrd)

        return if (passwrd.contains(" ") || passwrd.isEmpty() || passwrd.length < 9 || passwrd.length >= 15) {
            false
        } else if (!hasLetter.find()) {
            false
        } else if (!hasDigit.find()) {
            false
        } else hasSpecial.find()
    }

    fun validateUserText(username: String): Boolean {

        return !(username.isEmpty() || username.trim { it <= ' ' }.isEmpty())
    }


    fun validateMobileNumber(mobile: String): Boolean {

        return !(mobile.contains(" ") || mobile.isEmpty() || mobile.trim { it <= ' ' }.length < 9 || mobile.trim { it <= ' ' }.length > 11)
    }

    fun inputValidation(user: EditText, paswrd: EditText): Boolean {
        var value = false
        if (validateUserText("")) {
            if (validatePassword(paswrd)) {
                value = true
            } else {
                paswrd.error = "Please provide the password."
                value = false
            }
        } else {
            user.error = "Please provide a valid email."
            value = false
        }
        return value
    }

}
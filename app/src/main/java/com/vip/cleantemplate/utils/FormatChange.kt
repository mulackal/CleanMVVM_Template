package com.vip.cleantemplate.utils

import android.util.Log
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by vipin
 */
object FormatChange {
    
    @JvmStatic
    fun getdate(mydate: String?): String {
        if (mydate != null && mydate.trim { it <= ' ' }.isNotEmpty()) {
            Log.e("date", "" + mydate)
            var myDate: Date? = null
            // DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            try {
                myDate = format.parse(mydate)
            } catch (e1: ParseException) {
                e1.printStackTrace()
            }
            val timeFormat =
                SimpleDateFormat("dd/MM/yyyy  hh:mm a", Locale.getDefault())
            val finalDate = timeFormat.format(myDate)
            Log.e("date", "converted:$mydate")
            return finalDate.toString()
        }
        return "No date"
    }

    @JvmStatic
    fun getStingAddEMI(data: String): String {
        return "Amount: $data"
    }

    @JvmStatic
    fun getStingDoller(data: String): String {
        return "$$data"
    }

    @JvmStatic
    fun getAmount(data1: String?, data2: String?): String {
        var a1 = 0.0
        var a2 = 0.0
        if (data1 != null) a1 = java.lang.Double.valueOf(data1)
        if (data2 != null) a2 = java.lang.Double.valueOf(data2)
        return (a1 - a2).toString()
    }
}
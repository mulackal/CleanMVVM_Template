package com.vip.cleantemplate.utils

import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.text.Html
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DeviceSetupClass {

    // GET CURRENT TIME CURRESPONDING UTC TIME..
    val currentUTC: String
        get() {
            val time = Calendar.getInstance().time
            val outputFmt =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            outputFmt.timeZone = TimeZone.getTimeZone("UTC")
            return outputFmt.format(time)
        }

    companion object {
        /**
         * this is get the device token of the app
         * every device have a unique device id
         * @param context
         * @return
         */
        fun getDeviceId(context: Context): String {
            return Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

        /***
         * check the device is MI or not if it is MI divert to the auto start method
         * to start auto start
         * only then we can run background tasks
         * @param context
         */
        fun checkIsItMI(context: Context?): Boolean {
            val manufacturer = "xiaomi"
            return manufacturer.equals(Build.MANUFACTURER, ignoreCase = true)
        }

        /***
         * this method will direct the acitivity to the auto start method
         * @param context
         */
        fun setAutoStart(context: Context): Boolean {
            val intent = Intent()
            intent.component = ComponentName(
                "com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity"
            )
            context.startActivity(intent)
            return true
        }

        /***
         * this method is used to hide the keyboard which doesn't hide after enter information
         * in the textview
         * @param activity
         */
        fun setKeyboradHide(activity: Activity) {
            try {
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            } catch (E: Exception) {
                E.printStackTrace()
            }
        }

        fun CompressImageSize(bitmap: Bitmap): Bitmap? {
            // Initialize a new ByteArrayStream
            val stream = ByteArrayOutputStream()

            // Compress the bitmap with JPEG format and quality 50%
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()
                return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
        //TODO here need the code to set image to the recycler view
        /***
         * this method will return the gps is or or not status
         * @param context
         * @return
         */
        fun getGpsStatus(context: Context): Boolean {
            val manager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        /****
         * This used to take current system date
         */
        fun CurrentDatePick(): HashMap<String, String> {
            val userdate =
                HashMap<String, String>()
            val now = Date()
            val nowAsString = SimpleDateFormat("dd/MM/yyyy").format(now)
            val nowAsStringdefualt =
                SimpleDateFormat("yyyy/MM/dd").format(now)
            userdate["currentdate"] = nowAsString
            userdate["currentdate_defualt"] = nowAsStringdefualt
            return userdate
        }
        /****
         * This used to show calender view and get selected date
         */
        /****
         * This used to show calender view and get selected date
         */
        /* public static void DatepickerShow(Context context, final String date_txt,
                                      final OnDatePickerCallback mOnDatePickerCallback) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        final StringBuilder mydate = new StringBuilder();
        final StringBuilder mydateorder = new StringBuilder();

        final boolean[] dateval = {false};

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        mydate.setLength(0);
                        mydateorder.setLength(0);

                        Log.e("to pick null", ":" + mydate + "pass : " + mydateorder);

                        mydate.append(dayOfMonth).append("/").append(monthOfYear + 1).append("/")
                                .append(year);

                        mydateorder.append(year).append("/").append(monthOfYear + 1).append("/")
                                .append(dayOfMonth);

                        if (date_txt.equals("todate") && !dateval[0]){
                            mOnDatePickerCallback.onDatePickerCallbackComplteListener(mydate, true, mydateorder);
                            dateval[0] = true;
                        Log.e("to pick", "t:" + mydate + "pass : " + mydateorder);
                    }
                      else if(date_txt.equals("fromdate") && !dateval[0]){

                            mOnDatePickerCallback.onDatePickerCallbackComplteListener(mydate, false, mydateorder);
                            Log.e("to pick","f:"+mydate+"pass : "+mydateorder);
                            dateval[0] = true;
                        }
                        else{
                            Log.e("null pick",":");}

                    }
                }, mYear, mMonth, mDay);
      //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }*/
        fun getdate(mydate: String?): String {
            try {
                var myDate: Date? = null
                val format: DateFormat =
                    SimpleDateFormat("yyyy-MM-dd hh:mm:ss") // 2019-04-01 22:16:36
                try {
                    myDate = format.parse(mydate)
                } catch (e1: ParseException) {
                    e1.printStackTrace()
                }
                val timeFormat =
                    SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
                val finalDate = timeFormat.format(myDate)
                return finalDate.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }

        /****
         * This used to show calender view and get selected date
         */
        fun getDynamiDatepickerShow(
            context: Context?,
            date_txt: TextView
        ) {
            val c = Calendar.getInstance()
            val mYear = c[Calendar.YEAR]
            val mMonth = c[Calendar.MONTH]
            val mDay = c[Calendar.DAY_OF_MONTH]
            val mydate = StringBuilder()
            val datePickerDialog = DatePickerDialog(
                context!!,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    mydate.setLength(0)
                    mydate.append(dayOfMonth).append("/").append(monthOfYear + 1).append("/")
                        .append(year)
                    try {
                        date_txt.text = mydate
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, mYear, mMonth, mDay
            )
            // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show()
        }

        fun DateWithMonthShow(): String? {

//this 2 line use to get the current system date
            val now = Date()
            val nowAsString = SimpleDateFormat("dd/MM/yyyy").format(now)
            Log.e("system date : ", "" + nowAsString)
            val dfYYYYMMDD: DateFormat =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dfDDMMMYYYY: DateFormat =
                SimpleDateFormat("dd MMM,yyyy", Locale.getDefault())
            var date: String? = null
            try {
                date = dfDDMMMYYYY.format(dfYYYYMMDD.parse(nowAsString))
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return date
        }

        fun DateWithMonthInputeddateBase(datee: String): String? {

//this 2 line use to get the current system date
/*        Date now = new Date();
          String nowAsString = new SimpleDateFormat("dd/MM/yyyy").format(now);*/
            Log.e("input date : ", "" + datee)
            val dfYYYYMMDD: DateFormat =
                SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
            val dfDDMMMYYYY: DateFormat =
                SimpleDateFormat("dd MMM , yyyy  hh:mm aa", Locale.getDefault())
            var date1: String? = null
            try {
                date1 = dfDDMMMYYYY.format(dfYYYYMMDD.parse(datee))
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return date1
        }


        /****
         * This used to convert the UTC time to local date time
         */
        fun getDateInUTCtoLocak(OurDate: String?, from: String): String? {
            var OurDate = OurDate
            try {
                val formatter =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                formatter.timeZone = TimeZone.getTimeZone("UTC")
                val value = formatter.parse(OurDate)
                if (from == "penalty") {
                    val dateFormatter =
                        SimpleDateFormat("dd/MM/yyyy") //this format changeable
                    dateFormatter.timeZone = TimeZone.getDefault()
                    OurDate = dateFormatter.format(value)
                } else {
                    val dateFormatter =
                        SimpleDateFormat("dd/MM/yyyy  hh:mm a") //this format changeable
                    dateFormatter.timeZone = TimeZone.getDefault()
                    OurDate = dateFormatter.format(value)
                }
                Log.e("OurDate", OurDate)
            } catch (e: Exception) {
                OurDate = ""
            }
            return OurDate
        }

        /****
         * This used to take current system date and time
         */
        fun CurrentDateTimePick(): String {
            val c = Calendar.getInstance()
            // System.out.println("Current time => " + c.getTime());
            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return df.format(c.time)
        }

        fun getInputdateBaseDate(sJsonDate: String?): String? { //2018-04-02 09:32:18.42942
            val formatter =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var value: Date? = null
            var datevalue: String? = null
            try {
                value = formatter.parse(sJsonDate) //"03/05/2018 - 05:30 PM"
                val dateFormatter =
                    SimpleDateFormat("dd/MM/yyyy - hh:mm a") //this format changeable
                dateFormatter.timeZone = TimeZone.getDefault()
                datevalue = dateFormatter.format(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return datevalue
        }

        fun getDayMonthBaseDate(sJsonDate: String?): String? {
            val formatter =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var date: Date? = null
            var mydate: String? = null
            try {
                date = formatter.parse(sJsonDate) as Date
                val formattern: DateFormat =
                    SimpleDateFormat("EEEE MMMM dd") // MONDAY MARCH 14 FORMAT
                mydate = formattern.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            println(date)
            return mydate
        }

        fun getDayMonthYear(stringDate: String?): String? {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            var date: Date? = null
            var mydate: String? = null
            try {
                date = formatter.parse(stringDate) as Date
                val formattern: DateFormat =
                    SimpleDateFormat("dd/MM/yyyy") // 08/02/2018
                mydate = formattern.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            println(date)
            return mydate
        }

        fun getStringJustify(string: String): String {
            return Html
                .fromHtml(
                    "<html><head><body style=\"text-align:justify;color:#222222; \">"
                            + string
                            + "</body></head></html>"
                ).toString()
        }

        fun isAppIsInBackground(context: Context): Boolean {
            var isInBackground = true
            try {
                val am =
                    context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                    val runningProcesses =
                        am.runningAppProcesses
                    for (processInfo in runningProcesses) {
                        if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            for (activeProcess in processInfo.pkgList) {
                                if (activeProcess == context.packageName) {
                                    isInBackground = false
                                }
                            }
                        }
                    }
                } else {
                    val taskInfo = am.getRunningTasks(1)
                    val componentInfo = taskInfo[0].topActivity
                    if (componentInfo!!.packageName == context.packageName) {
                        isInBackground = false
                    }
                }
            } catch (e: Exception) {
            }
            return isInBackground
        }
    }
}
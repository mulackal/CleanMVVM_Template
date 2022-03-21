package com.vip.cleantemplate.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle

import android.text.Html
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.snackbar.Snackbar
import com.vip.cleantemplate.BuildConfig
import com.vip.cleantemplate.R
import com.vip.cleantemplate.data.preferences.SharedPreferenceUtils
import org.koin.android.ext.android.inject


abstract class BaseActivity : AppCompatActivity() {

    val preferences: SharedPreferenceUtils by inject()

    // This is for showing the progress
    private  var mLoadingDialog: Dialog? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun showSnackBar(snackBarView: View, message: String) {
        val sandbar: Snackbar = Snackbar.make(snackBarView, message, Snackbar.LENGTH_SHORT)
        sandbar.show()
    }


    fun debugLogD(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    fun debugLogE(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }

    fun showLoadingDialog(context: Context) {
        if (mLoadingDialog == null) {
            callLoader(context)
        }else{
            hideLoadingDialog()
            callLoader(context)
        }
    }

    private fun callLoader(context: Context) {
        mLoadingDialog = Dialog(context)
        mLoadingDialog!!.setCancelable(false)
        mLoadingDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mLoadingDialog!!.setContentView(R.layout.dialogue_loading_layout)
        mLoadingDialog!!.window!!.setBackgroundDrawableResource(
            android.R.color.transparent)

        mLoadingDialog!!.show()
    }

    // This is for hiding the progress
    fun hideLoadingDialog() {
        if (mLoadingDialog != null)
            if (mLoadingDialog!!.isShowing) {
                mLoadingDialog!!.cancel()
                mLoadingDialog!!.window!!.closeAllPanels()
            }
    }



    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun showActivityCloseDialoge(context: Context,msg:String) {
        val alertDialogBuilder = AlertDialog.Builder(ContextThemeWrapper(context,
            R.style.Theme_AppCompat
        ))
        // set title
        alertDialogBuilder.setTitle(Html.fromHtml("<font color='#ffa000'>Warning</font>"))
        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel() })
        // create alert dialog
        val alertDialog = alertDialogBuilder.create()
        // show it
        alertDialog.show()
    }


    open fun hideKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}

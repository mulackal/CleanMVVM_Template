package com.vip.cleantemplate.base


import android.app.Dialog
import android.content.Context

import android.view.View
import android.view.Window
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.vip.cleantemplate.R
import com.vip.cleantemplate.data.preferences.SharedPreferenceUtils
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {

    val preferences: SharedPreferenceUtils by inject()

    // This is for showing the progress
    var mLoadingDialog: Dialog? = null

    fun showLoadingDialog(context: Context) {
        if (mLoadingDialog == null) {
            callLoader(context)
        }else{
            hideaLoadingDialog()
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
    fun hideaLoadingDialog() {
        if (mLoadingDialog != null)
            if (mLoadingDialog!!.isShowing) {
                mLoadingDialog!!.cancel()
                mLoadingDialog!!.window!!.closeAllPanels()
            }
    }


    fun startFragment(frame: Int, fragment: Fragment, tag: String) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(frame, fragment, tag)
        fragmentTransaction.commitAllowingStateLoss()
    }

}

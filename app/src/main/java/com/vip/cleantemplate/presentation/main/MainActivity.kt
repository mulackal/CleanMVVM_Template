package com.vip.cleantemplate.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduvy.demo.utils.extentions.showToast
import com.vip.cleantemplate.R
import com.vip.cleantemplate.base.BaseActivity
import com.vip.cleantemplate.common.Status
import com.vip.cleantemplate.data.preferences.SharedPreferenceValue
import com.vip.cleantemplate.domain.model.Player
import com.vip.cleantemplate.presentation.paging.PagingActivity
import kotlinx.android.synthetic.main.activity_main.*

import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity(), OnClickAdapterListener {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        setupObserver()

        preferences.setValue(SharedPreferenceValue.IS_LOGGED, true)
        debugLogE("Username:", preferences.getStringValue(SharedPreferenceValue.USER_NAME, "")!!)

    }

    private fun setupUI() {

        refreshList.setOnClickListener {
            mainViewModel.fetchUsers()
        }

        mainAdapter = MainAdapter(arrayListOf(),this)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = mainAdapter
            }
    }

    private fun setupObserver() {

        with(mainViewModel) {

            showMessage.observe(this@MainActivity, Observer {
                showToast(it)
            })

            isProgressLoading.observe(this@MainActivity, Observer { isVisible ->
                if (isVisible) showLoadingDialog(this@MainActivity)
                else hideLoadingDialog()
            })

            users.observe(this@MainActivity, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        hideLoadingDialog()
                        nodata.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        it.data?.let {
                                users -> renderList(users)
                        }
                    }
                    Status.LOADING -> {
                        showLoadingDialog(this@MainActivity)
                        nodata.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        hideLoadingDialog()
                        nodata.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        showToast(R.string.apierror)
                    }
                }
            })

        }

    }

    private fun renderList(users: List<Player>) {
        mainAdapter.setItems(users)
    }


    override fun clickedAdapterItem(name: String) {
        showToast(name)
        Intent(this@MainActivity, PagingActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(it)
        }
    }
}

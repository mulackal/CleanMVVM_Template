package com.vip.cleantemplate.presentation.paging

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduvy.demo.utils.extentions.showToast
import com.vip.cleantemplate.R
import com.vip.cleantemplate.base.BaseActivity
import com.vip.cleantemplate.common.Status
import com.vip.cleantemplate.utils.Variables
import kotlinx.android.synthetic.main.activity_paging.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import org.koin.android.viewmodel.ext.android.viewModel

class PagingActivity : BaseActivity() {

    private val viewModel: PagingViewModel by viewModel()
    private lateinit var adapter: PagingAdapter

    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)
        setupUI()
        setupObservers()
        setupPaging()

    }

    private fun setupObservers() {
        if (Variables.isNetworkConnected) {
            viewModel.loadingState.observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        hideLoadingDialog()
                        showToast("Data fetched successfully.!")
                    }
                    Status.LOADING -> {
                        showLoadingDialog(this)
                        showToast("Loading...")
                    }
                    Status.ERROR -> {
                        hideLoadingDialog()
                        //Handle Error
                        showToast(R.string.apierror)
                    }
                }
            })
        } else showToast("No internet connection")

    }

    private fun setupPaging() {

        /* searchJob?.cancel()
         searchJob = lifecycleScope.launch {
             viewModel.pagingData.observe(this@PagingActivity, {
                 adapter.submitData(this@PagingActivity.lifecycle, it)
             })
         }*/

        /*
        Same thing using flow
        * */
        if (Variables.isNetworkConnected) {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                viewModel.players
                    .collectLatest {
                        adapter.submitData(it)
                    }
            }
        } else showToast("No internet connection")
    }

    private fun setupUI() {
        rv_paging.layoutManager = LinearLayoutManager(this)
        adapter = PagingAdapter()
        rv_paging.addItemDecoration(
            DividerItemDecoration(
                rv_paging.context,
                (rv_paging.layoutManager as LinearLayoutManager).orientation
            )
        )
        rv_paging.adapter = adapter.withLoadStateFooter(
            footer = PlayersLoadingStateAdapter { retry() }
        )

        adapter.addLoadStateListener { loadState ->

            //  loadState.mediator?.refresh
            if (loadState.refresh is LoadState.Loading) {
                if (adapter.snapshot().isEmpty()) {
                    showLoadingDialog(this)
                    error_txt.isVisible = true
                }
            } else {

                hideLoadingDialog()
                error_txt.isVisible = false

                val error = when {
                    loadState.mediator?.prepend is LoadState.Error -> loadState.mediator?.prepend as LoadState.Error
                    loadState.mediator?.append is LoadState.Error -> loadState.mediator?.append as LoadState.Error
                    loadState.mediator?.refresh is LoadState.Error -> loadState.mediator?.refresh as LoadState.Error
                    loadState.source.append is LoadState.Error -> loadState.source.append as LoadState.Error
                    loadState.source.prepend is LoadState.Error -> loadState.source.prepend as LoadState.Error
                    loadState.source.refresh is LoadState.Error -> loadState.source.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    if (adapter.snapshot().isEmpty()) {
                        error_txt.isVisible = true
                        error_txt.text = it.error.localizedMessage
                    }

                }

            }
        }
    }

    private fun retry() {
        adapter.retry()
    }

}
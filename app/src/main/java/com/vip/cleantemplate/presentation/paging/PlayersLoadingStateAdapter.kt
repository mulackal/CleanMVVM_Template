package com.vip.cleantemplate.presentation.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vip.cleantemplate.R
import kotlinx.android.synthetic.main.network_state_item.view.*


class PlayersLoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<PlayersLoadingStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        val progress = holder.itemView.progress_bar_item
        val retryBtn = holder.itemView.rety_btn
        val txtErrorMessage = holder.itemView.error_msg_item

        if (loadState is LoadState.Loading) {
            progress.isVisible = true
            txtErrorMessage.isVisible = false
            retryBtn.isVisible = false

        } else {
            progress.isVisible = false
        }

        if (loadState is LoadState.Error) {
            txtErrorMessage.isVisible = true
            retryBtn.isVisible = true
            txtErrorMessage.text = loadState.error.localizedMessage
        }


        retryBtn.setOnClickListener {
            retry.invoke()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false)
        )
    }

    inner class LoadStateViewHolder(val view: View) :
        RecyclerView.ViewHolder(view)
}
package com.vip.cleantemplate.presentation.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vip.cleantemplate.R
import com.vip.cleantemplate.domain.model.Player
import kotlinx.android.synthetic.main.user_item.view.*

class PagingAdapter : PagingDataAdapter<Player, PagingAdapter.MainHolder>(DataComparator) {

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder =
        MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_item, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.itemView.tv_name.text = getItem(position)?.first_name
        holder.itemView.tv_email.text = getItem(position)?.last_name

        Glide.with(holder.itemView.context)
            .load("https://bellard.org/bpg/lena30.jpg")
            .centerCrop()
            .into(holder.itemView.iv_user)
    }

    object DataComparator : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }

    }
}
package com.vip.cleantemplate.presentation.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vip.cleantemplate.R
import com.vip.cleantemplate.domain.model.Player
import com.vip.cleantemplate.utils.diffUtil.PlayerDifferUtil
import kotlinx.android.synthetic.main.item_layout.view.*


import com.bumptech.glide.request.RequestOptions




class MainAdapter(
    private val users: ArrayList<Player>,
    private val mListener: OnClickAdapterListener
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    fun setItems(mData: List<Player>) {
        val diffCallback= PlayerDifferUtil(this.users,mData)
        val diffResult= DiffUtil.calculateDiff(diffCallback)
        this.users.clear()
        this.users.addAll(mData)
        diffResult.dispatchUpdatesTo(this)
    }


   inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: Player) {
            itemView.textViewUserName.text = user.first_name
            itemView.textViewUserEmail.text = user.last_name

            Log.e("imageViewAvatar","------ ${user.id}")

            val requestOptions = RequestOptions()
                requestOptions
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                    .fitCenter()
                    .circleCrop()

            Glide.with(itemView.imageViewAvatar.context)
                .load("https://www.pngkey.com/png/detail/114-1149878_setting-user-avatar-in-specific-size-without-breaking.png")
                .apply(requestOptions)
                .into(itemView.imageViewAvatar)

            itemView.setOnClickListener {
                mListener.clickedAdapterItem(user.last_name)
              // Toast.makeText(itemView.imageViewAvatar.context,"Name: ${user.last_name}",Toast.LENGTH_SHORT).show()
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_layout, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])


}
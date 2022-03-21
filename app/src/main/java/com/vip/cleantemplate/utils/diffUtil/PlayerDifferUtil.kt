package com.vip.cleantemplate.utils.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.vip.cleantemplate.domain.model.Player

class PlayerDifferUtil(
    private val oldList: List<Player>,
    private val newList: List<Player>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }


}
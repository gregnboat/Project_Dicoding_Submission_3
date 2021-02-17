package com.greig.consumerapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greig.consumerapp.entity.GUData
import kotlinx.android.synthetic.main.item_row_list.view.*

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    private val listUser = ArrayList<GUData>()

    fun setData(items: ArrayList<GUData>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])

        val mContext = holder.itemView.context
        holder.itemView.setOnClickListener {
            val moveDetail = Intent(mContext, DetailUserActivity::class.java)
            moveDetail.putExtra(DetailUserActivity.EXTRA_USER, listUser[position])
            mContext.startActivity(moveDetail)
        }
    }

    override fun getItemCount(): Int = listUser.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: GUData ) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(img_item_photo)

                user_name_desc_list.text = user.login
                url.text = user.url
            }
        }
    }
}
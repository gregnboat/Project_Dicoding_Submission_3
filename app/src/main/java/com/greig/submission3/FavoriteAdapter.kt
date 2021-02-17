package com.greig.submission3

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greig.submission3.entity.GUData
import de.hdodenhof.circleimageview.CircleImageView

class FavoriteAdapter(private val context: Context?, private  val itemUser : List<GUData>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_item_row_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemUser[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, itemUser[position])
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemUser.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val favUserAvatar = view.findViewById<CircleImageView>(R.id.fav_img_item_photo)
        private val favUser = view.findViewById<TextView>(R.id.fav_user_name_desc_list)
        private val favUrl = view.findViewById<TextView>(R.id.fav_url)

        fun bind(user: GUData) {
            Glide.with(itemView.context)
                .load(user.avatar)
                .into(favUserAvatar)
            favUser.text = user.login
            favUrl.text = user.url
        }
    }

}
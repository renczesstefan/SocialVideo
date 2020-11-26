package com.social.socialvideo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.social.socialvideo.R
import com.social.socialvideo.domain.UserPost

/**
 * Adapter pre recycle view pre posts
 * */
class UserPostsAdapter : RecyclerView.Adapter<UserPostsAdapter.ViewHolder>() {

    var data: List<UserPost> = emptyList()
        set(value) {
            field = value
            //Potrebujeme notifikovat ze sa data zmenili, co zapricini ze sa invalidatnu itemy
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.user_post, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.userName.text = item.username
        holder.uploadDate.text = item.created
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val uploadDate: TextView = itemView.findViewById(R.id.upload_date)
        val profilePhoto: ImageView = itemView.findViewById(R.id.profile_photo)
    }
}
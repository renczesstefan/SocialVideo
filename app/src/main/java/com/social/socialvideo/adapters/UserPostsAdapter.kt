package com.social.socialvideo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.social.socialvideo.R
import com.social.socialvideo.domain.UserPost

class UserPostsAdapter : RecyclerView.Adapter<UserPostsAdapter.ViewHolder>() {

    var data: List<UserPost> = emptyList()
        set(value) {
            field = value

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
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
        holder.userName.text = item.surname
        holder.uploadDate.text = "12.3.1996"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val uploadDate: TextView = itemView.findViewById(R.id.upload_date)
        val profilePhoto: ImageView = itemView.findViewById(R.id.profile_photo)
    }
}
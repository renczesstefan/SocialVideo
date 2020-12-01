package com.social.socialvideo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.MimeTypes
import com.social.socialvideo.R
import com.social.socialvideo.domain.UserPost


/**
 * Adapter pre recycle view pre posts
 * */
class UserPostsAdapter(private val context: Context) :
    RecyclerView.Adapter<UserPostsAdapter.ViewHolder>() {

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

        // Glide na renderovanie profilovej fotky
        if(item.profile != "") {
            Glide.with(context)
                .load("http://api.mcomputing.eu/mobv/uploads/" + item.profile)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.profilePhoto)
        }else{
            holder.profilePhoto.setBackgroundResource(R.drawable.defaut_user_profile_picture)
        }

        // Exoplayer na prehravanie videi
        val player: SimpleExoPlayer = SimpleExoPlayer.Builder(context)
            .build()
        holder.player.player = player
        val mediaItem: MediaItem =
            MediaItem.Builder()
                .setUri("http://api.mcomputing.eu/mobv/uploads/" + item.videourl)
                .setMimeType(MimeTypes.BASE_TYPE_VIDEO)
                .build()

        player.setMediaItem(mediaItem)
        player.playWhenReady = true
        player.seekTo(0, 0)
        player.prepare()
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.player.player?.playWhenReady = true
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.player.player?.playWhenReady = false
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val uploadDate: TextView = itemView.findViewById(R.id.upload_date)
        val profilePhoto: ImageView = itemView.findViewById(R.id.profile_photo)
        val player: PlayerView = itemView.findViewById(R.id.video_view)
    }

    class VideoStateHolder {

    }

}


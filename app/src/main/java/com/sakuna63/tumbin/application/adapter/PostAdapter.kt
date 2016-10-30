package com.sakuna63.tumbin.application.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.data.model.AltSize
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.databinding.ListItemPostPhotoBinding

class PostAdapter(private val columns: Int, private var posts: List<Post>)
: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //        if (viewType == VIEW_TYPE_PHOTO) {
        //            return new PhotoVH(inflater.inflate(R.layout.list_item_post_photo, parent, false));
        //        }
        // TODO: 2016/07/31 support other view type
        return PhotoVH(inflater.inflate(R.layout.list_item_post_photo, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        // TODO: 2016/07/31 support other holder
        if (holder is PhotoVH) {
            holder.binding.post = PhotoPostViewModel(item)
            holder.binding.index = position
        }

        holder.itemView.setOnClickListener {
            listener?.onPostClick(holder.adapterPosition)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        //        if (item.getType().equals(Post.TYPE_PHOTO)) {
        //            return VIEW_TYPE_PHOTO;
        //        }
        // TODO: 2016/07/31 support other type
        return VIEW_TYPE_PHOTO
    }

    override fun getItemCount(): Int = posts.size

    fun getItem(position: Int): Post = posts[position]

    fun swapItems(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    fun getItemColumnSpan(position: Int): Int {
        val isProgressBarItem = position == RecyclerView.NO_POSITION
        return if (isProgressBarItem) columns else 1
    }

    interface Listener {
        fun onPostClick(position: Int)
    }

    internal inner class PhotoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ListItemPostPhotoBinding

        init {
            binding = ListItemPostPhotoBinding.bind(itemView)
        }
    }

    inner class PhotoPostViewModel(private val item: Post) {

        val thumbnailUrl: String
            get() = thumbnailPhoto.url

        val badgeTexts: List<String>
            get() {
                val texts = mutableListOf<String>()
                if (thumbnailPhoto.isGif()) {
                    texts.add("GIF")
                }
                val numOfPhoto = item.photos.size
                if (numOfPhoto > 1) {
                    texts.add("x" + numOfPhoto)
                }
                return texts
            }

        private val thumbnailPhoto: AltSize
            get() = item.photos[0].altSizes[0]
    }

    companion object {
        private val VIEW_TYPE_TEXT = 10
        private val VIEW_TYPE_QUOTE = 11
        private val VIEW_TYPE_LINK = 12
        private val VIEW_TYPE_ANSWER = 13
        private val VIEW_TYPE_VIDEO = 14
        private val VIEW_TYPE_AUDIO = 15
        private val VIEW_TYPE_PHOTO = 16
        private val VIEW_TYPE_CHAT = 17
    }
}


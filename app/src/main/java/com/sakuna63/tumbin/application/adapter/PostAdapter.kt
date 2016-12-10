package com.sakuna63.tumbin.application.adapter

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.misc.GlideImageGetter
import com.sakuna63.tumbin.application.util.PostUtils
import com.sakuna63.tumbin.data.model.AltSize
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.databinding.ListItemPostPhotoBinding
import com.sakuna63.tumbin.databinding.ListItemPostTextBinding

class PostAdapter(private val columns: Int, private var posts: List<Post>)
: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_PHOTO ->
                return PhotoVH.newInstance(parent)
            VIEW_TYPE_TEXT ->
                return TextVH.newInstance(parent)
            else -> throw IllegalArgumentException("Not supported yet")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is PhotoVH -> holder.bind(PhotoPostViewModel(item), position)
            is TextVH -> holder.bind(TextPostViewModel(item,
                    GlideImageGetter(holder.binding.textPostBody)))
        }

        holder.itemView.setOnClickListener {
            listener?.onPostClick(holder.adapterPosition)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        when (item.type) {
            Post.TYPE_PHOTO -> return VIEW_TYPE_PHOTO
            Post.TYPE_TEXT -> return VIEW_TYPE_TEXT
            else -> throw IllegalArgumentException("type: $item.type is not supported yet")
        }
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

    internal class PhotoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ListItemPostPhotoBinding

        init {
            binding = ListItemPostPhotoBinding.bind(itemView)
        }

        companion object {
            fun newInstance(parent: ViewGroup): PhotoVH {
                val inflater = LayoutInflater.from(parent.context)
                return PhotoVH(inflater.inflate(R.layout.list_item_post_photo, parent, false))
            }
        }

        fun bind(photoPost: PhotoPostViewModel, index: Int) {
            binding.post = photoPost
            binding.index = index
        }
    }

    internal class TextVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ListItemPostTextBinding

        init {
            binding = ListItemPostTextBinding.bind(itemView)
        }

        companion object {
            fun newInstance(parent: ViewGroup): TextVH {
                val inflater = LayoutInflater.from(parent.context)
                return TextVH(inflater.inflate(R.layout.list_item_post_text, parent, false))
            }
        }

        fun bind(textPost: TextPostViewModel) {
            binding.post = textPost
        }
    }

    class PhotoPostViewModel(item: Post) : PostViewModel(item, Post.TYPE_PHOTO) {

        private val thumbnailPhoto: AltSize = item.photos!![0].altSizes[0] // photo type post always some photos
        val thumbnailUrl: String = thumbnailPhoto.url
        val badgeTexts: List<String>

        init {
            val texts = mutableListOf<String>()
            if (thumbnailPhoto.isGif()) {
                texts.add("GIF")
            }
            val numOfPhoto = item.photos!!.size
            if (numOfPhoto > 1) {
                texts.add("x" + numOfPhoto)
            }
            badgeTexts = texts
        }

    }

    class TextPostViewModel(item: Post, imageGetter: Html.ImageGetter)
        : PostViewModel(item, Post.TYPE_TEXT) {
        val title = item.title
        val titleVisibility = if (title == null || title.isEmpty()) View.GONE else View.VISIBLE
        val body = PostUtils.getFormattedBody(item.body!!, item.format, imageGetter)
    }

    open class PostViewModel(item: Post, @Post.PostType type: String) {
        init {
            checkType(type, item.type)
        }
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

public fun checkType(@Post.PostType expected: String, @Post.PostType actual: String) {
    check(actual == expected, { "expected type is $expected but actual type is $actual" })
}

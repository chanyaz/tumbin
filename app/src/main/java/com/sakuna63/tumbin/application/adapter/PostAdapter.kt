package com.sakuna63.tumbin.application.adapter

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.misc.GlideImageGetter
import com.sakuna63.tumbin.data.model.AltSize
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.databinding.ListItemPostPhotoBinding
import com.sakuna63.tumbin.databinding.ListItemPostTextBinding
import com.sakuna63.tumbin.toHtml

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

    class PhotoPostViewModel(private val item: Post) {

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

    class TextPostViewModel(item: Post, imageGetter: Html.ImageGetter?) {

        val title = item.title
        val titleVisibility: Int = if (title.isEmpty()) View.GONE else View.VISIBLE
        val body: CharSequence = when (item.format) {
            Post.FORMAT_PLAIN -> item.body
            Post.FORMAT_HTML -> item.body.toHtml(imageGetter)
            Post.FORMAT_MARKDOWN -> item.body.toHtml(imageGetter)
            else -> throw IllegalArgumentException("Unknown post format: " + item.format)
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


package com.sakuna63.tumbin.application.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sakuna63.tumbin.R;
import com.sakuna63.tumbin.application.util.PostUtils;
import com.sakuna63.tumbin.data.model.AltSize;
import com.sakuna63.tumbin.data.model.Post;
import com.sakuna63.tumbin.databinding.ListItemPostPhotoBinding;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_TEXT = 10;
    private static final int VIEW_TYPE_QUOTE = 11;
    private static final int VIEW_TYPE_LINK = 12;
    private static final int VIEW_TYPE_ANSWER = 13;
    private static final int VIEW_TYPE_VIDEO = 14;
    private static final int VIEW_TYPE_AUDIO = 15;
    private static final int VIEW_TYPE_PHOTO = 16;
    private static final int VIEW_TYPE_CHAT = 17;

    private final int columns;
    private List<Post> posts;
    private Listener listener;

    public PostAdapter(int columns, List<Post> posts) {
        this.columns = columns;
        this.posts = posts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        if (viewType == VIEW_TYPE_PHOTO) {
//            return new PhotoVH(inflater.inflate(R.layout.list_item_post_photo, parent, false));
//        }
        // TODO: 2016/07/31 support other view type
        return new PhotoVH(inflater.inflate(R.layout.list_item_post_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Post item = getItem(position);

        // TODO: 2016/07/31 support other holder
        if (holder instanceof PhotoVH) {
            ((PhotoVH) holder).binding.setPost(new PhotoPostViewModel(item));
            ((PhotoVH) holder).binding.setIndex(position);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onPostClick(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        Post item = getItem(position);
//        if (item.getType().equals(Post.POST_TYPE_PHOTO)) {
//            return VIEW_TYPE_PHOTO;
//        }
        // TODO: 2016/07/31 support other type
        return VIEW_TYPE_PHOTO;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public Post getItem(int position) {
        return posts.get(position);
    }

    public void swapItems(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public int getItemColumnSpan(int position) {
        boolean isProgressBarItem = position == RecyclerView.NO_POSITION;
        return isProgressBarItem ? columns : 1;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onPostClick(int position);
    }

    class PhotoVH extends RecyclerView.ViewHolder {
        final ListItemPostPhotoBinding binding;

        public PhotoVH(View itemView) {
            super(itemView);
            binding = ListItemPostPhotoBinding.bind(itemView);
        }
    }

    public class PhotoPostViewModel {
        private final Post item;

        public PhotoPostViewModel(Post item) {
            this.item = item;
        }

        public String getThumbnailUrl() {
            return getThumbnailPhoto().url;
        }

        public List<String> getBadgeTexts() {
            List<String> texts = new ArrayList<>();
            if (PostUtils.isGifPhoto(getThumbnailPhoto())) {
                texts.add("GIF");
            }
            int numOfPhoto = item.photos.size();
            if (numOfPhoto > 1) {
                texts.add("x" + numOfPhoto);
            }
            return texts;
        }

        private AltSize getThumbnailPhoto() {
            return item.photos.get(0).altSizes.get(0);
        }
    }
}


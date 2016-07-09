package com.sakuna63.tumbin.application.contract.presenter.posts;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.sakuna63.tumbin.R;

public class PlaceHolderDrawable {
    public static Drawable newInstance(Context context, int index) {
        int[] colors =
                context.getResources().getIntArray(R.array.post_loading_placeholders);
        return new ColorDrawable(colors[index % colors.length]);
    }
}

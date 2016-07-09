/*
 * Copyright 2015 Google Inc.
 * Copyright 2016 sakuna63
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sakuna63.tumbin.application.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;

import com.sakuna63.tumbin.R;
import com.sakuna63.tumbin.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BadgedSquareImageView extends SquareImageView {
    private final int badgeGravity;
    private final int badgePadding;
    private boolean badgeBoundsSet = false;
    private List<Drawable> badges;
    private Collection<String> badgeLabels;

    public BadgedSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BadgedSquareImageView, 0, 0);
        badgeGravity = a.getInt(R.styleable.BadgedSquareImageView_badgeGravity, Gravity.END | Gravity
                .BOTTOM);
        badgePadding = a.getDimensionPixelSize(R.styleable.BadgedSquareImageView_badgePadding, 0);
        a.recycle();

    }

    public void setBadgeColor(@ColorInt int color) {
        if (CollectionUtils.isEmpty(badges)) {
            return;
        }

        for (Drawable badge : badges) {
            badge.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    public void setBadgeLabels(Collection<String> labels) {
        if (CollectionUtils.equals(badgeLabels, labels)) {
            return;
        }

        badges = new ArrayList<>(labels.size());
        for (String label : labels) {
            badges.add(new BadgeDrawable(getContext(), label));
        }
        badgeLabels = labels;
        badgeBoundsSet = false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (CollectionUtils.isEmpty(badges)) {
            return;
        }

        if (!badgeBoundsSet) {
            layoutBadge(badges);
        }
        for (Drawable badge : badges) {
            badge.draw(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!CollectionUtils.isEmpty(badges)) {
            layoutBadge(badges);
        }
    }

    private void layoutBadge(@NonNull List<Drawable> badges) {
        Rect preBounds = new Rect();
        for (int i = 0; i < badges.size(); i++) {
            Drawable badge = badges.get(i);
            Rect badgeBounds = badge.getBounds();
            int offsetX = Math.abs(preBounds.left - preBounds.right);
            Gravity.apply(badgeGravity,
                    badge.getIntrinsicWidth(),
                    badge.getIntrinsicHeight(),
                    new Rect(0, 0, getWidth(), getHeight()),
                    badgePadding * (i + 1) + offsetX,
                    badgePadding,
                    badgeBounds);
            badge.setBounds(badgeBounds);
        }
        badgeBoundsSet = true;
    }
}

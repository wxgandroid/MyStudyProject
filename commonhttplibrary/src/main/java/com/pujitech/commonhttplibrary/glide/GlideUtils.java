package com.pujitech.commonhttplibrary.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pujitech.commonhttplibrary.R;
import com.pujitech.commonhttplibrary.utils.BaseStringUtils;


/**
 * Created by WangXuguang on 2017/9/28.
 */

public class GlideUtils {


    public static void loadImage(Context context, final String url, ImageView imageView) {
        if (context == null) {
            throw new RuntimeException("context 不能为 null");
        }

        GlideApp.with(context)
                .load(url)
                .placeholder(R.drawable.icon_image_default)
                .into(imageView);
    }

    public static void loadImage(Context context, final Uri uri, ImageView imageView) {
        if (context == null) {
            throw new RuntimeException("context 不能为 null");
        }
        GlideApp.with(context)
                .load(uri)
                .placeholder(R.drawable.icon_image_default)
                .into(imageView);
    }

    /**
     * 带进度回调的加载图片
     */
    public static void loadImageWithProgress(Context context, final String url, ImageView imageView, ProgressManager.GlideProgressListener glideProgressListener) {
        if (context == null) {
            throw new RuntimeException("context不能为null");
        }

        //url为空
        if (BaseStringUtils.isEmpty(url)) {
            return;
        }

        //根据url添加监听到进度管理器中
        ProgressManager.addProgressListener(url, glideProgressListener);

        GlideApp.with(context)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ProgressManager.removeProgressListener(url);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ProgressManager.removeProgressListener(url);
                        return false;
                    }
                })
                .into(imageView);


    }


}

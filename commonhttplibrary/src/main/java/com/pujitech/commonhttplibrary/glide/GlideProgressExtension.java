package com.pujitech.commonhttplibrary.glide;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by WangXuguang on 2017/9/28.
 * <p>
 * Glide关于下载进度的扩展类
 */

@GlideExtension
public final class GlideProgressExtension {

    private GlideProgressExtension() {
    }


    @GlideOption
    public static void testProgress(RequestOptions options) {

    }


}

package com.pujitech.commonhttplibrary.glide;

import java.util.concurrent.ConcurrentHashMap;

import com.pujitech.commonhttplibrary.utils.BaseStringUtils;

/**
 * Created by WangXuguang on 2017/9/28.
 */

public class ProgressManager {

    public static ConcurrentHashMap<String, GlideProgressListener> mProgressMaps = new ConcurrentHashMap<>(); //线程安全的map，效率较高


    public static void addProgressListener(String url, GlideProgressListener glideProgressListener) {
        if (!mProgressMaps.containsKey(url)) {
            mProgressMaps.put(url, glideProgressListener);
        }
    }


    /**
     * 下载图片过程中的回调（更新下载进度）
     *
     * @param url
     * @param progress
     */
    public static void setGlideProgress(String url, int progress) {
        if (BaseStringUtils.isEmpty(url)) {
            return;
        }

        if (mProgressMaps.containsKey(url)) {
            //集合中包含当前的监听
            mProgressMaps.get(url).onProgress(progress);

        }
    }

    /**
     * 图片下载完成或失败，从map中删除当前listener
     *
     * @param url
     */
    public static void removeProgressListener(String url) {
        if (mProgressMaps.containsKey(url)) {
            mProgressMaps.remove(url);
        }
    }

    interface GlideProgressListener {
        void onProgress(int progress);
    }

}

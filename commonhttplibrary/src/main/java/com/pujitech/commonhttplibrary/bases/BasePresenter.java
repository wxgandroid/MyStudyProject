package com.pujitech.commonhttplibrary.bases;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by WangXuguang on 2017/10/18.
 */

public abstract class BasePresenter<T extends BaseView, V extends BaseModule> {

    public Context mContext;
    private WeakReference<T> reference;
    public T mView;
    public V mModule;

    protected String TAG = getClass().getSimpleName();

    public BasePresenter(Context context) {
        mContext = context;
    }

    public void attachView(T t) {
        if (reference == null) {
            reference = new WeakReference<T>(t);
            mView = reference.get();
            mModule = initModule();
        }
    }

    protected abstract V initModule();

}

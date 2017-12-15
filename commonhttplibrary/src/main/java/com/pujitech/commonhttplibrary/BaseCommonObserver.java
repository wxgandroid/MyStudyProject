package com.pujitech.merchant.network;

import com.pujitech.commonhttplibrary.RxLifeRecycle;
import com.pujitech.commonhttplibrary.utils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by WangXuguang on 2017/9/26.
 */

public class BaseCommonObserver<T extends Object> implements Observer<T> {

    public RxLifeRecycle lifeRecycle;
    public OnRequestDataListener onRequestDataListener;
    private Disposable mDisposable;
    private final String TAG = BaseCommonObserver.class.getSimpleName();

    /**
     * 注册到Observer中的对象，用来解除联网操作
     *
     * @param
     * @param onRequestDataListener
     */
    public BaseCommonObserver(RxLifeRecycle v, OnRequestDataListener onRequestDataListener) {
        if (v != null) {
            this.lifeRecycle = v;
            v.setRegisterObject(this);
        }
        this.onRequestDataListener = onRequestDataListener;
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(@NonNull T t) {
        LogUtils.i(TAG, "onNext()");
        if (onRequestDataListener != null) {
            onRequestDataListener.onSuccess(t);
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        LogUtils.i(TAG, "onError()");
        if (onRequestDataListener != null) {
            onRequestDataListener.onError(e.getMessage());
            onRequestDataListener.onCompleted();
        }

    }

    @Override
    public void onComplete() {
        LogUtils.i(TAG, "onComplete()");
        if (onRequestDataListener != null) {
            onRequestDataListener.onCompleted();
        }
    }

    /**
     * 取消网络请求
     */
    public void onDestroy() {
        LogUtils.i(TAG, "lifeRecycle onDestroy():" + lifeRecycle);
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public interface OnRequestDataListener<T> {

        void onSuccess(T t);

        void onError(String error);

        void onCompleted();         //Rxjava执行 onError以后依然会执行onComplete

    }

}

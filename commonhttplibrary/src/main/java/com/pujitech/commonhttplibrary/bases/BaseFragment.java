package com.pujitech.commonhttplibrary.bases;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pujitech.commonhttplibrary.RxLifeRecycle;
import com.pujitech.commonhttplibrary.utils.BaseDialogUtils;
import com.pujitech.commonhttplibrary.utils.CommonConstants;
import com.pujitech.merchant.network.BaseCommonObserver;

/**
 * Created by WangXuguang on 2017/10/18.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView, RxLifeRecycle {

    public View mRootView;
    public T mPresenter;
    public Context mContext;
    public Dialog mIosLoading;
    public Intent mIntent;
    private Toast mToast;

    protected final String TAG = getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * 根据传入的fragment类型，返回相应的fragment
     *
     * @param t
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws java.lang.InstantiationException
     */
    public static <T extends BaseFragment> T createFragment(Class<T> t) {
        T fragment = null;
        try {
            fragment = t.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        if (mRootView != null) {
            return mRootView;
        }
        mRootView = inflater.inflate(getLayoutId(), null);
        mPresenter = createPresenter(mContext);
        if (mPresenter != null) {
            attachView();
        }
        return mRootView;
    }

    protected abstract int getLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract void initData();


    /**
     * 给当前fragment设定相应的presenter
     *
     * @param context
     * @return
     */
    protected abstract T createPresenter(Context context);

    /**
     * 给presenter绑定view
     */
    protected abstract void attachView();


    /**
     * Toast 消息
     *
     * @param msg
     */
    @Override
    public void showToast(String msg) {
        if (msg == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 显示加载网络的进度条
     */
    @Override
    public void showProgressBar() {
        if (mIosLoading == null || !mIosLoading.isShowing()) {
            mIosLoading = BaseDialogUtils.showIosLoading(mContext, new BaseDialogUtils.OnKeyBackListener() {
                @Override
                public void onKeyBack() {

                }
            });
        }

    }

    /**
     * 隐藏加载网络的进度条
     */
    @Override
    public void dismissProgressBar() {
        if (mIosLoading != null) {
            mIosLoading.dismiss();
        }
    }

    /**
     * 管理联网操作的生命周期
     */
    @Override
    public void onDestroy() {
        for (BaseCommonObserver observer : mObservers) {
            if (observer != null) {
                observer.onDestroy();
            }
        }
        super.onDestroy();
    }


    @Override
    public void onStop() {
        if (mToast != null) {
            mToast.cancel();
        }
        super.onStop();
    }

    /**
     * 将联网的observer对象添加到集合中
     *
     * @param observer
     */
    @Override
    public void setRegisterObject(BaseCommonObserver observer) {
        mObservers.add(observer);
    }

    /**
     * 跳转到其他页面
     */
    public <T extends Activity> void jump2Activity(Class<T> tClass, Bundle bundle) {
        if (mIntent == null) {
            mIntent = new Intent();
        }
        mIntent.setClass(mContext, tClass);
        if (bundle != null) {
            mIntent.putExtra(CommonConstants.ACTIVITY_BUNDLE, bundle);
        }
        startActivity(mIntent);
    }

    /**
     * 跳转到其他页面
     */
    public <T extends Activity> void jump2Activity(Class<T> tClass) {
        jump2Activity(tClass, null);
    }

}

package com.pujitech.commonhttplibrary.bases;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.pujitech.commonhttplibrary.RxLifeRecycle;
import com.pujitech.commonhttplibrary.utils.BaseDialogUtils;
import com.pujitech.commonhttplibrary.utils.CommonConstants;
import com.pujitech.merchant.network.BaseCommonObserver;
import com.zhy.autolayout.AutoLayoutActivity;

public abstract class BaseActivity<T extends BasePresenter> extends AutoLayoutActivity implements BaseView, RxLifeRecycle {

    protected T mPresenter;
    private Dialog mIosLoading;
    protected Intent mIntent;
    private long mLastBackTime;
    private Toast mToast;

    public boolean mDoubleClickExit;

    private static final int STATUS_BAR_COLOR = Color.parseColor("#ff0000");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
            attachView();
        }

    }

    /**
     * presenter赋值
     *
     * @return
     */
    public abstract T createPresenter();


    /**
     * activity的入口方法
     */
    public abstract void attachView();


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
            mToast = Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT);
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
            mIosLoading = BaseDialogUtils.showIosLoading(this, new BaseDialogUtils.OnKeyBackListener() {
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
    public <T extends Activity> void jump2Activity(Class<T> tClass) {
        jump2Activity(tClass, null);
    }

    public <T extends Activity> void jump2Activity(Class<T> tClass, Bundle bundle) {
        if (mIntent == null) {
            mIntent = new Intent();
        }
        mIntent.setClass(this, tClass);
        if (bundle != null) {
            mIntent.putExtra(CommonConstants.ACTIVITY_BUNDLE, bundle);
        }
        startActivity(mIntent);
    }

    @Override
    protected void onStop() {
        if (mToast != null) {
            mToast.cancel();
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (mDoubleClickExit) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - mLastBackTime > 2000) {
                //两次返回间隔大于2s
                mLastBackTime = currentTime;
                showToast("再次点击退出应用");
                return;
            }

        }
        super.onBackPressed();
    }
}

package com.pujitech.commonhttplibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.pujitech.commonhttplibrary.R;
import com.pujitech.commonhttplibrary.weight.timepickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by WangXuguang on 2017/10/18.
 */

public class BaseDialogUtils {

    /**
     * 仿ios菊花效果的loading
     *
     * @param context
     */
    public static Dialog showIosLoading(Context context, final OnKeyBackListener onKeyBackListener) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (100 * density), (int) (100 * density));

        final Dialog dialog = new Dialog(context, R.style.Theme_NoTitleBarDialog);

        View view = View.inflate(context, R.layout.ios_loading_layout, null);
        dialog.setContentView(view, params);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    //点击了返回键
                    if (onKeyBackListener != null) {
                        onKeyBackListener.onKeyBack();
                        return true;
                    }
                }
                return false;
            }
        });
        dialog.show();
        return dialog;
    }


    /**
     * 显示仿ios的事件选择器
     */
    public static void showDatePickerView(Context context, final OnConfirmCancelListener onConfirmCancelListener) {
        TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {//选中事件回调
                String time = getTime(date2);
                if (onConfirmCancelListener != null) {
                    onConfirmCancelListener.onConfirm(time, date2);
                }
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(15)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.parseColor("#4a90e2"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#4a90e2"))//取消按钮文字颜色
//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                .setTitleText("")
                .setLineSpacingMultiplier(2.4F)
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    private static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }


    /**
     * 点击返回按钮的监听
     */
    public interface OnKeyBackListener {
        void onKeyBack();
    }

    public interface OnConfirmCancelListener {
        void onConfirm(String time, Date date);

        void onCancle();

    }

}

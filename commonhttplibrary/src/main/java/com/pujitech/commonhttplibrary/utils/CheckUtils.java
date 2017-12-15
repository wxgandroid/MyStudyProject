package com.pujitech.commonhttplibrary.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/7/17.
 */

public class CheckUtils {
    private static final String TAG = "CheckUtils";
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    /**
     * 手机号是否合法
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNum(String phoneNum) {
        String regExp = "^[1][3,4,5,7,8][0-9]{9}$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.matches();
    }

    /**
     * 密码是否合法
     *
     * @param password
     * @return
     */
    public static boolean isPasswordValid(String password) {
        LogUtils.d(TAG, "password length:" + password.length());
        if (password.length() < 8 || password.length() > 20) {
            return false;
        }
        return true;
    }

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) <= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}

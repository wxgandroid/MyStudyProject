package com.pujitech.commonhttplibrary.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//时间格式师列：	yyyy-MM-dd HH:mm:ss
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    public static Date date = null;

    public static DateFormat dateFormat = null;

    public static Calendar calendar = null;

    public static String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五",
            "周六"};

    public static Date parseDate(String dateTime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDate(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                dateFormat = new SimpleDateFormat(format);
                result = dateFormat.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static int getYear(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static String getDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String getTime(Date date) {
        return formatDate(date, "HH:mm:ss");
    }

    public static String getDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 判断是否同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        if (getDate(date1).equals("1970-01-01") || getDate(date1).equals(getDate(date2))) {
            return true;
        }
        return false;
    }

    /**
     * 时差是否在指定的范围内
     *
     * @param time1
     * @param time2
     * @param difference
     * @return
     */
    public static boolean innerTimeDifference(long time1, long time2, int difference) {
        int timeDiff = (int) ((time2 - time1) / 1000 / 60 / 60);
        return timeDiff < difference;
    }

    /**
     * 判断时间一是否大于时间二（不等与）
     *
     * @param str
     * @return
     */
    public static boolean isFirstLagre(String firstTime, String lastTime,
                                       String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = null;
        Date d2 = null;
        try {
            d = sdf.parse(firstTime);
            d2 = sdf.parse(lastTime);
            long startLong = d.getTime();
            long endLong = d2.getTime();
            return startLong > endLong ? true : false;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断当前时间是否比当前时间大
     *
     * @param str
     * @return
     */
    public static boolean isCurrentLagre(String anyTime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = sdf.parse(anyTime);
            long currentLong = System.currentTimeMillis();
            long endLong = d.getTime();
            return currentLong > endLong ? true : false;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public static Date addDate_Day(Date date, int day) {
        calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static Date addDate_Minute(Date date, int minute) {
        calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) minute) * 60 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    // 时差
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    public static String getMonthBegin(String strdate) {
        date = parseDate(strdate, "yyyy-MM-dd");
        return formatDate(date, "yyyy-MM") + "-01";
    }

    public static String getMonthEnd(String strdate) {
        date = parseDate(getMonthBegin(strdate), "yyyy-MM-dd");
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime(), "yyyy-MM-dd");
    }

    public static String getWeek(String pTime) {

        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));

        } catch (ParseException e) {

            e.printStackTrace();
        }

        Week = weekDays[c.get(Calendar.DAY_OF_WEEK) - 1];
        return Week;
    }

    public static String getWeek(Calendar c) {
        String Week = "";
        Week = weekDays[c.get(Calendar.DAY_OF_WEEK) - 1];
        return Week;
    }

    /**
     * 时间格式化
     *
     * @param time
     * @return
     */
    public static String formatTimeStr(String time) {
        if (time == null || (time != null && time.length() == 0)) {
            return "";
        }
        if (time.length() > 14) {
            return time;
        }
        Long lTime = Long.decode(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(lTime);
    }

    public static boolean isLargeCurrentTime(Date date) {
        String time = formatDate(date, "yyyyMMddHHmm");
        String currentTime = formatDate(new Date(System.currentTimeMillis()), "yyyyMMddHHmm");
        return Long.parseLong(time) >= Long.parseLong(currentTime);
    }
}

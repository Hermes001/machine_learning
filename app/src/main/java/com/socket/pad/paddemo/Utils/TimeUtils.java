package com.socket.pad.paddemo.Utils;

import java.util.Calendar;

public class TimeUtils {

    /**
     * 获取年
     * @return
     */
    public static int getYear(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.YEAR);
    }
    /**
     * 获取月
     * @return
     */
    public static int getMonth(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.MONTH)+1;
    }
    /**
     * 获取日
     * @return
     */
    public static int getDay(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.DATE);
    }
    /**
     * 获取时
     * @return
     */
    public static int getHour(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.HOUR);
    }
    /**
     * 获取分
     * @return
     */
    public static int getMinute(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.MINUTE);

    }

    /**
     * 获取当前时间的时间戳
     * @return
     */
    public static long getCurrentTimeMillis(){
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间
     */

    /*public static String getCurrentTime(){
        return getFormatedDateTime(System.currentTimeMillis());
    }*/
}

package com.example.recyclerdirection.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建人：
 * 创建时间： 2017/4/24
 * 内容描叙： 格式化时间
 * 修改人：
 * 修改时间：
 * 修改描叙：
 */

public class FormateDate {

    /**
     * 将时间戳转化为制定的格式
     *
     * @param formate
     * @param time
     * @return
     */
    public static String formateDate(String formate, long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
        return dateFormat.format(new Date(time * 1000));

    }

    /**
     * 根据格式获得对应的时间戳
     *
     * @param formate
     * @param time
     * @return
     */
    public static String getTime(String formate, String time) {
        String re_time = null;
        if (time != null && !"".equals(time)) {
            SimpleDateFormat sdf = new SimpleDateFormat(formate);
            Date d;
            try {
                d = sdf.parse(time);
                long l = d.getTime();
                String str = String.valueOf(l);
                re_time = str.substring(0, 10);
            } catch (ParseException e) {
                // TODO Auto-generated catch block e.printStackTrace();
            }
        }


        return re_time;
    }

    public static String timeDifference(long time) {
        String t = "";
        if (time < 60) {
            t = 60 + "秒";
        } else if (time > 60 && time < 3600) {
            t = time / 60 + "分钟";
        } else if (time >= 3600 && time < 3600 * 24) {
            t = time / 3600 + "小时";
        } else {
            t = time / 3600 / 24 + "天";
        }
        return t;
    }
}

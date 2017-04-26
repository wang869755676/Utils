package com.example.recyclerdirection.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 创建人：
 * 创建时间： 2017/4/24
 * 内容描叙：
 * 修改人：
 * 修改时间：
 * 修改描叙：
 */

public class ToastUtils {
    private static Toast toast;
    public static  void showToast(Context context, String msg){
        if(toast==null){
            toast= Toast.makeText(context,msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        toast.show();;
    }
}

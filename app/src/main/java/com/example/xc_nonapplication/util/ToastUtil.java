package com.example.xc_nonapplication.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/4/28 16:07
 */
public class ToastUtil {
    public static Toast mToast;
    public static void showMsg(Context context, String msg){
        if(mToast==null){
            mToast=Toast.makeText(context,msg,Toast.LENGTH_LONG);
        }else{
            mToast.setText(msg);
        }
        mToast.show();
    }
}

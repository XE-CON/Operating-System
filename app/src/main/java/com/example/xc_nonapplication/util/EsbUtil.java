package com.example.xc_nonapplication.util;

import android.os.Handler;

import com.example.xc_nonapplication.Vo.LoginInfoVo;
import com.example.xc_nonapplication.Vo.PhoneInfoVo;
import com.example.xc_nonapplication.request.Body;
import com.example.xc_nonapplication.request.Head;
import com.example.xc_nonapplication.request.RequsetInfo;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 作者：Royal
 * 网络通讯的工具类
 * 日期: 2020/5/21 08:37
 */
public class EsbUtil {

    //服务端地址
    private static final String URL_SERVER = "http://192.168.1.117:8848/xenco/getXencoInfo";

    /**
     * 请求服务到longin
     */
    public void LonginService(LoginInfoVo loginfoVo,Handler handler){
        OperateData operateData = new OperateData();
        RequsetInfo requsetInfo = new RequsetInfo();
        Head head = new Head();
        head.setService_type("LOGIN");
        Body body = new Body();
        body.setLogininfo(loginfoVo);
        requsetInfo.setHead(head);
        requsetInfo.setBody(body);
        String jsonString = new Gson().toJson(requsetInfo);
        URL url = null;
        try {
            url = new URL(URL_SERVER);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        operateData.sendData(jsonString, handler, url,head.getService_type());
    }


    /**
     * 请求服务到Train_service
     */
    public void TrainService(PhoneInfoVo phoneInfo, Handler handler){
        OperateData operateData = new OperateData();
        RequsetInfo requsetInfo = new RequsetInfo();
        Head head = new Head();
        head.setService_type("TRAIN_NUMBER");
        Body body = new Body();
        body.setPhoneInfo(phoneInfo);
        requsetInfo.setHead(head);
        requsetInfo.setBody(body);
        String jsonString = new Gson().toJson(requsetInfo);
        URL url = null;
        try {
            url = new URL(URL_SERVER);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        operateData.sendData(jsonString, handler, url,head.getService_type());
    }
}

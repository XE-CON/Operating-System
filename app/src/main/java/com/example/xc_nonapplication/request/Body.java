package com.example.xc_nonapplication.request;

import com.example.xc_nonapplication.Vo.LoginInfoVo;
import com.example.xc_nonapplication.Vo.PhoneInfoVo;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/5/18 15:10
 */
public class Body {

    private LoginInfoVo logininfo;
    private PhoneInfoVo phoneInfo;


    public LoginInfoVo getLogininfo() {
        return logininfo;
    }

    public void setLogininfo(LoginInfoVo logininfo) {
        this.logininfo = logininfo;
    }

    public PhoneInfoVo getPhoneInfo() {
        return phoneInfo;
    }

    public void setPhoneInfo(PhoneInfoVo phoneInfo) {
        this.phoneInfo = phoneInfo;
    }
}

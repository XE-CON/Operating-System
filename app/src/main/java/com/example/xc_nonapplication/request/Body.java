package com.example.xc_nonapplication.request;

import com.example.xc_nonapplication.Vo.LoginInfoVo;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/5/18 15:10
 */
public class Body {

    private LoginInfoVo logininfo;

    public LoginInfoVo getLogininfo() {
        return logininfo;
    }

    public void setLogininfo(LoginInfoVo logininfo) {
        this.logininfo = logininfo;
    }
}

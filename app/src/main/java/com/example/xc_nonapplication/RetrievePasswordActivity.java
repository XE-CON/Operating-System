package com.example.xc_nonapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.Vo.PhoneInfoVo;
import com.example.xc_nonapplication.util.EsbUtil;
import com.example.xc_nonapplication.util.OtherUtil;
import com.example.xc_nonapplication.util.ToastUtil;

import java.util.Random;

public class RetrievePasswordActivity extends AppCompatActivity {

    private Button mBtnPpevious, mBtnNext, mBtnGetVfCode;
    private EditText mEtPhoneNumber, mEtVfCode;
    String verifyCode="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
        mBtnPpevious = findViewById(R.id.btn_previous);
        mBtnNext = findViewById(R.id.btn_next);
        mBtnGetVfCode = findViewById(R.id.btn_vfcode);
        OnClick onClick = new OnClick();
        mBtnPpevious.setOnClickListener(onClick);
        //控制登录手机图标大小,位置
        mEtPhoneNumber = findViewById(R.id.et_phonenumber);
        Drawable drawable = getResources().getDrawable(R.drawable.phone_number);
        drawable.setBounds(0, 0, 80, 80);
        mEtPhoneNumber.setCompoundDrawables(drawable, null, null, null);

        //控制验证码图标图标大小，位置
        mEtVfCode = findViewById(R.id.et_vfcode);
        Drawable drawable1 = getResources().getDrawable(R.drawable.verification_code);
        drawable1.setBounds(0, 0, 80, 80);
        mEtVfCode.setCompoundDrawables(drawable1, null, null, null);

        mBtnGetVfCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //生成验证码
                verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
                ToastUtil.showMsgTop(RetrievePasswordActivity.this, "验证码是" + verifyCode);
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名和密码
                String phoneNumber = mEtPhoneNumber.getText().toString().trim();
                String vfCode = mEtVfCode.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(vfCode)) {
                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "验证码和手机号输入不能为空");
                } else if (!verifyCode.equals(vfCode)) {
                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "输入的验证码错误");
                } else {
                    //调用接口 查询是否绑定培训证号
                    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what) {
                                case 0:
                                    ToastUtil.showMsgTop(RetrievePasswordActivity.this,"服务器连接失败");
                                    break;
                                case 1:
                                    //通过检验 跳转到设置新密码界面
                                    Intent intent = new Intent(RetrievePasswordActivity.this, SetPasswordActivity.class);
                                    startActivity(intent);
//                                    RetrievePasswordActivity.this.finish();
                                    break;
                                case 2:
                                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "该手机未绑定培训证号");
                                    break;
                                case 3:
                                    Log.e("input error", "url为空");
                                    break;
                                case 4:
                                    ToastUtil.showMsgTop(RetrievePasswordActivity.this,"连接超时");
                                    break;
                                default:
                            }
                        }
                    };

                    PhoneInfoVo phoneInfoVo = new PhoneInfoVo();
                    phoneInfoVo.setPhonenumber(phoneNumber);
                    //=======================发送请求到服务器====================//
                    EsbUtil esbUtil = new EsbUtil();
                    esbUtil.TrainService(phoneInfoVo, handler);

                }
            }
        });

        //手机号码输入框监听事件
        mEtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean result=OtherUtil.CheckMobilePhoneNum(mEtPhoneNumber.getText().toString().trim());
                //不符合则提示手机号码有误
                if (!result){
                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "手机号格式不正确");
                }
            }
        });
    }


    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_previous:
                    //返回上一个界面
                    intent = new Intent(RetrievePasswordActivity.this, LoginActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}

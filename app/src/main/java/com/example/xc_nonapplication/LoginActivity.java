package com.example.xc_nonapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.Vo.LoginInfoVo;
import com.example.xc_nonapplication.request.Body;
import com.example.xc_nonapplication.request.Head;
import com.example.xc_nonapplication.request.RequsetInfo;
import com.example.xc_nonapplication.util.OperateData;
import com.example.xc_nonapplication.util.ToastUtil;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {

    private Button mBtnLogin, mBtnForget;
    private CheckBox mCbDisplayPassword;
    private EditText mEtUsername, mEtPassword;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private CheckBox mCbRemberPs;

    //服务端地址
    private static final String URLLOGIN = "http://192.168.1.117:8848/xenco/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //控制登录用户名图标大小
        mEtUsername = findViewById(R.id.et_username);
        Drawable drawable = getResources().getDrawable(R.drawable.username);
        drawable.setBounds(0, 0, 60, 60);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mEtUsername.setCompoundDrawables(drawable, null, null, null);//只放左边

        //控制密码图标大小
        mEtPassword = findViewById(R.id.et_password);
        Drawable drawable1 = getResources().getDrawable(R.drawable.password);
        drawable1.setBounds(0, 0, 60, 60);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mEtPassword.setCompoundDrawables(drawable1, null, null, null);//只放左边

        mBtnLogin = findViewById(R.id.btn_login);
        mBtnForget = findViewById(R.id.btn_forgetps);
        mCbDisplayPassword = findViewById(R.id.cb_DisplayPassword);
        mEtPassword = findViewById(R.id.et_password);
        //账号密码正确时登陆成功跳转页面
        OnClick onClick = new OnClick();
        mBtnLogin.setOnClickListener(new View.OnClickListener() {

//            String[] data = null;
            @Override
            public void onClick(View v) {
                //获取用户名和密码
                String username = mEtUsername.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    ToastUtil.showMsg(LoginActivity.this, "用户名密码输入不能为空");
                } else {
//                    if ("abcd".equals(username)) {
//                        //同后台校验之后密码错误弹出提示.这部分需要调用后台的接口
//                        ToastUtil.showMsg(LoginActivity.this, "该培训证号不存在");
//                    } else {
//                        //通过检验 跳转到主界面
//                        Intent intent = new Intent(LoginActivity.this, LoginSuccessActivity.class);
//                        startActivity(intent);
//                    }
//                    data = new String[]{username, password};
                    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what) {
                                case 0:
                                    Toast.makeText(LoginActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, LoginSuccessActivity.class));
                                    LoginActivity.this.finish();
                                    break;
                                case 2:
                                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                    break;
                                case 3:
                                    Log.e("input error", "url为空");
                                    break;
                                case 4:
                                    Toast.makeText(LoginActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                            }
                        }
                    };
                    //传后端的方法
                    OperateData operateData = new OperateData();
                    //创建一个请求对象 给他装配
                    RequsetInfo requsetInfo=new RequsetInfo();
                    LoginInfoVo loginInfoVo=new LoginInfoVo();
                    Head head=new Head();
                    head.setService_type("LOGIN");
                    Body body=new Body();
                    loginInfoVo.setUsername(username);
                    loginInfoVo.setPassword(password);
                    body.setLogininfo(loginInfoVo);
                    requsetInfo.setHead(head);
                    requsetInfo.setBody(body);
                    String jsonString = new Gson().toJson(requsetInfo);
                    URL url = null;
                    try {
                        url = new URL(URLLOGIN);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    operateData.sendData(jsonString, handler, url);

                }
            }
        });
        mBtnForget.setOnClickListener(onClick);
        //切换密码显示
        mCbDisplayPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("aaa", "onCheckedChanged: " + isChecked);
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        //记住密码功能
        mSharedPreferences = getSharedPreferences("data111", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mCbRemberPs = findViewById(R.id.cb_remmberps);
        mCbRemberPs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //选中时保存
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String username = mEtUsername.getText().toString().trim();
                    String password = mEtPassword.getText().toString().trim();
                    mEditor.putString("username", username);
                    mEditor.putString("password", password);
                    mEditor.putString("ischeck", "true");
                    mEditor.apply();
                } else {
                    mEditor.putString("username", "");
                    mEditor.putString("password", "");
                    mEditor.putString("ischeck", "false");
                    mEditor.apply();
                }
            }
        });
        //初始化的时候
        if (mSharedPreferences.getString("ischeck", "").equals("true")) {
            mEtUsername.setText(mSharedPreferences.getString("username", ""));
            mEtPassword.setText(mSharedPreferences.getString("password", ""));
            mCbRemberPs.setChecked(true);
        }else {
            mEtUsername.setText("");
            mEtPassword.setText("");
            mCbRemberPs.setChecked(false);
        }
    }


    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_forgetps:
                    //跳转找回密码界面
                    intent = new Intent(LoginActivity.this, RetrievePasswordActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}

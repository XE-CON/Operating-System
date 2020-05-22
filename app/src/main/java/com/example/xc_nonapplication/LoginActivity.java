package com.example.xc_nonapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.Vo.LoginInfoVo;
import com.example.xc_nonapplication.util.EsbUtil;
import com.example.xc_nonapplication.util.ToastUtil;


public class LoginActivity extends AppCompatActivity {

    private Button mBtnLogin, mBtnForget;
    private CheckBox mCbDisplayPassword;
    private EditText mEttrainnumber, mEtPassword;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private CheckBox mCbRemberPs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //控制登录用户名图标大小
        mEttrainnumber = findViewById(R.id.et_trainnumber);
        Drawable drawable = getResources().getDrawable(R.drawable.trainnumber);
        drawable.setBounds(0, 0, 60, 60);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mEttrainnumber.setCompoundDrawables(drawable, null, null, null);//只放左边

        //控制密码图标大小
        mEtPassword = findViewById(R.id.et_password);
        Drawable drawable1 = getResources().getDrawable(R.drawable.password);
        drawable1.setBounds(0, 0, 60, 60);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mEtPassword.setCompoundDrawables(drawable1, null, null, null);//只放左边

        mBtnLogin = findViewById(R.id.btn_login);
        mBtnForget = findViewById(R.id.btn_forgetps);
        mCbDisplayPassword = findViewById(R.id.cb_DisplayPassword);
        mEtPassword = findViewById(R.id.et_password);
        OnClick onClick = new OnClick();
        //登陆功能
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名和密码
                String trainnumber = mEttrainnumber.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(trainnumber) || TextUtils.isEmpty(password)) {
                    ToastUtil.showMsgTop(LoginActivity.this, "用户名密码输入不能为空");
                } else {
                    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what) {
                                case 0:
                                    ToastUtil.showMsgTop(LoginActivity.this,"服务器连接失败");
                                    break;
                                case 1:
                                    ToastUtil.showMsgTop(LoginActivity.this,"登录成功");
                                    startActivity(new Intent(LoginActivity.this, LoginSuccessActivity.class));
                                    LoginActivity.this.finish();
                                    break;
                                case 2:
                                    ToastUtil.showMsgTop(LoginActivity.this,"登录失败,培训证号或密码输入有误");
                                    break;
                                case 3:
                                    Log.e("input error", "url为空");
                                    break;
                                case 4:
                                    ToastUtil.showMsgTop(LoginActivity.this,"连接超时");
                                    break;
                                default:
                            }
                        }
                    };
                    LoginInfoVo loginInfoVo = new LoginInfoVo();
                    loginInfoVo.setTrainnumber(trainnumber);
                    loginInfoVo.setPassword(password);
                    //=======================发送请求到服务器====================//
                    EsbUtil esbUtil = new EsbUtil();
                    esbUtil.LonginService(loginInfoVo, handler);
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
                    String trainnumber = mEttrainnumber.getText().toString().trim();
                    String password = mEtPassword.getText().toString().trim();
                    mEditor.putString("trainnumber", trainnumber);
                    mEditor.putString("password", password);
                    mEditor.putString("ischeck", "true");
                    mEditor.apply();
                } else {
                    mEditor.putString("trainnumber", "");
                    mEditor.putString("password", "");
                    mEditor.putString("ischeck", "false");
                    mEditor.apply();
                }
            }
        });
        //初始化的时候
        if (mSharedPreferences.getString("ischeck", "").equals("true")) {
            mEttrainnumber.setText(mSharedPreferences.getString("trainnumber", ""));
            mEtPassword.setText(mSharedPreferences.getString("password", ""));
            mCbRemberPs.setChecked(true);
        } else {
            mEttrainnumber.setText("");
            mEtPassword.setText("");
            mCbRemberPs.setChecked(false);
        }

        //培训证号码输入框监听事件
        mEttrainnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trainnumber = mEttrainnumber.getText().toString().trim();
                if (trainnumber.length() > 12) {
                    //到时候换成一个校验
                    ToastUtil.showMsgTop(LoginActivity.this, "培训证号的长度小于12位,请检查您输入的培训证号");
                }
            }
        });

        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = mEtPassword.getText().toString().trim();
                if (password.length() > 8 || password.length() < 6) {
                    ToastUtil.showMsgTop(LoginActivity.this, "输入密码的长度应大于6位小于8位,请检查您输入的账号和密码");
                }
            }
        });
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

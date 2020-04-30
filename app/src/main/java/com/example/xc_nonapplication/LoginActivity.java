package com.example.xc_nonapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.util.ToastUtil;


public class LoginActivity extends AppCompatActivity {

    private Button mBtnLogin, mBtnForget;
    private CheckBox mCbDisplayPassword;
    private EditText mEtUsername, mEtPassword;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private CheckBox mCbRemberPs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //控制登录用户名图标大小
        mEtUsername = findViewById(R.id.et_username);
        Drawable drawable = getResources().getDrawable(R.drawable.username);
        drawable.setBounds(0, 0, 80, 80);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mEtUsername.setCompoundDrawables(drawable, null, null, null);//只放左边

        //控制密码图标大小
        mEtPassword = findViewById(R.id.et_password);
        Drawable drawable1 = getResources().getDrawable(R.drawable.password);
        drawable1.setBounds(0, 0, 80, 80);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mEtPassword.setCompoundDrawables(drawable1, null, null, null);//只放左边

        mBtnLogin = findViewById(R.id.btn_login);
        mBtnForget = findViewById(R.id.btn_forgetps);
        mCbDisplayPassword = findViewById(R.id.cb_DisplayPassword);
        mEtPassword = findViewById(R.id.et_password);
        //账号密码正确时登陆成功跳转页面
        OnClick onClick = new OnClick();
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名和密码
                String username =mEtUsername.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    ToastUtil.showMsg(LoginActivity.this, "用户名和密码输入不能为空");
                } else {
                    if (false) {
                        //同后台校验之后密码错误弹出提示.这部分需要调用后台的接口
                        ToastUtil.showMsg(LoginActivity.this, "密码错误");
                    } else {
                        //通过检验 跳转到主界面
                        Intent intent = new Intent(LoginActivity.this, LoginSuccessActivity.class);
                        startActivity(intent);
                    }
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
        //创建一个保存对象
//        mSharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
//        //编辑器
//        mEditor=mSharedPreferences.edit();
//        boolean isRemember = mSharedPreferences.getBoolean("true", false);
//        if (true) {
//            mEditor.putString("username", mEtUsername.getText().toString());
//            mEditor.putString("Password", mEtPassword.getText().toString());
//            mEtUsername.setText(mSharedPreferences.getString("username", ""));
//            mEtPassword.setText(mSharedPreferences.getString("Password", ""));
//            mCbRemberPs.setChecked(true);
//        }
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

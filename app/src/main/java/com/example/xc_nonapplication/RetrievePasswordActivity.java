package com.example.xc_nonapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xc_nonapplication.util.ToastUtil;

public class RetrievePasswordActivity extends AppCompatActivity {

    private Button mBtnPpevious, mBtnNext, mBtnGetVfCode;
    private EditText mEtPhoneNumber, mEtVfCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
        mBtnPpevious = findViewById(R.id.btn_previous);
        mBtnNext = findViewById(R.id.btn_next);
        mBtnGetVfCode = findViewById(R.id.btn_vfcode);
        OnClick onClick = new OnClick();
        mBtnPpevious.setOnClickListener(onClick);
        //控制登录手机图标大小
        mEtPhoneNumber = findViewById(R.id.et_phonenumber);
        Drawable drawable = getResources().getDrawable(R.drawable.phone_number);
        drawable.setBounds(0, 0, 80, 80);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mEtPhoneNumber.setCompoundDrawables(drawable, null, null, null);//只放左边

        //控制验证码图标图标大小
        mEtVfCode = findViewById(R.id.et_vfcode);
        Drawable drawable1 = getResources().getDrawable(R.drawable.verification_code);
        drawable1.setBounds(0, 0, 80, 80);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mEtVfCode.setCompoundDrawables(drawable1, null, null, null);//只放左边
        mBtnGetVfCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMsg(RetrievePasswordActivity.this, "验证码是xxxxxxx");
            }
        });


        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名和密码
                String phoneNumber = mEtPhoneNumber.getText().toString().trim();
                String vfCode = mEtVfCode.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(vfCode)) {
                    ToastUtil.showMsg(RetrievePasswordActivity.this, "验证码和手机号输入不能为空");
                } else {
                    //写死暂时用自己的手机号码来作为不可能的情况
                    if ("15067333278".equals(phoneNumber)) {
                        //该手机号未绑定培训证号
                        ToastUtil.showMsg(RetrievePasswordActivity.this, "该手机号未绑定培训证号");
                        return;
                    }
                    if ("591867".equals(vfCode)) {
                        ToastUtil.showMsg(RetrievePasswordActivity.this, "验证码错误");
                        return;
                    } else {
                        //通过检验 跳转到设置新密码界面
                        Intent intent = new Intent(RetrievePasswordActivity.this, SetPasswordActivity.class);
                        startActivity(intent);
                    }
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

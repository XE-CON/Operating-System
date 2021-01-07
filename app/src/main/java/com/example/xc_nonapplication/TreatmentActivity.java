package com.example.xc_nonapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/12/9 10:41
 */
public class TreatmentActivity extends AppCompatActivity {

    private Button mBtnComplete,btn_start,mBtnStart;
    private TextView mTUser;
    private TextView tv_progress,mTvtime;
    private CircleProgressView circle_progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        //testtime
//        mBtnStart=findViewById(R.id.btn_starttime);
//        mTvtime=findViewById(R.id.et_time);
//        mBtnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mTvtime.setText("3");
//            }
//        });


        //test
        btn_start = (Button) findViewById(R.id.btn_start);
//        btn_reset = (Button) findViewById(R.id.btn_reset);
        circle_progress = (CircleProgressView) findViewById(R.id.circle_progress);
        tv_progress = (TextView) findViewById(R.id.tv_progress);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开锁执行动画效果
                circle_progress.startAnimProgress(100, 1200);
                //监听进度条进度
                circle_progress.setOnAnimProgressListener(new CircleProgressView.OnAnimProgressListener() {
                    @Override
                    public void valueUpdate(int progress) {
                        tv_progress.setText(String.valueOf(progress));
                    }
                });
            }
        });
//        btn_reset.setOnClickListener(this);

        //记录信息界面用户图标大小
        mTUser = findViewById(R.id.tv_user);
        Drawable drawable = getResources().getDrawable(R.drawable.user1);
        drawable.setBounds(0, 0, 60, 60);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mTUser.setCompoundDrawables(drawable, null, null, null);//只放左边

        //设置用户 这个用户名可以从前面获取
        mTUser.setText("Royal");

        //跳转至治疗界面
        mBtnComplete=findViewById(R.id.btn_complete2);
        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.btn_complete2:
                        intent = new Intent(TreatmentActivity.this, CompleteTreatmentActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_start:
//                //开锁执行动画效果
//                circle_progress.startAnimProgress(50, 1200);
//                //监听进度条进度
//                circle_progress.setOnAnimProgressListener(new CircleProgressView.OnAnimProgressListener() {
//                    @Override
//                    public void valueUpdate(int progress) {
//                        tv_progress.setText(String.valueOf(progress));
//                    }
//                });
//                break;
////            case R.id.btn_reset:
////                circle_progress.setCurrent(0);
////                tv_progress.setText("0");
////                break;
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (circle_progress != null) {
            circle_progress.destroy();
        }
    }

}

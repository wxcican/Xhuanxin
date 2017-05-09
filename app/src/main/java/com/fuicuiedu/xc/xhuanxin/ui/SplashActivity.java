package com.fuicuiedu.xc.xhuanxin.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fuicuiedu.xc.xhuanxin.ui.user.LoginActivity;
import com.fuicuiedu.xc.xhuanxin.MyHelper;
import com.fuicuiedu.xc.xhuanxin.R;
import com.hyphenate.chat.EMClient;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //判断登录状态，决定跳转位置
                Intent intent;
                if (MyHelper.getLogin()){
                    //两个方法是为了保证进入主页面后本地会话和群组都 load 完毕。
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                }else{
                    intent = new Intent(SplashActivity.this,LoginActivity.class);
                }

                startActivity(intent);
                finish();
            }
        },1500);
    }
}

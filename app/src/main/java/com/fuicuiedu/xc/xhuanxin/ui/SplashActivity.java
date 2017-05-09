package com.fuicuiedu.xc.xhuanxin.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fuicuiedu.xc.xhuanxin.ui.user.LoginActivity;
import com.fuicuiedu.xc.xhuanxin.MyHelper;
import com.fuicuiedu.xc.xhuanxin.R;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

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
//                EMClient.getInstance().isLoggedInBefore()
//                环信提供登录状态获取方法，但在初始化时不能关闭自动登录
                Intent intent;
                if (MyHelper.getLogin()) {
                    //两个方法是为了保证进入主页面后本地会话和群组都 load 完毕。
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();

                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }

                //注册一个监听连接状态的listener
                EMClient.getInstance().addConnectionListener(new MyConnectionListener());

                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }
        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        Toast.makeText(SplashActivity.this, "账号被移除", Toast.LENGTH_SHORT).show();
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        Toast.makeText(SplashActivity.this, "帐号在其他设备登录", Toast.LENGTH_SHORT).show();
                    } else {
                        if (NetUtils.hasNetwork(SplashActivity.this)) {
                            //连接不到聊天服务器
                            Toast.makeText(SplashActivity.this, "连接不到服务器", Toast.LENGTH_SHORT).show();
                        } else {
                            //当前网络不可用，请检查网络设置
                            Toast.makeText(SplashActivity.this, "请检查网路设置", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}

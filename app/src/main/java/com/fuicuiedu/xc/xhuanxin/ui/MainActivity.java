package com.fuicuiedu.xc.xhuanxin.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.fuicuiedu.xc.xhuanxin.MyHelper;
import com.fuicuiedu.xc.xhuanxin.R;
import com.fuicuiedu.xc.xhuanxin.ui.user.ChatActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.main_btn)
    public void loginout(){
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("正在退出...");
        pb.show();

        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                pb.dismiss();
                MyHelper.setLogin(false);
                startActivity(new Intent(MainActivity.this,SplashActivity.class));
            }

            @Override
            public void onError(int code, String error) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(MainActivity.this, "退出失败", Toast.LENGTH_SHORT).show();
        }
    };
}

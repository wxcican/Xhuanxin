package com.fuicuiedu.xc.xhuanxin.ui.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fuicuiedu.xc.xhuanxin.R;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class ChatActivity extends AppCompatActivity {

    /** 启动当前Activity*/
    public static void open(Context context, String chatId){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_SINGLE); // 单聊
        intent.putExtra(EaseConstant.EXTRA_USER_ID, chatId); // 对方id
        context.startActivity(intent);
        // 一旦进入聊天页面，就取消通知栏通知
//        EaseUI.getInstance().getNotifier().reset();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        addChatFragment();
    }

    // 添加聊天Fragment
    private void addChatFragment(){
        int chatType = getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, 0);   // 类别
        String chatId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);

        EaseChatFragment chatFragment = new EaseChatFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("chatType",chatType);
        bundle.putString("userId",chatId);
        chatFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.layout_container, chatFragment)
                .commit();
    }
}

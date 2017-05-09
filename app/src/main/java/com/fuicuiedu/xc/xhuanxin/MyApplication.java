package com.fuicuiedu.xc.xhuanxin;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //本地配置初始化
        MyHelper.init(this);

        //环信相关
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        //正式使用 EaseUI 需要先调用初始化方法，在 Application 的 oncreate 里调用初始化。
        EaseUI.getInstance().init(this,options);
    }
}

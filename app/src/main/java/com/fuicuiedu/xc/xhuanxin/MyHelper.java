package com.fuicuiedu.xc.xhuanxin;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class MyHelper {
    private static final String NAME = MyHelper.class.getSimpleName();
    private static final String ISLOGIN = "islogin";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private MyHelper(){};

    public static void init(Context context){
        sharedPreferences =context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void setLogin(boolean islogin){
        editor.putBoolean(ISLOGIN,islogin);
        editor.apply();
    }

    public static boolean getLogin(){
        return sharedPreferences.getBoolean(ISLOGIN,false);
    }
}

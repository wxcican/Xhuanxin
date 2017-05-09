package com.fuicuiedu.xc.xhuanxin.ui.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fuicuiedu.xc.xhuanxin.MyHelper;
import com.fuicuiedu.xc.xhuanxin.R;
import com.fuicuiedu.xc.xhuanxin.ui.MainActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText usernameEditText;
    @BindView(R.id.password)
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_btn_login, R.id.login_btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            //注册
            case R.id.login_btn_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            //登录
            case R.id.login_btn_login:
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog pb = new ProgressDialog(this);
                pb.setMessage(getString(R.string.Is_landing));
                pb.show();

                //执行环信登录操作
                EMClient.getInstance().login(username, password, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        pb.dismiss();
                        //两个方法是为了保证进入主页面后本地会话和群组都 load 完毕。
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();

                        MyHelper.setLogin(true);
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(int code, String error) {
                        pb.dismiss();
                        handler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });


                break;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
        }
    };
}

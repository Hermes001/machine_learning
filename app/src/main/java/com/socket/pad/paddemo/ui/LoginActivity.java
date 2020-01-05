package com.socket.pad.paddemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.socket.pad.paddemo.R;
import com.socket.pad.paddemo.Utils.DataUtils;
import com.socket.pad.paddemo.Utils.ToolsUtils;
import com.socket.pad.paddemo.db.DBUtils;
import com.socket.pad.paddemo.model.RecInfoModel;

import java.net.InetAddress;
import java.util.ArrayList;

public class LoginActivity extends BaseActivity {

    private TextView tvLogin;
    private EditText etUserName;
    private EditText etPwd;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    private void initView()
    {
        initTitle();
        tvLogin = findViewById(R.id.tv_login);
        etUserName = findViewById(R.id.et_username);
        etPwd = findViewById(R.id.et_pwd);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etUserName.getText().toString())){
                    Toast.makeText(LoginActivity.this,R.string.please_input_username,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(etPwd.getText().toString())){
                    Toast.makeText(LoginActivity.this,R.string.please_input_pwd,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ToolsUtils.checkUser(etUserName.getText().toString(),etPwd.getText().toString())){
                    startmActivity(LoginActivity.this,MainActivity.class);
                }else{
                    Toast.makeText(LoginActivity.this,R.string.usernmae_or_pwd_error,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initTitle()
    {
        ivBack = findViewById(R.id.iv_back);
        tvBack = findViewById(R.id.tv_back);
        tvQuit = findViewById(R.id.tv_quit);
        tvTitle = findViewById(R.id.tv_title);
        ivHead = findViewById(R.id.iv_head);
        tvNickName = findViewById(R.id.tv_nickname);
        ivBack.setVisibility(View.GONE);
        tvBack.setVisibility(View.GONE);
        tvQuit.setVisibility(View.GONE);
        ivHead.setVisibility(View.GONE);
        tvNickName.setVisibility(View.GONE);
        tvTitle.setText(getResources().getString(R.string.title));
    }

}


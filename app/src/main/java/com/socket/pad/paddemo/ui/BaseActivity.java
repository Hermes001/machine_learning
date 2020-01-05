package com.socket.pad.paddemo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseActivity extends Activity {

    public ImageView ivBack;
    public TextView tvBack;
    public TextView tvQuit;
    public TextView tvTitle;
    public ImageView ivHead;
    public TextView tvNickName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void startmActivity(Context mContext,Class<?> cls)
    {
        Intent intent = new Intent(mContext,cls);
        startActivity(intent);
    }
}

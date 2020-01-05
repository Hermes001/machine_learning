package com.socket.pad.paddemo.ui;

import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.socket.pad.paddemo.R;
import com.socket.pad.paddemo.Utils.DataUtils;
import com.socket.pad.paddemo.Utils.PopWinDownUtil;
import com.socket.pad.paddemo.db.DBUtils;
import com.socket.pad.paddemo.manager.RecvInfoManager;
import com.socket.pad.paddemo.model.ConfigureModel;
import com.socket.pad.paddemo.net.socket.SocketServer;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final int REC_DATA = 0x1001;
    public static final int SOCKET_CONNECT_SUCCESS = 0x1002;
    private LinearLayout linear_setting;
    private LinearLayout linear_realtime_data;
    private LinearLayout linear_query_data;
    private LinearLayout linear_start;
    private LinearLayout linear_pause;
    private LinearLayout linear_end;
    int resquest = 100;
    private SocketServer mSocketServer;
    ExecutorService exec = Executors.newCachedThreadPool();

    private WorkHandler mWorkHandler;
    private RecvInfoManager mRecvInfoManager;
    private ConfigureModel mConfigureModel;

    /*
     * 标记命令所处的阶段
     * 0 初始状态
     * 1 等待基础命令的ack
     * 2 等待开始指令的ack
     * 3 等待暂停指令的ack
     * 4 等待结束指令的ack
     * 5 等待
     * */
    public static int commandFlag = 0;

    private TextView tvFlag ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mWorkHandler = new WorkHandler(MainActivity.this);
    }

    private void initView() {
        initTitle();
        linear_setting = findViewById(R.id.linear_setting);
        linear_realtime_data = findViewById(R.id.linear_realtime_data);
        linear_query_data = findViewById(R.id.linear_query_data);
        linear_start = findViewById(R.id.linear_start);
        linear_pause = findViewById(R.id.linear_pause);
        linear_end = findViewById(R.id.linear_end);
        tvFlag = findViewById(R.id.tv_flag);

        linear_setting.setOnClickListener(this);
        linear_realtime_data.setOnClickListener(this);
        linear_query_data.setOnClickListener(this);
        linear_start.setOnClickListener(this);
        linear_pause.setOnClickListener(this);
        linear_end.setOnClickListener(this);

    }

    private void initTitle() {
        ivBack = findViewById(R.id.iv_back);
        tvBack = findViewById(R.id.tv_back);
        tvQuit = findViewById(R.id.tv_quit);
        tvTitle = findViewById(R.id.tv_title);
        ivHead = findViewById(R.id.iv_head);
        tvNickName = findViewById(R.id.tv_nickname);
        ivBack.setVisibility(View.INVISIBLE);
        tvBack.setVisibility(View.INVISIBLE);

        tvNickName.setText("王二小");
        tvTitle.setText(getResources().getString(R.string.welcome_come_in));
        tvQuit.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mConfigureModel!=null)
        {
            if (mSocketServer == null) {
                mSocketServer = new SocketServer(mWorkHandler);
                mRecvInfoManager = new RecvInfoManager(this, mSocketServer, exec);
            }
            exec.execute(mSocketServer);
            tvFlag.setText("启动socket");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.linear_setting:
                /* startmActivity(MainActivity.this,SettingActivity.class);*/
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(intent, resquest);
                Log.d("cfn", "进入设置");
                break;
            case R.id.linear_realtime_data:
                startmActivity(MainActivity.this, RealTimeActivity.class);
                Log.d("cfn", "进入实时数据");
                break;
            case R.id.linear_query_data:
                startmActivity(MainActivity.this, QueryActivity.class);
                Log.d("cfn", "进入数据查询");

                break;
            case R.id.linear_start:
                Log.d("cfn", "开始试验");
                startTest();
                break;
            case R.id.linear_pause:
                Log.d("cfn", "暂停试验");
                pauseTest();
                break;
            case R.id.linear_end:
                Log.d("cfn", "结束试验");
                endTest();
                break;
            case R.id.tv_quit:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.resquest) {
            try {
                mConfigureModel = data.getExtras().getParcelable("configure");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private static class WorkHandler extends Handler {
        WeakReference<MainActivity> mWeakReference;

        public WorkHandler(MainActivity activity) {
            mWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MainActivity activity = mWeakReference.get();
            if (activity != null) {
                if (msg.what == REC_DATA) {
                    int flag =activity.mRecvInfoManager.praseData((String) msg.obj);
                    if(flag!=0){
                        return;
                    }
                    switch (commandFlag) {
                       /* case 0:
                            activity.mRecvInfoManager.praseData((String) msg.obj);
                            break;*/
                        case 1:
                            if(DataUtils.isConfigureCommandAck((String) msg.obj))
                            {
                                commandFlag = 0;
                                activity.mSocketServer.SST.get(0).send(DataUtils.getStartCommand(1));
                                commandFlag = 2;
                            }else{
                    //            Toast.makeText(activity,"配置指令ack错误",Toast.LENGTH_SHORT).show();
                                activity.tvFlag.setText("配置指令ack错误");
                            }
                            break;
                        case 2:
                            if(DataUtils.isStartCommandAck((String) msg.obj))
                            {
                                commandFlag =0;
                            }else{
                   //             Toast.makeText(activity,"开始指令ack错误",Toast.LENGTH_SHORT).show();
                                activity.tvFlag.setText("开始指令ack错误");
                            }
                            break;
                        case 3:
                            if(DataUtils.isStartCommandAck((String) msg.obj))
                            {
                                commandFlag =0;
                            }else{
                       //         Toast.makeText(activity,"暂停指令ack错误",Toast.LENGTH_SHORT).show();
                                activity.tvFlag.setText("暂停指令ack错误");
                            }
                            break;
                        case 4:
                            if(DataUtils.isStartCommandAck((String) msg.obj))
                            {
                                commandFlag =0;
                            }else{
                   //             Toast.makeText(activity,"结束指令ack错误",Toast.LENGTH_SHORT).show();
                                activity.tvFlag.setText("结束指令ack错误");
                            }
                            break;
                        case 5:
                            break;

                    }
           //
                }
                if (msg.what == SOCKET_CONNECT_SUCCESS) {
                    //Toast.makeText(activity, "设备已连接", Toast.LENGTH_SHORT).show();
                    activity.tvFlag.setText("设备已连接");
                    if (activity.mSocketServer.SST != null && activity.mSocketServer.SST.size() > 0) {
                        //连接成功，发送基础实验数据
                   ///     activity.mSocketServer.SST.get(0).send(DataUtils.getConfigureCommand(activity.mConfigureModel));
                        activity.exec.execute(new Runnable() {
                            @Override
                            public void run() {
                                activity.mSocketServer.SST.get(0).send(DataUtils.getConfigureCommand(activity.mConfigureModel));
                            }
                        });
                        commandFlag = 1;
                    }
                }
            }
        }
    }

    private void startTest() {
        if (mConfigureModel == null) {
//            Toast.makeText(MainActivity.this, "请进入设置选择实验", Toast.LENGTH_SHORT).show();
            tvFlag.setText("请进入设置选择实验");
            return;
        }
        if (mSocketServer!=null&&mSocketServer.SST != null && mSocketServer.SST.size() > 0) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    mSocketServer.SST.get(0).send(DataUtils.getStartCommand(1));
                }
            });

            commandFlag = 2;
        }else{
            tvFlag.setText("没有实验数据");
        }
    }

    private void pauseTest() {
  //      Toast.makeText(MainActivity.this, "执行暂停实验", Toast.LENGTH_SHORT).show();
        tvFlag.setText("执行暂停实验");
        if (mSocketServer!=null&&mSocketServer.SST != null && mSocketServer.SST.size() > 0) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    mSocketServer.SST.get(0).send(DataUtils.getStartCommand(2));
                }
            });

            commandFlag = 3;
        }else{
            tvFlag.setText("没有实验数据");
        }
    }

    private void endTest() {
//        mSocketServer.closeSelf();
//        Toast.makeText(MainActivity.this, "执行结束实验", Toast.LENGTH_SHORT).show();
        tvFlag.setText("执行结束实验");
        if (mSocketServer!=null&&mSocketServer.SST != null && mSocketServer.SST.size() > 0) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    mSocketServer.SST.get(0).send(DataUtils.getStartCommand(0));
                }
            });

            commandFlag = 4;
        }else{
            tvFlag.setText("没有实验数据");
        }
    }

}

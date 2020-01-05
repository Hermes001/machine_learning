package com.socket.pad.paddemo.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socket.pad.paddemo.R;
import com.socket.pad.paddemo.Utils.PopWinDownUtil;
import com.socket.pad.paddemo.Utils.ToolsUtils;
import com.socket.pad.paddemo.db.DBUtils;
import com.socket.pad.paddemo.model.ConfigureModel;
import com.socket.pad.paddemo.net.ConnectCallback;
import com.socket.pad.paddemo.net.HttpConnectCallback;
import com.socket.pad.paddemo.net.OkhttpManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_get_configure;
    private TextView tv_select_configure;
    private TextView tv_configure_ip;
    private RelativeLayout relative_select;
    private ImageView ivLoading;
    private TextView tvLoadingTip;
    private Animation mAnimation;
    private WorkHandler mWorkHandler;
    private ArrayList<ConfigureModel>mConfigureModelList = new ArrayList<>();

    private PopWinDownUtil mPopWinDownStorage;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private TextView text_storageLocation;
    private TextView tv_setIp;

    private final static int GET_DATA_FAIL = 0x0001;
    private final static int GET_DATA_SUCCESS = 0x0002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
    }

    private void initView()
    {
        initTitle();
        tv_get_configure = findViewById(R.id.tv_get_configure);
        tv_select_configure = findViewById(R.id.tv_select_configure);
        tv_configure_ip = findViewById(R.id.tv_configure_ip);
        ivLoading = findViewById(R.id.iv_loading);
        tvLoadingTip = findViewById(R.id.tv_loadtip);
        relative_select = findViewById(R.id.relative_select);
        text_storageLocation = findViewById(R.id.text_storageLocation);
        tv_setIp = findViewById(R.id.tv_setIp);
        tv_setIp.setOnClickListener(this);
        mOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                text_storageLocation.setText("ip :"+mConfigureModelList.get(position).getIp());
                tv_setIp.setVisibility(View.VISIBLE);
                mPopWinDownStorage.hide();
                Intent intent = new Intent();
                intent.putExtra("configure", mConfigureModelList.get(position));
               setResult(RESULT_OK, intent);
            }
        };

    }

    private void initData()
    {
        mWorkHandler = new WorkHandler(this);
        new ExcelDataLoader().execute();
 //       startLoading();
//        getConfigure();
    }

    private void getConfigure()
    {
          OkhttpManager.getInstance().getHttpConfigure(new HttpConnectCallback() {

              @Override
              public void onFailure() {
                  mWorkHandler.sendEmptyMessage(GET_DATA_FAIL);
              }

              @Override
              public void onResponseSuccess(String response) {
                  if(!TextUtils.isEmpty(response)){
                      Log.d("cfn","response =="+response);
                      try {
                          JSONObject jsonResponse = new JSONObject(response);
                          if("1".equals(jsonResponse.getString("num"))){
                              Gson gs = new Gson();
                              JSONArray jsonList = jsonResponse.getJSONArray("info");
                              mConfigureModelList =gs.fromJson(jsonList.toString(),new TypeToken<List<ConfigureModel>>(){}.getType());
                          }
                          if(mConfigureModelList!=null&&mConfigureModelList.size()>0)
                          {
                              for(int i=0;i<mConfigureModelList.size();i++){
                                  DBUtils.addConfigure(getApplicationContext(),mConfigureModelList.get(i));
                              }
                          }
                          mWorkHandler.sendEmptyMessage(GET_DATA_SUCCESS);
                      } catch (JSONException e) {
                          e.printStackTrace();
                          mWorkHandler.sendEmptyMessage(GET_DATA_FAIL);
                      }
                  }else{
                      mWorkHandler.sendEmptyMessage(GET_DATA_FAIL);
                  }

              }
          },ToolsUtils.getCheckCode());
    }

    private void startLoading()
    {
        mAnimation= AnimationUtils.loadAnimation(SettingActivity.this, R.anim.anim_refresh);
        ivLoading.startAnimation(mAnimation);
    }
    private void cancelLoading()
    {
        ivLoading.clearAnimation();
    }

    private void initTitle()
    {
        ivBack = findViewById(R.id.iv_back);
        tvBack = findViewById(R.id.tv_back);
        tvQuit = findViewById(R.id.tv_quit);
        tvTitle = findViewById(R.id.tv_title);
        ivHead = findViewById(R.id.iv_head);
        tvNickName = findViewById(R.id.tv_nickname);
        relative_select = findViewById(R.id.relative_select);
        tvNickName.setText("王二小");
        tvTitle.setText(getResources().getString(R.string.welcome_come_in));
        tvQuit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        relative_select.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_quit:
                finish();
                startmActivity(SettingActivity.this,LoginActivity.class);
                break;
            case R.id.iv_back:
            case R.id.tv_back:
                finish();
                break;
            case R.id.relative_select:
                mPopWinDownStorage.show();
                break;
            case R.id.tv_setIp:
              /*  Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
                startActivity(wifiSettingsIntent);*/
            //  Intent intent =new Intent(SettingActivity.this,com.android.settings/.SubSettings);

                Intent intent = new Intent();
                ComponentName comp = new ComponentName("com.android.settings","com.android.settings.SubSettings");
                intent.setComponent(comp);
                intent.setAction("android.intent.action.VIEW");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private static class WorkHandler extends Handler {
        WeakReference<SettingActivity> mWeakReference;

        public WorkHandler(SettingActivity activity) {
            mWeakReference = new WeakReference<SettingActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final SettingActivity activity = mWeakReference.get();
            if (activity != null) {
                if (msg.what == GET_DATA_FAIL) {
                    activity.cancelLoading();
                    activity.tvLoadingTip.setText(activity.getResources().getString(R.string.sync_fail));
                }
                if(msg.what == GET_DATA_SUCCESS){
                    activity.cancelLoading();
                    activity.tvLoadingTip.setVisibility(View.GONE);
                    activity.ivLoading.setVisibility(View.GONE);
                    activity.relative_select.setVisibility(View.VISIBLE);
                    activity.mPopWinDownStorage = new PopWinDownUtil(activity, activity.relative_select, activity.mConfigureModelList, activity.mOnItemClickListener);
                }
            }
        }
    }

    //在异步方法中 调用
    private class ExcelDataLoader extends AsyncTask<String, Void, ArrayList<ConfigureModel>> {

        @Override
        protected void onPreExecute() {
            startLoading();
        }

        @Override
        protected ArrayList<ConfigureModel>doInBackground(String... params) {
           /* mConfigureModelList = DBUtils.queryConfigure(getApplicationContext());
            if(mConfigureModelList.size() == 0){
                getConfigure();
            }else{
                mWorkHandler.sendEmptyMessage(GET_DATA_SUCCESS);
            }*/
            getConfigure();
            return mConfigureModelList;
        }

        @Override
        protected void onPostExecute(ArrayList<ConfigureModel> models) {
            cancelLoading();
        }
    }


}

package com.socket.pad.paddemo.net;

import android.util.Log;

import com.socket.pad.paddemo.Utils.Contants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpManager {

    private static volatile OkhttpManager instance = null;
    private static final String TAG = "OkhttpManager";

    private OkHttpClient myOkHttpClient;

    public static OkhttpManager getInstance() {
 //       mContext = mContext;
        if (instance == null) {
            synchronized (OkhttpManager.class) {
                if (instance == null) {
                    instance = new OkhttpManager();
                }
            }
        }
        return instance;
    }

    private OkhttpManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        myOkHttpClient = builder.build();
    }

    /**
     * 获取配置信息
     * */
    public void getHttpConfigure(final HttpConnectCallback mHttpCallback, final String checkCode) {

        FormBody.Builder builder = new FormBody.Builder();
        RequestBody formBody = builder.add("checkcode",checkCode).build();
        Log.e("cfn",checkCode);
        final Request request = new Request.Builder().url(Contants.BASE_URL).post(formBody).build();
        Call call = myOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "get login status error ,");
                mHttpCallback.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "get login status success");
                mHttpCallback.onResponseSuccess(response.body().string());
            }
        });
    }

    /**
     * 请求显示数据
     * */
   /* public void getData(final HttpCallback mHttpCallback,String token) {
        JSONObject object =new JSONObject();
        try {
            object.put("time",TimeUtils.getDate());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = FormBody.create(MediaType.parse("application/json"),object.toString());
        final Request request = new Request.Builder().url(Constant.Url.GET_DATA_URL).
                addHeader("token",token).post(body).build();
        Call call = myOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "get data error ,");
                mHttpCallback.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
    //            Log.d(TAG, "get data success=="+response.body().string());
               mHttpCallback.onSuccess(response.body().string());
            }
        });
    }*/

}

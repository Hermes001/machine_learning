package com.socket.pad.paddemo.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.socket.pad.paddemo.Utils.GjsonUtils;
import com.socket.pad.paddemo.model.ConfigureModel;
import com.socket.pad.paddemo.model.RecInfoModel;

import java.util.ArrayList;

public class DBUtils {

    /*
    * 添加实验配置
    * */
    public static void addConfigure(Context mContext, ConfigureModel mConfigure)
    {
        Log.d("cfn","addConfigure");
        ContentValues  values = new ContentValues();
        if(mConfigure == null){
            return;
        }
        if(!TextUtils.isEmpty(mConfigure.getSy_id())){
            values.put(DBManager.CONFIGURE_SY_ID,mConfigure.getSy_id());
        }
        if(!TextUtils.isEmpty(mConfigure.getCj_no())){
            values.put(DBManager.CONFIGURE_CJ_NO,mConfigure.getCj_no());
        }
        if(!TextUtils.isEmpty(mConfigure.getCj_sbno())){
            values.put(DBManager.CONFIGURE_CJ_SBNO,mConfigure.getCj_sbno());
        }
        if(!TextUtils.isEmpty(mConfigure.getCj_title())){
            values.put(DBManager.CONFIGURE_CJ_TITLE,mConfigure.getCj_title());
        }
        if(!TextUtils.isEmpty(mConfigure.getCj_para())){
            values.put(DBManager.CONFIGURE_CJ_PARA,mConfigure.getCj_para());
        }
        if(!TextUtils.isEmpty(mConfigure.getIp())){
            values.put(DBManager.CONFIGURE_CJ_IP,mConfigure.getIp());
        }
        try {
            mContext.getContentResolver().insert(DBManager.CONFIGURE_URI, values);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<ConfigureModel> queryConfigure(Context mCongtext)
    {
        ArrayList<ConfigureModel> modelList =new ArrayList<>();
        String selection = null;
        String[] selectionArgs = null;
        Cursor cursor = mCongtext.getContentResolver().query(DBManager.CONFIGURE_URI, null, selection, selectionArgs, null);
        if (cursor == null) {
            return modelList;
        }
        while (cursor.moveToNext()) {
            ConfigureModel configureModel = new ConfigureModel();

            configureModel.setSy_id(cursor.getString(cursor.getColumnIndex(DBManager.CONFIGURE_SY_ID)));
            configureModel.setCj_no(cursor.getString(cursor.getColumnIndex(DBManager.CONFIGURE_CJ_NO)));
            configureModel.setCj_sbno(cursor.getString(cursor.getColumnIndex(DBManager.CONFIGURE_CJ_SBNO)));
            configureModel.setCj_title(cursor.getString(cursor.getColumnIndex(DBManager.CONFIGURE_CJ_TITLE)));
            configureModel.setCj_para(cursor.getString(cursor.getColumnIndex(DBManager.CONFIGURE_CJ_PARA)));
            configureModel.setIp(cursor.getString(cursor.getColumnIndex(DBManager.CONFIGURE_CJ_IP)));

            modelList.add(configureModel);
        }
        cursor.close();
        return modelList;
    }

    /*
    * 添加实验数据
    * */
    public static void addRecInfoData(Context mContext, RecInfoModel mRecInfoModel)
    {
        Log.d("cfn","addRecInfoData");
        ContentValues  values = new ContentValues();
        if(mRecInfoModel == null){
            return;
        }
        if(!TextUtils.isEmpty(mRecInfoModel.getXh())){
            values.put(DBManager.DATA_XH,mRecInfoModel.getXh());
        }
        if(!TextUtils.isEmpty(mRecInfoModel.getXn())){
            values.put(DBManager.DATA_XN,mRecInfoModel.getXn());
        }
        if(!TextUtils.isEmpty(GjsonUtils.listToJson(mRecInfoModel.getPercentList()))){
            values.put(DBManager.DATA_PERCENTLIST,GjsonUtils.listToJson(mRecInfoModel.getPercentList()));
        }
        values.put(DBManager.DATA_COEFFICIENT,mRecInfoModel.getCoefficient());
        values.put(DBManager.DATA_PERCENT_AVERAGE,mRecInfoModel.getPercentAverage());
        values.put(DBManager.DATA_PRESSURE_NUM,mRecInfoModel.getPressureNum());
        values.put(DBManager.DATA_STATUS,mRecInfoModel.getStatus());
        values.put(DBManager.DATA_TIME,mRecInfoModel.getTime());
        try {
            mContext.getContentResolver().insert(DBManager.DATA_URI, values);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<RecInfoModel> queryRecInfo(Context mCongtext)
    {
        ArrayList<RecInfoModel> modelList =new ArrayList<>();
        String selection = null;
        String[] selectionArgs = null;
        Cursor cursor = mCongtext.getContentResolver().query(DBManager.DATA_URI, null, selection, selectionArgs, null);
        if (cursor == null) {
            return modelList;
        }
        while (cursor.moveToNext()) {
            RecInfoModel model = new RecInfoModel();

            model.setXh(cursor.getString(cursor.getColumnIndex(DBManager.DATA_XH)));
            model.setXn(cursor.getString(cursor.getColumnIndex(DBManager.DATA_XN)));
            model.setPercentAverage(cursor.getInt(cursor.getColumnIndex(DBManager.DATA_PERCENT_AVERAGE)));
            model.setPressureNum(cursor.getInt(cursor.getColumnIndex(DBManager.DATA_PRESSURE_NUM)));
            model.setCoefficient(cursor.getInt(cursor.getColumnIndex(DBManager.DATA_COEFFICIENT)));
            model.setTime(cursor.getInt(cursor.getColumnIndex(DBManager.DATA_TIME)));
            model.setStatus(cursor.getInt(cursor.getColumnIndex(DBManager.DATA_STATUS)));
            model.setPercentList(GjsonUtils.jsonToList(cursor.getString(cursor.getColumnIndex(DBManager.DATA_PERCENTLIST))));

            modelList.add(model);
        }
        cursor.close();
        return modelList;
    }


    /*
    * 删除表中的数据
    * */
    public static void deleteData(Context mContext)
    {
        int rows = mContext.getContentResolver().delete(DBManager.DATA_URI, null, null);
    }


}

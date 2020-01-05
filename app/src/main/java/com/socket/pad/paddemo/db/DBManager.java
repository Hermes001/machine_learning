package com.socket.pad.paddemo.db;

import android.content.ContentResolver;
import android.net.Uri;

public final class DBManager {


    public static final String AUTHORITY = "com.socket.pad.db";
    public static final String BASE_DIR_TYPE = "vnd.android.cursor.dir/vnd.enterprise";


    public final static String CONFIGURE_CONTENT_TYPE = BASE_DIR_TYPE + ".configure";
    public final static String DATA_CONTENT_TYPE = BASE_DIR_TYPE + ".data";


    /*
     * 实验配置各属性
     * */
    public final static String CONFIGURE_SY_ID = "sy_id";
    public final static String CONFIGURE_CJ_NO = "cj_no";
    public final static String CONFIGURE_CJ_SBNO = "cj_sbno";
    public final static String CONFIGURE_CJ_TITLE = "cj_title";
    public final static String CONFIGURE_CJ_PARA = "cj_para";
    public final static String CONFIGURE_CJ_IP = "cj_ip";


    /*
    * 实验数据各属性
    * */
    public final static String DATA_PRESSURE_NUM="pressureNum";
    public final static String DATA_PERCENT_AVERAGE = "percentAverage";
    public final static String DATA_STATUS = "status";
    public final static String DATA_COEFFICIENT ="coefficient";
    public final static String DATA_TIME = "time";
    public final static String DATA_XN = "xn";
    public final static String DATA_XH = "xh";
    public final static String DATA_PERCENTLIST = "percentList";


    /*
    * 实验配置表名
    * */
    public final static String CONFIGURE_TABLE_NAME = "table_configure";

    /*
    * 实验数据表名
    * */
    public final static String DATA_TABLE_NAME = "table_data";


    /**
     * Uri for user provider.
     */
    public static final Uri CONFIGURE_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://"
            + AUTHORITY + "/"
            + CONFIGURE_TABLE_NAME);
    /**
     * Base uri for a specific user. Should be appended with id of the user.
     */
    public static final Uri CONFIGURE_ID_URI_BASE = Uri.parse(ContentResolver.SCHEME_CONTENT + "://"
            + AUTHORITY + "/"
            + CONFIGURE_TABLE_NAME + "/");


    /**
     * Uri for user provider.
     */
    public static final Uri DATA_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://"
            + AUTHORITY + "/"
            + DATA_TABLE_NAME);
    /**
     * Base uri for a specific user. Should be appended with id of the user.
     */
    public static final Uri DATA_ID_URI_BASE = Uri.parse(ContentResolver.SCHEME_CONTENT + "://"
            + AUTHORITY + "/"
            + DATA_TABLE_NAME + "/");
}

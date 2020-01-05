package com.socket.pad.paddemo.ui;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.socket.pad.paddemo.R;
import com.socket.pad.paddemo.Utils.CustomLineChart;
import com.socket.pad.paddemo.Utils.CustomLineChartRenderer;
import com.socket.pad.paddemo.Utils.LineChartUtils;
import com.socket.pad.paddemo.Utils.ToolsUtils;
import com.socket.pad.paddemo.adapter.RealTimeDataAdapter;
import com.socket.pad.paddemo.db.DBManager;
import com.socket.pad.paddemo.db.DBUtils;
import com.socket.pad.paddemo.model.RecInfoModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RealTimeActivity extends BaseActivity implements View.OnClickListener {

    private final static int GET_DATA_SUCCESS = 0x200;
    private TextView tvTime;
    private ListView lv_realtime_data;
    private RealTimeDataAdapter mRealTimeDataAdapter;
    private ArrayList<RecInfoModel> mTestModelList = new ArrayList<>();
    private CustomLineChart lineChart_sq;
    private CustomLineChart lineChart_slgt;
    private DataChangedObserver mObserver;
    private static ExecutorService mExecutorService;
    private WorkHandler mWorkHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);
        initView();
        setData();
        mObserver = new DataChangedObserver(new Handler());
        getContentResolver().registerContentObserver(DBManager.DATA_URI, true, mObserver);
        mExecutorService = Executors.newSingleThreadExecutor();
        mWorkHandler = new WorkHandler(RealTimeActivity.this);
    }

    private void initView() {
        initTitle();
    }

    private void setData() {
        // x轴数据源
        List<String> xDataList = new ArrayList<>();
        // y轴数据数据源
        List<Entry> yDataList1 = new ArrayList<>();
        List<Entry> yDataList2 = new ArrayList<>();
        List<Entry> yDataList3 = new ArrayList<>();
        //给上面的X、Y轴数据源做假数据测试
        for (int i = 0; i < 10; i++) {
            // x轴显示的数据
            xDataList.add(i + ":00");
            //y轴生成float类型的随机数
            float value1 = (float) (Math.random() * 50) + 3;
            yDataList1.add(new Entry(i, value1));
            float value2 = (float) (Math.random() * 50) + 3;
            yDataList2.add(new Entry(i, value2));
            float value3 = (float) (Math.random() * 50) + 3;
            yDataList3.add(new Entry(i, value3));
        }

        //显示图表,参数（ 上下文，图表对象， X轴数据，Y轴数据，图表标题，曲线图例名称，坐标点击弹出提示框中数字单位）
        LineChartUtils.showLineChar(this, lineChart_sq, xDataList, yDataList1, "", "", "mm/kn");
        LineChartUtils.showLineChar(this, lineChart_slgt, xDataList, yDataList2, "", " ", "mm/kn");
        ((CustomLineChartRenderer)lineChart_slgt.getRenderer()).addPressValue("1000kN");
        LineChartUtils.showLineChar(this, lineChart_slgt, xDataList, yDataList3, "", " ", "mm/kn");
        ((CustomLineChartRenderer)lineChart_slgt.getRenderer()).addPressValue("2000kN");
    }

   /* private void setData()
    {
        // x轴数据源
        List<String> xDataList = new ArrayList<>();
        // y轴数据数据源
        List<Entry> yDataList = new ArrayList<>();
        //给上面的X、Y轴数据源做假数据测试
        for (int i = 0; i < 10; i++) {
            // x轴显示的数据
            //   xDataList.add(i + ":00");
            //y轴生成float类型的随机数
            float value = (float) (Math.random() * 100) + 3;
            yDataList.add(new Entry(i, value));
            yDataList.add(new Entry(i, value+10));
        }

        //显示图表,参数（ 上下文，图表对象， X轴数据，Y轴数据，图表标题，曲线图例名称，坐标点击弹出提示框中数字单位）
        LineChartUtils.showLineChar(this, lineChart_sq, xDataList, yDataList, "", "位移/压力值","mm/kn");

    }*/

  /*  private void setData()
    {
        // x轴数据源
        List<String> xDataList = new ArrayList<>();
        // y轴数据数据源
        List<Entry> yDataList = new ArrayList<>();
        //给上面的X、Y轴数据源做假数据测试
        for (int i = 0; i < 10; i++) {
            // x轴显示的数据
            xDataList.add(i + ":00");
            //y轴生成float类型的随机数
            float value = (float) (Math.random() * 100) + 3;
            yDataList.add(new Entry(i, value));
        }

        //显示图表,参数（ 上下文，图表对象， X轴数据，Y轴数据，图表标题，曲线图例名称，坐标点击弹出提示框中数字单位）
        LineChartUtils.showLineChar(this, lineChart_sq, xDataList, yDataList, "", "位移/压力值","mm/kn");

    }*/

    private void initTitle() {
        ivBack = findViewById(R.id.iv_back);
        tvBack = findViewById(R.id.tv_back);
        tvQuit = findViewById(R.id.tv_quit);
        tvTitle = findViewById(R.id.tv_title);
        ivHead = findViewById(R.id.iv_head);
        tvNickName = findViewById(R.id.tv_nickname);
        tvTime = findViewById(R.id.tv_time);
        lv_realtime_data = findViewById(R.id.lv_realtime_data);
        lineChart_sq = findViewById(R.id.lineChart_sq);
        lineChart_slgt = findViewById(R.id.lineChart_slgt);

        tvTime.setText(ToolsUtils.getTime() + getResources().getString(R.string.real_time_test_data));
        tvNickName.setText("王二小");
        tvTitle.setText(getResources().getString(R.string.welcome_come_in));

        tvQuit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvBack.setOnClickListener(this);

        ArrayList<Integer> percentList = new ArrayList<>();
        percentList.add(12);
        percentList.add(145);

        RecInfoModel a = new RecInfoModel();
        RecInfoModel b = new RecInfoModel();
        b.setXh("1");
        b.setPercentList(percentList);
        RecInfoModel c = new RecInfoModel();
        c.setXh("2");
        c.setPercentList(percentList);
        RecInfoModel d = new RecInfoModel();
        d.setXh("3");
        d.setPercentList(percentList);
        RecInfoModel e = new RecInfoModel();
        e.setXh("4");
        e.setPercentList(percentList);
        RecInfoModel f = new RecInfoModel();
        f.setXh("5");
        f.setPercentList(percentList);
        mTestModelList.add(a);
        mTestModelList.add(b);
        mTestModelList.add(c);
        mTestModelList.add(d);
        mTestModelList.add(e);
        mTestModelList.add(f);


        mRealTimeDataAdapter = new RealTimeDataAdapter(this, mTestModelList);
        lv_realtime_data.setAdapter(mRealTimeDataAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_quit:
                finish();
                startmActivity(RealTimeActivity.this, LoginActivity.class);
                break;
            case R.id.iv_back:
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }
    }

    private static class WorkHandler extends Handler {
        WeakReference<RealTimeActivity> mWeakReference;

        public WorkHandler(RealTimeActivity activity) {
            mWeakReference = new WeakReference<RealTimeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final RealTimeActivity activity = mWeakReference.get();
            if (activity != null) {
                if (msg.what == GET_DATA_SUCCESS) {
                    activity.mTestModelList = (ArrayList<RecInfoModel>) msg.obj;
                    activity.mRealTimeDataAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private final class DataChangedObserver extends ContentObserver {
        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public DataChangedObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    ArrayList<RecInfoModel> list = DBUtils.queryRecInfo(RealTimeActivity.this);
                    Message msg = mWorkHandler.obtainMessage();
                    msg.what = GET_DATA_SUCCESS;
                    msg.obj = list;
                    mWorkHandler.sendMessage(msg);
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mExecutorService.isShutdown()) {
            mExecutorService.shutdown();
        }
    }
}

package com.socket.pad.paddemo.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socket.pad.paddemo.R;
import com.socket.pad.paddemo.model.RecInfoModel;
import com.socket.pad.paddemo.model.TestModel;

import java.util.ArrayList;

public class RealTimeDataAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<RecInfoModel> list = new ArrayList<>();
    /*
    * 表个数
    * */
    private int watchSum=0;


    public RealTimeDataAdapter(Context mContext,ArrayList<RecInfoModel> list)
    {
        this.mContext = mContext;
        this.list = list;
        watchSum = list.get(1).getPercentList().size();
    }

    @Override
    public int getCount() {
        if(list == null){
            return 0;
        }else{
            return list.size();
        }

    }

    @Override
    public Object getItem(int position) {
        if(list == null){
            return null;
        }else{
            return list.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_realtime_data, null, true);
            mHolder.tv_no = convertView.findViewById(R.id.tv_no);
            mHolder.tv_status = convertView.findViewById(R.id.tv_status);
            mHolder.tv_presure = convertView.findViewById(R.id.tv_presure);
            mHolder.tv_time = convertView.findViewById(R.id.tv_time);
            /*mHolder.tv_watch1 = convertView.findViewById(R.id.tv_watch1);
            mHolder.tv_watch2 = convertView.findViewById(R.id.tv_watch2);
            mHolder.tv_watch3 = convertView.findViewById(R.id.tv_watch3);
            mHolder.tv_watch4 = convertView.findViewById(R.id.tv_watch4);*/
            mHolder.linear_percent_watch = convertView.findViewById(R.id.linear_percent_wtach);
            addTextView(mHolder.linear_percent_watch,position);
            mHolder.tv_average = convertView.findViewById(R.id.tv_average);
            mHolder.tv_coefficient = convertView.findViewById(R.id.tv_coefficient);
            mHolder.ll_root = convertView.findViewById(R.id.ll_root);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        if(position == 0){
            mHolder.tv_no.setText("序号");
//        mHolder.tv_status.setText(list.get(position).getStatus());
            mHolder.tv_presure.setText("压力值");
            mHolder.tv_time.setText("时间");
            mHolder.tv_average.setText("平均(mm)");
            mHolder.tv_coefficient.setText("系数");
        }else{
            mHolder.tv_no.setText(list.get(position).getXh());
//        mHolder.tv_status.setText(list.get(position).getStatus());
            mHolder.tv_presure.setText(list.get(position).getPressureNum()+"");
            mHolder.tv_time.setText(list.get(position).getTime()+"");
            mHolder.tv_average.setText(list.get(position).getPercentAverage()+"");
            mHolder.tv_coefficient.setText("");

            if(position%2 ==0){
                mHolder.ll_root.setBackground(mContext.getDrawable(R.color.colorAccent));
            }else{
                mHolder.ll_root.setBackground(mContext.getDrawable(R.color.colorPrimary));
            }
        }

        return convertView;
    }

    private void addTextView(LinearLayout linear_percent_watch,int postion)
    {
        for(int i=0;i<watchSum;i++){
            TextView tv_watch1 = new TextView(mContext);
     //       tv_watch1.setText(i+1+mContext.getString(R.string.percent_no));
            tv_watch1.setTextSize(12);
            tv_watch1.setTextColor(mContext.getColor(R.color.white));
            tv_watch1.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( 140, 60);
            linear_percent_watch.addView(tv_watch1,lp);

            ImageView imageView = new ImageView(mContext);
            imageView.setBackgroundColor(mContext.getColor(R.color.white));
            LinearLayout.LayoutParams ivLp = new LinearLayout.LayoutParams( 2, 60);
            linear_percent_watch.addView(imageView,ivLp);
            if(postion==0){
                tv_watch1.setText(i+1+mContext.getString(R.string.percent_no));
            }else{
                tv_watch1.setText(list.get(postion).getPercentList().get(i)+"");
            }
        }

    }

    class ViewHolder {
        private TextView tv_no;
        private TextView tv_status;
 //       private TextView tv_load;
        private TextView tv_presure;
        private TextView tv_time;
       /* private TextView tv_watch1;
        private TextView tv_watch2;
        private TextView tv_watch3;
        private TextView tv_watch4;*/
        private TextView tv_average;
        private TextView tv_coefficient;
        private LinearLayout ll_root;
        private LinearLayout linear_percent_watch;
    }
}

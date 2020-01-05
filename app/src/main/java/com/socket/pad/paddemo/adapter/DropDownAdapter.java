package com.socket.pad.paddemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.socket.pad.paddemo.R;
import com.socket.pad.paddemo.model.ConfigureModel;

import java.util.ArrayList;

public class DropDownAdapter extends BaseAdapter {

    private ArrayList<ConfigureModel> drops;
    private Context context;
    public DropDownAdapter(ArrayList<ConfigureModel> drops, Context context){
        this.drops = drops;
        this.context = context;
    }
    @Override
    public int getCount() {
        return drops.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.dropdown_item,parent,false);
            holder.tvSelect = (TextView) convertView.findViewById(R.id.tv_select);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvSelect.setText(drops.get(position).getCj_title());
        return convertView;
    }
    private class ViewHolder{
        TextView tvSelect;
    }

}

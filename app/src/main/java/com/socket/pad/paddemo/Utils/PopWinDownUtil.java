package com.socket.pad.paddemo.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.socket.pad.paddemo.R;
import com.socket.pad.paddemo.adapter.DropDownAdapter;
import com.socket.pad.paddemo.model.ConfigureModel;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PopWinDownUtil {

    private Context context;
    private View contentView;
    private View relayView;
    private PopupWindow popupWindow;
    private ListView lvSelect;
    private ArrayList<ConfigureModel> dropList=new ArrayList<>();
    private DropDownAdapter dropDownAdapter;
    private AdapterView.OnItemClickListener onItemClickListener;
    public PopWinDownUtil(Context context, View relayView, ArrayList<ConfigureModel>dropList, AdapterView.OnItemClickListener onItemClickListener){
        this.context = context;
        this.relayView = relayView;
        this.dropList=dropList;
        this.onItemClickListener=onItemClickListener;
        dropDownAdapter=new DropDownAdapter(dropList,context);
        init();
    }

    public DropDownAdapter getDropDownAdapter()
    {
        return  dropDownAdapter;
    }
    public void init(){
        LayoutInflater mLayoutInflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        contentView= mLayoutInflater.inflate(
                R.layout.popwindow, null);
        initData();
        //内容，高度，宽度
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //动画效果
 //       popupWindow.setAnimationStyle(R.style.AnimationTopFade);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOutsideTouchable(true);
        //关闭事件
    //    popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(onDismissLisener != null){
                    onDismissLisener.onDismiss();
                }
            }
        });
    }

    private void initData()
    {
        lvSelect=contentView.findViewById(R.id.lv_pop);
        if(null!=dropList&&dropList.size()>3)
        {
            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) lvSelect.getLayoutParams();
            params.height=250;
            lvSelect.setLayoutParams(params);
        }
        if(null!=dropList&&dropList.size() == 2)
        {
            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) lvSelect.getLayoutParams();
            params.height=120;
            lvSelect.setLayoutParams(params);
        }
        lvSelect.setAdapter(dropDownAdapter);
        lvSelect.setOnItemClickListener(onItemClickListener);
    }
    public void show(){
        //显示位置
        popupWindow.showAsDropDown(relayView);
    }
    public void hide(){
        if(popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }

    private OnDismissLisener onDismissLisener;
    public void setOnDismissListener(OnDismissLisener onDismissLisener){
        this.onDismissLisener = onDismissLisener;
    }
    public interface OnDismissLisener{
        void onDismiss();
    }
    public boolean isShowing(){
        return popupWindow.isShowing();
    }
}

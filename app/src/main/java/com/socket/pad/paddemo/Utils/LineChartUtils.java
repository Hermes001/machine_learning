package com.socket.pad.paddemo.Utils;

import android.content.Context;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class LineChartUtils {

    /**
     * 显示图表
     * @param mContext
     *            上下文
     * @param lineChart
     *            图表对象
     * @param xDataList
     *            X轴数据
     * @param yDataList
     *            Y轴数据
     * @param title
     *            图表标题（如：XXX趋势图）
     * @param curveLable
     *            曲线图例名称（如：--用电量/时间）
     * @param unitName
     *            坐标点击弹出提示框中数字单位（如：KWH）
     */
  //  https://blog.csdn.net/x541211190/article/details/77389121/

    public static void showLineChar(Context mContext, CustomLineChart lineChart, List<String> xDataList,
                                    List<Entry> yDataList, String title, String curveLable, String unitName)
    {
        // 设置数据
 //       lineChart.setData(setLineData(mContext, xDataList, yDataList, curveLable));
        if(lineChart.getLineData()==null){
            lineChart.setData(setLineData(mContext, xDataList, yDataList, curveLable));
        }else{
            lineChart.getLineData().addDataSet(setLineDataSet(mContext, xDataList, yDataList, curveLable));
        }
        // 是否在折线图上添加边框
        lineChart.setDrawBorders(true);
        // 曲线描述 -标题
        Description description = new Description();
        description.setText(title);
        lineChart.setDescription(description);
        // 标题字体大小
 //       lineChart.setDescriptionTextSize(16f);
        // 如果没有数据的时候，会显示这个，类似文本框的placeholder
        lineChart.setNoDataText("暂无数据");
        // 是否显示表格颜色
        lineChart.setDrawGridBackground(false);
        // 禁止绘制图表边框的线
        lineChart.setDrawBorders(false);
        // 设置是否启动触摸响应
        lineChart.setTouchEnabled(false);
        // 是否可以拖拽
        lineChart.setDragEnabled(false);
        // 是否可以缩放
        lineChart.setScaleEnabled(true);
        // 如果禁用，可以在x和y轴上分别进行缩放
        lineChart.setPinchZoom(false);
        lineChart.getLineData().getDataSets();

        Legend legend = lineChart.getLegend();
        if ("".equals(curveLable)){
            legend.setEnabled(false);
        }else {
            legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            legend.setForm(Legend.LegendForm.EMPTY);
            legend.setFormSize(30);
        }

        // 隐藏右侧Y轴（只在左侧的Y轴显示刻度）
        lineChart.getAxisRight().setEnabled(false);

        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.setAxisMinimum(0);
        leftYAxis.setSpaceTop(0);
        leftYAxis.setInverted(true);

        XAxis xAxis = lineChart.getXAxis();
        // 显示X轴上的刻度值
        xAxis.setDrawLabels(true);
        // 设置X轴的数据显示在报表的上方
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        // 设置不从X轴发出纵向直线
        xAxis.setDrawGridLines(false);
    }

    public static LineDataSet setLineDataSet(Context mContext, List<String> xDataList, List<Entry> yDataList, String curveLable)
    {
        // LineDataSet表示一条曲线数据对象
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        // y轴的数据集合
        LineDataSet lineDataSet = new LineDataSet(yDataList, curveLable);
        // 不显示坐标点的数据
        lineDataSet.setDrawValues(false);
        // 显示坐标点的小圆点
        lineDataSet.setDrawCircles(true);
        // 定位线
        lineDataSet.setHighlightEnabled(true);
        // 线宽
        lineDataSet.setLineWidth(2.0f);
        // 显示的圆形大小
        lineDataSet.setCircleSize(4f);
        // 显示颜色
        /*lineDataSet.setColor(context.getApplicationContext().getResources().getColor(R.color.bg_blue));
        // 圆形的颜色
        lineDataSet.setCircleColor(context.getApplicationContext().getResources().getColor(R.color.bg_blue));
        // 高亮的线的颜色
         lineDataSet.setHighLightColor(context.getApplicationContext().getResources() .getColor(R.color.text_yellow));
        // 设置坐标点的颜色
        lineDataSet.setFillColor(context.getApplicationContext().getResources().getColor(R.color.bg_blue));*/
        // 设置坐标点为空心环状
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setFillAlpha(65);
        // 设置显示曲线和X轴围成的区域阴影
        lineDataSet.setDrawFilled(true);
        // 坐标轴在左侧
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setValueTextSize(14f);
        // 曲线弧度（区间0.05f-1f，默认0.2f）
        lineDataSet.setCubicIntensity(0.2f);
        // 设置为曲线显示,false为折线setDrawFilled
    //    lineDataSet.setDrawCubic(true);
        lineDataSet.setDrawFilled(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSets.add(lineDataSet);
        // y轴的数据
        LineData lineData = new LineData(lineDataSet);
        return lineDataSet;
    }


    public static LineData setLineData(Context mContext, List<String> xDataList, List<Entry>yDataList, String curveLable)
    {
        // LineDataSet表示一条曲线数据对象
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        // y轴的数据集合
        LineDataSet lineDataSet = new LineDataSet(yDataList, curveLable);
        // 不显示坐标点的数据
        lineDataSet.setDrawValues(false);
        // 显示坐标点的小圆点
        lineDataSet.setDrawCircles(true);
        // 定位线
        lineDataSet.setHighlightEnabled(true);
        // 线宽
        lineDataSet.setLineWidth(2.0f);
        // 显示的圆形大小
        lineDataSet.setCircleSize(4f);
        // 显示颜色
        /*lineDataSet.setColor(context.getApplicationContext().getResources().getColor(R.color.bg_blue));
        // 圆形的颜色
        lineDataSet.setCircleColor(context.getApplicationContext().getResources().getColor(R.color.bg_blue));
        // 高亮的线的颜色
         lineDataSet.setHighLightColor(context.getApplicationContext().getResources() .getColor(R.color.text_yellow));
        // 设置坐标点的颜色
        lineDataSet.setFillColor(context.getApplicationContext().getResources().getColor(R.color.bg_blue));*/
        // 设置坐标点为空心环状
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setFillAlpha(65);
        // 设置显示曲线和X轴围成的区域阴影
        lineDataSet.setDrawFilled(true);
        // 坐标轴在左侧
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setValueTextSize(14f);
        // 曲线弧度（区间0.05f-1f，默认0.2f）
        lineDataSet.setCubicIntensity(0.2f);
        // 设置为曲线显示,false为折线setDrawFilled
        //    lineDataSet.setDrawCubic(true);
        lineDataSet.setDrawFilled(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSets.add(lineDataSet);
        // y轴的数据
        LineData lineData = new LineData(lineDataSet);
        return lineData;
    }


}

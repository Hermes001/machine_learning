package com.socket.pad.paddemo.Utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

public class CustomLineChartRenderer extends LineChartRenderer {
    private List<String> pressValues = new ArrayList<>();

    public CustomLineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void drawValues(Canvas c) {
        super.drawValues(c);
        Paint paint =new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿画笔
        paint.setTextSize(Utils.convertDpToPixel(8));//设置字体大小
        paint.setColor(Color.BLACK);
        for (int i = 0,size = pressValues.size();i<size;i++){
            LineDataSet lineDataSet = (LineDataSet) mChart.getLineData().getDataSets().get(i);
            Transformer trans =mChart.getTransformer(lineDataSet.getAxisDependency());
            String label = lineDataSet.getLabel();
            MPPointD minPoint = trans.getPixelForValues(lineDataSet.getValues().get(lineDataSet.getValues().size()-1).getX(),lineDataSet.getValues().get(lineDataSet.getValues().size()-1).getY());

            float x = (float)minPoint.x;

            float y = (float)minPoint.y;
            c.drawText(pressValues.get(i),x + Utils.convertDpToPixel(10),y + Utils.convertDpToPixel(3),paint);
        }
    }

    public void addPressValue(String press){
        pressValues.add(press);
    }

}

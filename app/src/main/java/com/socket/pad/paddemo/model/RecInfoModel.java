package com.socket.pad.paddemo.model;

import java.util.ArrayList;
import java.util.List;

public class RecInfoModel  {

    /*
    * 压力表的值,以0.1MPA为单位
    * */
    private int pressureNum;

    /*
    * 百分表值
    * */
    ArrayList<Integer> percentList = new ArrayList<>();

    private int percentAverage;

    private int status;

    /*
    * 系数，暂时为空
    * */
    private int  coefficient;

    private int time;

    /*
    * 机器编码
    * */
    private String xn;

    /*
    * 序号
    * */
    private String xh;


    /*
    * 1 上载状态 ，2 上载记录
    * */
    int flag ;

    @Override
    public String toString() {

        return "recv mode is pressureNum="+pressureNum+" percentList.size= "+percentList.size()
                +"  percentAverage = "+percentAverage+" time"+time+"  xn=="+xn+"  xh="+xh +" flag= "+flag+
                "status = "+status;
    }

    public String getXn() {
        return xn;
    }

    public void setXn(String xn) {
        this.xn = xn;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public int getPressureNum() {
        return pressureNum;
    }

    public void setPressureNum(int pressureNum) {
        this.pressureNum = pressureNum;
    }

    public int getPercentAverage() {
        return percentAverage;
    }

    public void setPercentAverage(int percentAverage) {
        this.percentAverage = percentAverage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ArrayList<Integer> getPercentList() {
        return percentList;
    }

    public void setPercentList(ArrayList<Integer> percentList) {
        this.percentList = percentList;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}

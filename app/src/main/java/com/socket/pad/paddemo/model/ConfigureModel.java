package com.socket.pad.paddemo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ConfigureModel implements Parcelable {

    /*
    * 实验ID
    * */
    private String sy_id;

    /*
     * 实验编号
     * */
    private String cj_no;

    /*
     * 设备编号
     * */
    private String cj_sbno;

    /*
     * 实验名称
     * */
    private String cj_title;

    /*
     * 实验参数
     * */
    private String cj_para;

    /*
     * ip
     * */
    private String cj_ip;

    protected ConfigureModel(Parcel in) {
        sy_id = in.readString();
        cj_no = in.readString();
        cj_sbno = in.readString();
        cj_title = in.readString();
        cj_para = in.readString();
        cj_ip = in.readString();
    }

    public ConfigureModel(){

    }

    public static final Creator<ConfigureModel> CREATOR = new Creator<ConfigureModel>() {
        @Override
        public ConfigureModel createFromParcel(Parcel in) {
            return new ConfigureModel(in);
        }

        @Override
        public ConfigureModel[] newArray(int size) {
            return new ConfigureModel[size];
        }
    };

    public String getSy_id() {
        return sy_id;
    }

    public void setSy_id(String sy_id) {
        this.sy_id = sy_id;
    }

    public String getCj_no() {
        return cj_no;
    }

    public void setCj_no(String cj_no) {
        this.cj_no = cj_no;
    }

    public String getCj_sbno() {
        return cj_sbno;
    }

    public void setCj_sbno(String cj_sbno) {
        this.cj_sbno = cj_sbno;
    }

    public String getCj_title() {
        return cj_title;
    }

    public void setCj_title(String cj_title) {
        this.cj_title = cj_title;
    }

    public String getCj_para() {
        return cj_para;
    }

    public void setCj_para(String cj_para) {
        this.cj_para = cj_para;
    }

    public String getIp() {
        return cj_ip;
    }

    public void setIp(String ip) {
        this.cj_ip = ip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sy_id);
        dest.writeString(cj_no);
        dest.writeString(cj_sbno);
        dest.writeString(cj_title);
        dest.writeString(cj_para);
        dest.writeString(cj_ip);
    }
}



/*
* "num": 1,
	"meg": "\u83b7\u53d6\u6210\u529f\uff01",
	"info": [{
		"sy_id": "1",
		"cj_no": "20190215001",
		"cj_sbno": "1001",
		"cj_title": "\u5927\u540d\u8def\u9759\u8f7d\u8bd5\u9a8c",
		"cj_para": "01 01 01 02 1e 5a 03 3c 0f 05 28 02 18 50 50 50 50"
	}, {
		"sy_id": "2",
		"cj_no": "20190215002",
		"cj_sbno": "1001",
		"cj_title": "\u9759\u8f7d\u8bd5\u9a8c",
		"cj_para": "01 01 01 02 1e 5a 03 3c 0f 05 29 02 18 50 50 50 50"
	}]
*
* */
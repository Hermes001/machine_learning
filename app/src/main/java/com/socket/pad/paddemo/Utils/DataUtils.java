package com.socket.pad.paddemo.Utils;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.socket.pad.paddemo.model.ConfigureModel;
import com.socket.pad.paddemo.model.RecInfoModel;
import com.socket.pad.paddemo.model.TestModel;

import java.util.List;

public class DataUtils {

    /*
     * 基础指令的head
     * */
    public static final String HEAD_BASE_COMMAND = "37";

    public static final String HEAD_RECINFO_DATA = "37";

    public static String getBaseCommand(ConfigureModel model, boolean start) {
        StringBuilder command = new StringBuilder();
        command.append(HEAD_BASE_COMMAND);
        command.append("0c");
       /* command.append(Integer.toHexString(TimeUtils.getYear()));
        command.append(Integer.toHexString(TimeUtils.getMonth()));
        command.append(ByteUtils.IntTo4Hex(TimeUtils.getDay()));*/
        command.append(model.getCj_no());
        if (start) {
            command.append("00");
        } else {
            command.append("01");
        }
        int sum = 0;
        byte[] bytes = ByteUtils.hexString2Bytes(command.toString().replaceAll(" ",""));
        for(int i=0;i<bytes.length;i++){
            sum = sum+bytes[i];
        }
        command.append(Integer.toHexString((sum%256)&0xff));
        Log.e("cfn","basecommand =="+command.toString());
        return command.toString();
    }

    /*
    * 开始、暂停、结束指令
    *1-开始  0-结束 2-暂停
    * */
    public static String getStartCommand(int flag)
    {
        StringBuilder command = new StringBuilder();
        command.append(HEAD_BASE_COMMAND+" ");
        command.append("05 ");
        command.append("b0 ");
        command.append("0"+flag+" ");

        int sum = 0;
        byte[] bytes = ByteUtils.hexString2Bytes(command.toString().replaceAll(" ",""));
        for(int i=0;i<bytes.length;i++){
            sum = sum+bytes[i];
        }
        command.append(Integer.toHexString((sum%256)&0xff));
        Log.e("cfn","StartCommand =="+command.toString());
        return command.toString();
    }
    /*
    * 配置指令
    * */
    public static  String getConfigureCommand(ConfigureModel model)
    {
        StringBuilder command = new StringBuilder();
        command.append(HEAD_BASE_COMMAND+" ");
        int longByte = (model.getCj_para().replace(" ","")).length()/2+4;
        command.append(Integer.toHexString(longByte)+" ");
        command.append("b2 ");
        command.append(model.getCj_para()+" ");

        int sum = 0;
        byte[] bytes = ByteUtils.hexString2Bytes(command.toString().replaceAll(" ",""));
        for(int i=0;i<bytes.length;i++){
            sum = sum+bytes[i];
        }
        command.append(Integer.toHexString((sum%256)&0xff));
        Log.e("cfn","basecommand =="+command.toString());
        return command.toString();
    }

    /*
    * 上载记录的ack
    * */
    public static String getRecInfoAck(boolean isCorrect,RecInfoModel mRecInfoModel)
    {
        StringBuilder ack = new StringBuilder();
        ack.append(HEAD_BASE_COMMAND+" ");
        ack.append("08 ");
        ack.append("ab ");
        ack.append(mRecInfoModel.getXn().substring(0,2)+" "+mRecInfoModel.getXn().substring(2,4)+" ");
        ack.append(mRecInfoModel.getXh()+" ");
        if (isCorrect) {
            ack.append("01 ");
        } else {
            ack.append("00 ");
        }
        int sum = 0;
        byte[] bytes = ByteUtils.hexString2Bytes(ack.toString().replaceAll(" ",""));
        for(int i=0;i<bytes.length;i++){
            sum = sum+bytes[i];
        }
        ack.append(Integer.toHexString((sum%256)&0xff));
        return ack.toString();
    }

    public static boolean isAck(String ack)
    {
        byte[] bytes =ByteUtils.hexString2Bytes(ack);
            if(bytes[bytes.length-2] ==1){
                return true;
        }
        return false;
    }

    /*
    * 基础指令的ack
    * */
    public static boolean isConfigureCommandAck(String ack)
    {

        byte[] bytes =ByteUtils.hexString2Bytes(ack);
        if(bytes[bytes.length-2] ==1&&bytes[2]==12){
            return true;
        }
        return false;
    }

    /*
    * 开始、结束、暂停的ack
    * */
    public static boolean isStartCommandAck(String ack)
    {
        ack = ack.replaceAll(" ","");
        byte[] bytes =ByteUtils.hexString2Bytes(ack);
        if(bytes[bytes.length-2] ==1&&"b1".equals(bytes[2])){
            return true;
        }
        return false;
    }
    public static RecInfoModel praseRecInfoModel(String data)
    {
        //固定字节数（除了百分表）
        final int index = 21;
        final int sum = 22;
        //表总值
        int percentTotal = 0;
        RecInfoModel mRecInfoModel =new RecInfoModel();
        if(TextUtils.isEmpty(data)){
            return null;
        }
        data = data.replaceAll(" ","");
        byte[] bytes =ByteUtils.hexString2Bytes(data);
        if(bytes[0]==55&&bytes.length>22){
            if(bytes.length == Integer.valueOf(ByteUtils.bytes2HexString(bytes[1]),16)){
         //       Log.e("cfn",bytes[6]*256+bytes[7]+"");
                if((bytes[2]&0xFF)==168)
                {
                    mRecInfoModel.setFlag(1);
                    int percentSum = (bytes.length - sum)/4;
                    for(int i=0; i<percentSum;i++){
                        int percent =(ByteUtils.byteToInt(bytes[index+4*i])+
                                ByteUtils.byteToInt(bytes[index+4*i+1])*256+
                                ByteUtils.byteToInt(bytes[index+4*i+2])*256*256+
                                ByteUtils.byteToInt(bytes[index+4*i+3])*256*256*256)/8;
                        percentTotal = percentTotal +percent;
                        mRecInfoModel.getPercentList().add(percent);
                    }

                    //压力表的值
                    mRecInfoModel.setPressureNum(ByteUtils.byteToInt(bytes[17])+ByteUtils.byteToInt(bytes[18])*256);
                    mRecInfoModel.setTime(ByteUtils.byteToInt(bytes[5])*60*60+ByteUtils.byteToInt(bytes[6])*60+ByteUtils.byteToInt(bytes[7]));
                    mRecInfoModel.setPercentAverage(percentTotal/mRecInfoModel.getPercentList().size());
                    mRecInfoModel.setXn(data.substring(6,10));
             //       mRecInfoModel.setXh(data.substring(10,12));
                    mRecInfoModel.setStatus(bytes[8]);
                }
                if((bytes[2]&0xFF)==170)
                {
                    mRecInfoModel.setFlag(2);
                    int percentSum = (bytes.length - sum)/4;
                    for(int i=0; i<percentSum;i++){
                        int percent =(ByteUtils.byteToInt(bytes[index+4*i])+
                                ByteUtils.byteToInt(bytes[index+4*i+1])*256+
                                ByteUtils.byteToInt(bytes[index+4*i+2])*256*256+
                                ByteUtils.byteToInt(bytes[index+4*i+3])*256*256*256)/8;
                        percentTotal = percentTotal +percent;
                        mRecInfoModel.getPercentList().add(percent);
                    }

                    //压力表的值
                    mRecInfoModel.setPressureNum(ByteUtils.byteToInt(bytes[17])+ByteUtils.byteToInt(bytes[18])*256);
                    mRecInfoModel.setTime(ByteUtils.byteToInt(bytes[7])+ByteUtils.byteToInt(bytes[8])*256);
                    mRecInfoModel.setPercentAverage(percentTotal/mRecInfoModel.getPercentList().size());
                    mRecInfoModel.setXn(data.substring(6,10));
                    mRecInfoModel.setXh(data.substring(10,12));
                    mRecInfoModel.setStatus(bytes[6]);
                }

            }else{
                return null;
            }
        }else{
            Log.e("cfn","数据head不正确  head =="+bytes[0]);
            return null;
        }

        Log.e("cfn",mRecInfoModel.toString());
        return mRecInfoModel;
    }
}

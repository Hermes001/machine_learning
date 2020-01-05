package com.socket.pad.paddemo.manager;

import android.content.Context;
import android.util.Log;

import com.socket.pad.paddemo.Utils.DataUtils;
import com.socket.pad.paddemo.db.DBUtils;
import com.socket.pad.paddemo.model.RecInfoModel;
import com.socket.pad.paddemo.net.socket.SocketServer;

import java.util.concurrent.ExecutorService;

public class RecvInfoManager {

    public static final int SOCKET_STATUS_BASE_COMMAND =1;

    private SocketServer mSocketServer;
    private Context mContext;

    /*
    * 消息发送的状态
    * */
    private int status = 0;
    ExecutorService exec;



    public RecvInfoManager(Context mContext, SocketServer mSocketServer, ExecutorService exec)
    {
        this.mContext = mContext;
        this.mSocketServer = mSocketServer;
        this.exec = exec;
    }
    public int praseData(String recvMsg)
    {
        final RecInfoModel mRecInfoModel = DataUtils.praseRecInfoModel(recvMsg);
        if(mRecInfoModel!=null){
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    DBUtils.addRecInfoData(mContext,mRecInfoModel);
                    if(mRecInfoModel.getFlag()==2)
                    {
                        mSocketServer.SST.get(0).send(DataUtils.getRecInfoAck(true,mRecInfoModel));
                    }
                }
            });
            return mRecInfoModel.getFlag();
        }
        return 0;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

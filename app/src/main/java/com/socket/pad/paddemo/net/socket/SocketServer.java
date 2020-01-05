package com.socket.pad.paddemo.net.socket;

import android.os.Handler;
import android.os.Message;
import androidx.annotation.WorkerThread;
import android.util.Log;

import com.socket.pad.paddemo.ui.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static com.socket.pad.paddemo.ui.MainActivity.REC_DATA;

//https://blog.csdn.net/shankezh/article/details/51555455

public class SocketServer implements Runnable{

    private static final String TAG = "SocketServer";
    private static final int PORT = 8888;
    private static final int TIMEOUT = 5000;
    private boolean isListen = true;   //线程监听标志位
    public ArrayList<ServerSocketThread> SST = new ArrayList<ServerSocketThread>();
    private Handler mHandler;

    public void setListen(boolean listen) {
        isListen = listen;
    }
    public SocketServer(Handler mHandler){
        this.mHandler = mHandler;
    }
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(5000);

            while (isListen){
                Log.e(TAG, "run: 开始监听...");

                Socket socket = getSocket(serverSocket);
                if (socket != null){
                    new ServerSocketThread(socket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Socket getSocket(ServerSocket serverSocket){
        try {
            return serverSocket.accept();
        } catch (IOException e) {
          /*  Log.i(TAG, "run: 监听超时");
            e.printStackTrace();*/
            return null;
        }
    }

    public void closeSelf(){
        isListen = false;
        if(SST!=null&&SST.size()>0){
            for (ServerSocketThread s : SST){
                s.isRun = false;
            }
            SST.clear();
        }

    }

    public class ServerSocketThread extends Thread{
        Socket socket = null;
        private PrintWriter pw;
        private InputStream is = null;
        private OutputStream os = null;
        private String ip = null;
        private boolean isRun = true;

        ServerSocketThread(Socket socket){
            this.socket = socket;
            ip = socket.getInetAddress().toString();
            Log.i("cfn", "ServerSocketThread:检测到新的客户端联入,ip:" + ip);
            try {
                socket.setSoTimeout(5000);
                os = socket.getOutputStream();
                is = socket.getInputStream();
                pw = new PrintWriter(os,true);
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String msg){
            pw.println(msg);
            pw.flush(); //强制送出数据
        }

        @Override
        public void run() {
            byte buff[]  = new byte[4096];
            String rcvMsg;
            int rcvLen;
            SST.add(this);
            mHandler.sendEmptyMessage(MainActivity.SOCKET_CONNECT_SUCCESS);
            while (isRun && !socket.isClosed() && !socket.isInputShutdown()){
                try {
                    if ((rcvLen = is.read(buff)) != -1 ){
                        rcvMsg = new String(buff,0,rcvLen);
                  //      String str = new String(rcvMsg.getBytes("Hex"),"Hex");
                 //       new String(Hex.decodeHex(rcvMsg.toCharArray()), "GBK");
                        Log.i("cfn", "run:收到消息: " + rcvMsg);
                        Message msg = mHandler.obtainMessage();
                        msg.what = REC_DATA;
                        msg.obj = rcvMsg;
                        mHandler.sendMessage(msg);
                      //  send("haha");
                     /*   Intent intent =new Intent();
                        intent.setAction("tcpServerReceiver");
                        intent.putExtra("tcpServerReceiver",rcvMsg);
                        FuncTcpServer.context.sendBroadcast(intent);//将消息发送给主界面*/
                        if (rcvMsg.equals("QuitServer")){
                            isRun = false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                socket.close();
                SST.clear();
                Log.i("cfn", "run: 断开连接");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.socket.pad.paddemo.net;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class ChannelClient {
    private static final String TAG = "ChannelClient";
    private static final int port = 8098;
    private static final String server = "58.221.58.89";
    private Socket mSocket;
    private ConnectCallback mConnectCallback;
    private ChannelSocket channelSocket;
    private String channelId;
    private Thread mConnectThread;

    private boolean isConnecting = false;

    public ChannelClient(
                         ConnectCallback connectCallback) {
        this.mConnectCallback = connectCallback;
    }

    public void connect() {
        Log.i(TAG, "connect: ");
        mConnectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    isConnecting = true;
                    mSocket = new Socket(server, port);
                    channelSocket = new ChannelSocket( mSocket, mConnectCallback);
                    Log.i(TAG, "run: ");
                    mConnectCallback.onConnect();
                    channelSocket.begin();
                    Log.i(TAG, "Thread end!");
                } catch (IOException e) {
                    e.printStackTrace();
                    mConnectCallback.onError(1, e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    mConnectCallback.onError(2, e.getMessage());
                } finally {
                    isConnecting = false;
                }
            }
        });
        mConnectThread.start();
    }

    public void close() {
        if (channelSocket != null) {
            try {
                channelSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

  /*  public void send(String  message) {
        Log.i(TAG, "send: " + message.toJson());
        try {
            mGuardChannel.sendResult(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}

package com.socket.pad.paddemo.net;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ChannelSocket {

    private static final String TAG =  "ChannelSocket";

    private static final String SESSION_START = ">>session_start>>";
    private static final String SESSION_END = "<<session_end<<";
    private static final String LINE_SEPERATOR = "\r\n";
    /**
     * 通道的读取流
     */
    private BufferedReader reader;
    /**
     * 通道的Socket
     */
    private Socket socket;
    /**
     * 通道的 id，服务端做通道识别用的，客户端需要在会话开始的时候发送一个验证消息
     */
    private String channelId;

    private boolean isChannelOpen;

    /**
     * 连接回调
     */
    private ConnectCallback mConnectCallback;

    public ChannelSocket(Socket socket, ConnectCallback connectCallback) {
        isChannelOpen = true;
        this.socket = socket;
//        socket.setKeepAlive(true);
        try {
            InputStreamReader input = new InputStreamReader(socket.getInputStream(),"UTF-8");
            reader = new BufferedReader(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mConnectCallback = connectCallback;
    }

    /**
     * 读取通道的数据
     */
    public void begin() {
      /*  String line;
        String message = "";
        while (isChannelOpen) {
            try {
                line = reader.readLine();
                if (SESSION_START.equals(line)) {
                    // 会话开始
                    message = "";
                } else if (SESSION_END.equals(line)) {
                    // 会话结束，将内容返回
                    if (isChannelOpen) {
                        mConnectCallback.onMessage(GuardMessage.fromJson(message));
                    }
                } else if (line == null) {
                    Log.i(TAG, "begin: 断开连接");
                    close();
                } else {
                    // 获取到返回内容
                    message += line;
                }
            } catch (SocketException e) {
                // 如果 socket断开， readLine 会报这个异常
                Log.d(TAG, "begin: socket断开");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

   /* *//**
     * 发送shell执行结果
     *//*
    public void sendResult(GuardMessage result) {
        // 如果传的时 shell格式， 将其转成 result
        if (GuardMessageType.SHELL.getType().equals(result.getKey())) {
            result.setType(GuardMessageType.RESULT);
        }
        write(result.toJson());
    }*/

    /**
     * 向通道写入内容
     */
    private void write(String content) {
        // 写入内容
        StringBuilder sb = new StringBuilder();
        sb.append(LINE_SEPERATOR)
                .append(SESSION_START)
                .append(LINE_SEPERATOR)
                .append(content)
                .append(LINE_SEPERATOR)
                .append(SESSION_END)
                .append(LINE_SEPERATOR);
        try {
            socket.getOutputStream().write(sb.toString().getBytes());
            socket.getOutputStream().flush();
        } catch (SocketException e) {
            Log.d(TAG, "write: Socket异常");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 关闭通道
     */
    public void close() {
        Log.i(TAG, "关闭通道 close: ");
        isChannelOpen = false;
        if (socket != null && !socket.isClosed()) {
            try {
                // 这里不能先关闭 reader，
                // reader关闭的时候，
                // 线程会卡死在reader.readLine();
                // 所以直接关闭 socket， 在readLine 处捕获异常
                // reader.close();

                socket.close();
                mConnectCallback.onDisconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "close: socket不需要关闭");
        }
    }
}

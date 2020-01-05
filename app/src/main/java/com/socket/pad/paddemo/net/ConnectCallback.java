package com.socket.pad.paddemo.net;

/**
 * 连接回调
 */
public interface ConnectCallback {
    /**
     * 连接成功
     */
    void onConnect();

    /**
     * 连接失败
     */
    void onError(int code, String msg);

    /**
     * 断开连接
     */
    void onDisconnect();

    /**
     *
     * 通道的会话消息，需要将 message 解析为对应的命令
     *
     * @param message
     */
    void onMessage(String message);
}

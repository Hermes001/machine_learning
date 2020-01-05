package com.socket.pad.paddemo.net;

public interface HttpConnectCallback {

    /*
    *http返回 失败
    * */
    void onFailure();

    /*
    * http返回 成功
    * */
    void onResponseSuccess(String response);

}

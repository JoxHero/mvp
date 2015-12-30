package com.mvplibray.zxp.view;

import com.squareup.okhttp.Request;

/**
 * 所有的activity或者fragment实现这个
 * Created by zxp on 2015/12/17 0017.(相互学习，共同进步)
 */
public interface BaseView {

    void showDialog();

    void hideDialog();

    void response(int requestCode, String url, String data);

    void fail(Request request, Exception e);
}

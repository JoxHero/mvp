package com.mvplibray.zxp.model;

import com.squareup.okhttp.Request;

/**
 * 所有的api数据的回掉
 * Created by zxp on 2015/12/13 0013.(相互学习，共同进步)
 */
public interface OnResponse {

    void success(int requestCode, String url, String data);

    void fail(Request request, Exception e);
}

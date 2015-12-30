package com.mvplibray.zxp.okHttps;

import com.socks.library.KLog;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * 自定义CallBack，统一输出url那些参数,统一成string类型
 * Created by zxp on 2015/12/17 0017.(相互学习，共同进步)
 */
public abstract class MyResultCallBack extends Callback<String> {

    @Override
    public String parseNetworkResponse(final Response response) throws IOException {
        return response.body().string();
    }

    @Override
    public void onBefore(Request request) {
        super.onBefore(request);
        KLog.d(request.toString());
    }

    @Override
    public void onAfter() {
        super.onAfter();
    }
}

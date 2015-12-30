package com.mvplibray.zxp.presenter;

import android.os.Handler;

import com.mvplibray.zxp.model.OnResponse;
import com.mvplibray.zxp.view.BaseView;
import com.squareup.okhttp.Request;

/**
 * 基类Presenter
 * TODO: BasePresenter可以和Fragemnt或者Activity的生命周期关联对应，暂时不知道如何抽取以及勇武之地。
 * Created by zxp on 2015/12/20 0020.(相互学习，共同进步)
 */
public class BasePresenter implements Presenter {

    protected OnResponse onResponse;
    protected BaseView baseView;
    protected Handler handler;

    public BasePresenter(BaseView baseView) {
        this.baseView = baseView;
        initOnResponse();
        handler = new Handler();
    }

    /**
     * 初始化OnResponse(统一管理)
     */
    private void initOnResponse() {
        onResponse = new OnResponse() {
            @Override
            public void success(final int requestCode, final String url, final String data) {
                if(handler!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            baseView.respense(requestCode, url, data);
                            baseView.hideDialog();
                        }
                    });
                }
            }

            @Override
            public void fail(final Request request, final Exception e) {
                if(handler!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            baseView.fail(request, e);
                            baseView.hideDialog();
                        }
                    });
                }
            }
        };
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        if(null != handler) {
            handler = null;
        }
    }
}

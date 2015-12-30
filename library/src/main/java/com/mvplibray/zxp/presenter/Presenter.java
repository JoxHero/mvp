package com.mvplibray.zxp.presenter;

/**
 * 所有presenter的基类，绑定fragment或者activity生命周期（在项目中继承他的presenter按模块划分）
 * Created by zxp on 2015/12/17 0017.(相互学习，共同进步)
 */
public interface Presenter {

    void onCreate();

    void onPause();

    void onResume();

    void onDestroy();
}

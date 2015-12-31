package com.mvplibray.zxp.model;

import com.mvplibray.zxp.okHttps.MyResultCallBack;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFileBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * 基础model  所有的model继承这个（在项目中继承他的presenter按模块划分）
 * 2015.12.20加入get下载文件
 * 2015.12.24加入post提交表单形式数据，post提交文件
 * 2015.12.29加入post提交json字符串
 * Created by zxp on 2015/12/20 0020.(相互学习，共同进步)
 */
public class BaseModel {

    //get方式获取params（统一封装任何get接口）
    protected CustomGetThread threadGet = null;
    //post方式提交params（统一封装任何post接口）
    protected CustomPostThread threatPost = null;
    //post方式提交JSON字符串
    protected CustomPostJsonThread threadPostJson = null;
    //post方式提交文件file
    protected CustomPostFileThread threatPostFile = null;
    //post方式提交表单形式文件file（统一封装任何post接口）
    protected CustomPostFormFilesThread threatPostFormFiles = null;
    //get方式下载文件（统一封装任何get接口）
    protected CustomDownloadThread threatDownloadFile = null;

    /**
     * get方式请求数据
     *
     * @param requestCode 请求码
     * @param url         url
     * @param params      参数
     * @param onResponse  回掉
     */
    protected void requestDataByGet (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, OnResponse onResponse) {
        if (threadGet != null) {
            threadGet.interrupt();
            threadGet = null;
        }
        threadGet = new CustomGetThread(requestCode, url, headers, params, onResponse);
        threadGet.start();
    }

    /**
     * post方式请求数据
     *
     * @param requestCode 请求码
     * @param url         url
     * @param headers     请求头
     * @param params      参数
     * @param onResponse  回掉
     */
    protected void requestDataByPost (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, OnResponse onResponse) {
        if (threatPost != null) {
            threatPost.interrupt();
            threatPost = null;
        }
        threatPost = new CustomPostThread(requestCode, url, headers, params, onResponse);
        threatPost.start();
    }

    /**
     * post方式提交json
     * @param requestCode 请求码
     * @param url url
     * @param headers 请求头
     * @param params 参数
     * @param contentJson json字符串
     * @param onResponse 回掉
     */
    protected void requestDataByPostJson(int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, String contentJson, OnResponse onResponse) {
        if (threadPostJson != null) {
            threadPostJson.interrupt();
            threadPostJson = null;
        }
        threadPostJson = new CustomPostJsonThread(requestCode, url, headers, params, contentJson, onResponse);
        threadPostJson.start();
    }

    /**
     *
     * @param requestCode 请求码
     * @param url url
     * @param headers 请求头
     * @param params 请求参数
     * @param file 文件
     * @param onResponse 回掉
     */
    protected void requestDataByPostFile(int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, File file, OnResponse onResponse) {
        if (threatPostFile != null) {
            threatPostFile.interrupt();
            threatPostFile = null;
        }
        threatPostFile = new CustomPostFileThread(requestCode, url, headers, params, file, onResponse);
        threatPostFile.start();
    }

    /**
     * post提交文件(表单形式)
     *
     * @param requestCode 请求码
     * @param url         url
     * @param headers     请求头
     * @param params      参数
     * @param keys        键
     * @param files       文件
     * @param onResponse  回掉
     */
    protected void requestDataByPostFormFiles (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, List<String> keys, List<File> files, OnResponse onResponse) {
        if (threatPostFormFiles != null) {
            threatPostFormFiles.interrupt();
            threatPostFormFiles = null;
        }
        threatPostFormFiles = new CustomPostFormFilesThread(requestCode, url, headers, params,keys, files, onResponse);
        threatPostFormFiles.start();
    }

    /**
     * get方式下载文件
     *
     * @param requestCode 请求码
     * @param url         url
     * @param headers     请求头
     * @param params      参数
     * @param path        下载保存文件的路径
     * @param fileName    文件的名称
     * @param onResponse  回掉
     */
    protected void downloadFile (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, String path, String fileName, OnResponse onResponse) {
        if (threatDownloadFile != null) {
            threatDownloadFile.interrupt();
            threatDownloadFile = null;
        }
        threatDownloadFile = new CustomDownloadThread(requestCode, url, headers, params, path, fileName, onResponse);
        threatDownloadFile.start();
    }


    /**
     * Get方式
     */
    protected class CustomGetThread extends Thread {

        private int requestCode;
        private String url;
        private OnResponse onResponse;
        private HashMap<String, String> headers;
        private HashMap<String, String> params;

        public CustomGetThread () {
        }

        public CustomGetThread (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, OnResponse onResponse) {
            this.requestCode = requestCode;
            this.url = url;
            this.headers = headers;
            this.params = params;
            this.onResponse = onResponse;
        }

        @Override
        public void run () {
            super.run();
            GetBuilder getBuilder = OkHttpUtils.get();

            if (headers != null) {
                getBuilder.params(headers);
            }
            if (params != null)
                getBuilder.params(params);
            getBuilder.url(url).build().execute(new MyResultCallBack() {

                @Override
                public void onError (Request request, Exception e) {
                    onResponse.fail(request, e);
                }

                @Override
                public void onResponse (String data) {
                    onResponse.success(requestCode, url, data);
                }
            });
        }
    }

    /**
     * Post方式，参数
     */
    protected class CustomPostThread extends Thread {

        private int requestCode;
        private String url;
        private HashMap<String, String> headers;
        private HashMap<String, String> params;
        private OnResponse onResponse;

        public CustomPostThread () {
        }

        public CustomPostThread (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, OnResponse onResponse) {
            this.requestCode = requestCode;
            this.url = url;
            this.headers = headers;
            this.params = params;
            this.onResponse = onResponse;
        }

        @Override
        public void run () {
            super.run();
            PostFormBuilder postFormBuilder = OkHttpUtils.post();

            if (headers != null)
                postFormBuilder.headers(headers);
            if (params != null)
                postFormBuilder.params(params);
            postFormBuilder.url(url).build().execute(new MyResultCallBack() {
                @Override
                public void onError (Request request, Exception e) {
                    onResponse.fail(request, e);
                }

                @Override
                public void onResponse (String data) {
                    onResponse.success(requestCode, url, data);
                }
            });
        }
    }

    /**
     * Post方式提交Json格式字符串
     */
    protected class CustomPostJsonThread extends Thread {
        private int requestCode;
        private String url;
        private HashMap<String, String> headers;
        private HashMap<String, String> params;
        private String contentJson;
        private OnResponse onResponse;

        public CustomPostJsonThread() {
        }

        public CustomPostJsonThread(int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, String contentJson, OnResponse onResponse) {
            this.requestCode = requestCode;
            this.url = url;
            this.headers = headers;
            this.params = params;
            this.contentJson = contentJson;
            this.onResponse = onResponse;
        }

        @Override
        public void run() {
            super.run();
            PostStringBuilder postStringBuilder = OkHttpUtils.postString();

            if (headers != null)
                postStringBuilder.headers(headers);
            if (params != null)
                postStringBuilder.params(params);
            if (params != null)
                postStringBuilder.content(contentJson);

            postStringBuilder.url(url).build().execute(new MyResultCallBack() {
                @Override
                public void onError(Request request, Exception e) {
                    onResponse.fail(request, e);
                }

                @Override
                public void onResponse(String data) {
                    onResponse.success(requestCode, url, data);
                }
            });
        }
    }

    /**
     * Post方式提交文件file
     */
    protected class CustomPostFileThread extends Thread {

        private int requestCode;
        private String url;
        private HashMap<String, String> headers;
        private HashMap<String, String> params;
        private File file;
        private OnResponse onResponse;

        public CustomPostFileThread() {
        }

        public CustomPostFileThread(int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, File file, OnResponse onResponse) {
            this.requestCode = requestCode;
            this.url = url;
            this.headers = headers;
            this.params = params;
            this.file = file;
            this.onResponse = onResponse;
        }

        @Override
        public void run() {
            super.run();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PostFileBuilder postFileBuilder = OkHttpUtils.postFile();

            if (file != null) {
                postFileBuilder.file(file);
            }
            if (headers != null)
                postFileBuilder.headers(headers);
            if (params != null)
                postFileBuilder.params(params);

            postFileBuilder.url(url).build().execute(new MyResultCallBack() {
                @Override
                public void onError(Request request, Exception e) {
                    onResponse.fail(request, e);
                }

                @Override
                public void onResponse(String data) {
                    onResponse.success(requestCode, url, data);
                }
            });
        }
    }

    /**
     * Post方式提交文件(表单形式)
     */
    protected class CustomPostFormFilesThread extends Thread {

        private int requestCode;
        private String url;
        private HashMap<String, String> headers;
        private HashMap<String, String> params;
        private List<String> keys;
        private List<File> files;
        private OnResponse onResponse;

        public CustomPostFormFilesThread () {
        }

        public CustomPostFormFilesThread (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params,List<String> keys, List<File> files, OnResponse onResponse) {
            this.requestCode = requestCode;
            this.url = url;
            this.headers = headers;
            this.params = params;
            this.keys = keys;
            this.files = files;
            this.onResponse = onResponse;
        }

        @Override
        public void run () {
            super.run();
            PostFormBuilder postFormBuilder = OkHttpUtils.post();

            if (headers != null)
                postFormBuilder.headers(headers);
            if (params != null)
                postFormBuilder.params(params);
            if (files != null) {
                for (int i = 0; i < files.size(); i++) {
                    //切割图片的名称
                    String name = files.get(i).toString().substring(files.get(i).toString().lastIndexOf("/") + 1);
                    postFormBuilder.addFile(keys.get(i), name, files.get(i));
                }
            }

            postFormBuilder.url(url).build().execute(new MyResultCallBack() {
                @Override
                public void onError (Request request, Exception e) {
                    onResponse.fail(request, e);
                }

                @Override
                public void onResponse (String data) {
                    onResponse.success(requestCode, url, data);
                }
            });
        }
    }

    /**
     * get下载文件
     */
    protected class CustomDownloadThread extends Thread {

        private int requestCode;
        private String url;
        private HashMap<String, String> headers;
        private HashMap<String, String> params;
        private String path;
        private String fileName;
        private OnResponse onResponse;

        public CustomDownloadThread () {
        }

        public CustomDownloadThread (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, String path, String fileName, OnResponse onResponse) {
            this.requestCode = requestCode;
            this.url = url;
            this.headers = headers;
            this.params = params;
            this.path = path;
            this.fileName = fileName;
            this.onResponse = onResponse;
        }

        @Override
        public void run () {
            super.run();
            GetBuilder getBuilder = OkHttpUtils.get();

            if (headers != null)
                getBuilder.headers(headers);
            if (params != null)
                getBuilder.params(params);
            getBuilder.url(url).build().execute(new FileCallBack(path, fileName) {

                @Override
                public void onBefore (Request request) {
                    super.onBefore(request);
                    KLog.d(request.toString());
                }

                @Override
                public void onAfter () {
                    super.onAfter();
                    KLog.d("请求完成");
                }

                @Override
                public void inProgress (float progress) {

                }

                @Override
                public void onError (Request request, Exception e) {
                    onResponse.fail(request, e);
                }

                @Override
                public void onResponse (File response) {
                    onResponse.success(requestCode, url, response.getAbsolutePath());
                }
            });
        }
    }
}

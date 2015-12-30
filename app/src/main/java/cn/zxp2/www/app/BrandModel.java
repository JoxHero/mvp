package cn.zxp2.www.app;

import android.os.Environment;

import com.mvplibray.zxp.model.BaseModel;
import com.mvplibray.zxp.model.OnResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Model
 * Created by zxp on 2015/12/13 0013.(相互学习，共同进步)
 */
public class BrandModel extends BaseModel {

    public static final int BASE_CODE = 100;
    public static final int CODE_GET_BRAND = BASE_CODE + 1;
    public static final int CODE_ADD_ADDRESS = BASE_CODE + 2;
    public static final int CODE_DOWN_LOAD = BASE_CODE + 3;
    public static final int CODE_POST_FILE = BASE_CODE + 4;

    //得到所有商品列表(如果需要一些其他参数，在getBrand()方法里面在写上,同时在BrandPresenter的getBrand方法也写上)
    public void getBrand(String page, String psize, final OnResponse onResponse) {
        final String url = "http://112.74.114.103/vehicle-brand/list";
        HashMap<String, String> params = new HashMap<>();
        params.put("ajax", "json");
        params.put("page", page);
        params.put("psize", psize);

        requestDataByGet(CODE_GET_BRAND, url, null, params, onResponse);
    }

    //添加地址（post，必须先用公司项目的app先登录）
    public void addAddress(final OnResponse onResponse) {
        final String url = "http://112.74.114.103/address/add";
        HashMap<String, String> params = new HashMap<>();
        params.put("ajax", "json");
        params.put("ssid", "2cegju2pkpun8et47a279oajo7");
        params.put("realname", "曾小平");
        params.put("city", "100110000");
        params.put("address", "深圳市南山区桃源站");
        params.put("mobile", "18515077648");

        requestDataByPost(CODE_ADD_ADDRESS, url, null, params, onResponse);
    }

    //下载文件
    public void downloatFile(final OnResponse onResponse) {
        final String url = "http://xz.cr173.com/soft3/dalingqianbao.apk";
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "da222lingqianbao.apk";

        downloadFile(CODE_DOWN_LOAD, url, null, null, path, fileName, onResponse);
    }

    //post上传表单文件（post，必须先用公司项目的app先登录，记得改ssid）
    public void requestDataByPostFile (final OnResponse onResponse) {
        String url = "http://112.74.114.103/member/upload-logo";
        HashMap<String, String> params = new HashMap<>();
        params.put("ajax", "json");
        params.put("ssid", "tlfrupflholjpe0n85h96ptgc3");

        List<File> files = new ArrayList<>();
        File file = new File("/storage/emulated/0/UCDownloads/Screenshot/tmp/TMPSNAPSHOT1450804899586.jpg");
        files.add(file);

        requestDataByPostFormFiles(CODE_POST_FILE, url, null, params, files, onResponse);
    }

}

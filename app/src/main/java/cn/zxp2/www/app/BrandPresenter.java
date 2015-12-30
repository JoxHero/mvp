package cn.zxp2.www.app;

import com.mvplibray.zxp.presenter.BasePresenter;
import com.mvplibray.zxp.view.BaseView;

/**
 * 2015.12.20新增BasePresenter，抽出来重复代码,写入到BasePresenter中
 * ---------->按模块划分
 * Created by zxp on 2015/12/13 0013.(相互学习，共同进步)
 */
public class BrandPresenter extends BasePresenter {

    BrandModel brandModel;

    public BrandPresenter(final BaseView baseView) {
        super(baseView);
        brandModel = new BrandModel();
    }

    //如果brandModel.getBrand还需要新的参数那么可以直接在getBrandData中船进来
    public void getBrandData(String page, String psize) {
        baseView.showDialog();
        brandModel.getBrand(page, psize, onResponse);
    }

    //添加地址
    public void addAddress(){
        baseView.showDialog();
        brandModel.addAddress(onResponse);
    }

    //下载文件
    public void downloadFile(){
        baseView.showDialog();
        brandModel.downloatFile(onResponse);
    }

    //post文件
    public void postFile(){
        baseView.showDialog();
        brandModel.requestDataByPostFile(onResponse);
    }

}

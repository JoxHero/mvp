#基于mvp和okhttp网络请求的的library,减少Fragment/Activity代码的臃肿
====
使用方法：
----
###1.自定义model继承BaseModel
###2.自定义presenter继承BasePresenter
###3.Fragment/Activity继承BaseView，重写四个方法：
        showDialog()    ：显示进度条
        hideDialog()    ：隐藏进度条
        respense(int requestCode, String url, String data)    ：成功返回数据
        fail(Request request, Exception e)    ：请求失败

请求方式：
----
###1.get请求
requestDataByGet (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, OnResponse onResponse)

###2.post请求
requestDataByPost (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, OnResponse onResponse)

###3.post上传表单文件
requestDataByPostFormFiles (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, List<String> keys, List<File> files, OnResponse onResponse)

###4.post上传文件
requestDataByPostFile (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, File file, OnResponse onResponse)

###5.get下载文件
downloadFile (int requestCode, String url, HashMap<String, String> headers, HashMap<String, String> params, String path, String fileName, OnResponse onResponse)

其中：
----
    requestCode    ：请求码
    url    ：url
    headers    ：请求头
    params    ：请求参数
    file    ：文件
    files    ：多个文件
    onResponse    ：回掉

例子：
----
###1.model
public class BrandModel extends BaseModel {

    public static final int BASE_CODE = 100;
    public static final int CODE_GET_BRAND = BASE_CODE + 1;

    //得到所有商品列表
    public void getBrand(String page, String psize, final OnResponse onResponse) {
        final String url = "http://112.74.114.103/vehicle-brand/list";
        HashMap<String, String> params = new HashMap<>();
        params.put("ajax", "json");
        params.put("page", page);
        params.put("psize", psize);

        requestDataByGet(CODE_GET_BRAND, url, null, params, onResponse);
    }
}

###2.presenter
public class BrandPresenter extends BasePresenter {

    BrandModel brandModel;

    public BrandPresenter(final BaseView baseView) {
        super(baseView);
        brandModel = new BrandModel();
    }

    public void getBrandData(String page, String psize) {
        baseView.showDialog();
        brandModel.getBrand(page, psize, onResponse);
    }
}

###3.Fragment/Activity
public class MainActivity extends AppCompatActivity implements BaseView{

    TextView tv;
    ProgressBar pb;
    BrandPresenter brandPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.tv);
        pb= (ProgressBar) findViewById(R.id.pb);

        brandPresenter=new BrandPresenter(this);
        brandPresenter.getBrandData("1","5");//请求数据

    }

    @Override
    public void showDialog() {
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDialog() {
        pb.setVisibility(View.GONE);
    }

    @Override
    public void respense(int requestCode, String url, String data) {
        switch (requestCode){
            case BrandModel.CODE_GET_BRAND:
                tv.setText(data);
                break;
        }
    }

    @Override
    public void fail(Request request, Exception e) {

    }
}

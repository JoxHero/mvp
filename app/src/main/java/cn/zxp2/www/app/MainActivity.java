package cn.zxp2.www.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mvplibray.zxp.view.BaseView;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

public class MainActivity extends AppCompatActivity implements BaseView{

    TextView tv;
    BrandPresenter brandPresenter;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.tv);
        pb= (ProgressBar) findViewById(R.id.pb);

        brandPresenter=new BrandPresenter(this);
//        brandPresenter.getBrandData("1","5");
        brandPresenter.postFile();

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
    public void response(int requestCode, String url, String data) {
        switch (requestCode){
            case BrandModel.CODE_GET_BRAND:
                tv.setText(data);
                KLog.json(data);
                break;
            case BrandModel.CODE_POST_FILE:
                tv.setText(data);
                KLog.json(data);
                break;
        }
    }

    @Override
    public void fail(Request request, Exception e) {
        KLog.json(request.toString());
        KLog.json(e.toString());
    }
}

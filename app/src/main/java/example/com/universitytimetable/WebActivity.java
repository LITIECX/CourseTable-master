package example.com.universitytimetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;

/**
 * Created by lianlingneng on 2017/5/19.
 */

public class WebActivity extends AppCompatActivity {

    private static final String TAG = "WebActivity";


    BridgeWebView bridgeWebView;
    Button button;

    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

//        Intent intent = this.getIntent();
//        String email = intent.getStringExtra("email");

//        mUserInfo = new UserInfo(email);
        button = (Button) findViewById(R.id.button);

        getSupportActionBar().setTitle("JsBridge_test");

        button = (Button) findViewById(R.id.button);

        bridgeWebView = (BridgeWebView) findViewById(R.id.web_view);

        bridgeWebView.setDefaultHandler(new DefaultHandler());

        bridgeWebView.setWebChromeClient(new WebChromeClient());

        bridgeWebView.setWebViewClient( new MyWebViewClient(bridgeWebView));
        bridgeWebView.loadUrl("http://47.100.13.155:8080/springboot/");
//        bridgeWebView.loadUrl("file:///android_asset/getuserinfo.html");

        registerHandler();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 给Html发消息   js接收并返回data
                 */
                bridgeWebView.callHandler("functionInJs", "调用js的方法", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {

                    }
                });
            }
        });



    }

    private void registerHandler() {
        bridgeWebView.registerHandler("getUserInfo", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(WebActivity.this, ""+data, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "handler = getUserInfo, data from web = " + data);
                function.onCallBack(new Gson().toJson("lttt"));

            }
        });
    }

}

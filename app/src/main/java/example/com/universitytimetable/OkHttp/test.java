package example.com.universitytimetable.OkHttp;

public class test {

    public void demo1() {  //异步调用
        IRequest request = new MyRequest("");
        request.setHeader("", "");
        request.setBody("", "");
        IHttpClient mHttpClient = new MyOkHttpClient();
        mHttpClient.get(request, new OnResultListener<MyResponse>() {
            @Override
            public void onResult(MyResponse result) {

            }

            @Override
            public void onError(Exception error) {

            }
        });

    }
    public void demo2() {  //同步调用
        IRequest request = new MyRequest("");
        request.setHeader("","");
        request.setBody("","");
        IHttpClient mHttpClient = new MyOkHttpClient();
        IResponse response = mHttpClient.get(request);
    }


}

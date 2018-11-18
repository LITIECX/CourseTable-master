package example.com.universitytimetable.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import example.com.universitytimetable.Notification.NotificationUtils;
import example.com.universitytimetable.OkHttp.IHttpClient;
import example.com.universitytimetable.OkHttp.IRequest;
import example.com.universitytimetable.OkHttp.MyOkHttpClient;
import example.com.universitytimetable.OkHttp.MyRequest;
import example.com.universitytimetable.OkHttp.MyResponse;
import example.com.universitytimetable.OkHttp.OnResultListener;
import example.com.universitytimetable.table.TableData;
import example.com.universitytimetable.workers.RequstModel;

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                IRequest request = new MyRequest("http://47.100.13.155:8080/TimeTable/nextTable");
                request.setBody("userId", pref.getString("userId", ""));
                IHttpClient mHttpClient = new MyOkHttpClient();
                mHttpClient.post(request, new OnResultListener<MyResponse>() {
                    @Override
                    public void onResult(MyResponse result) {
                        final String data1 = result.getData();
                        try {
                            Gson gson = new Gson();
                            TableData tableData = gson.fromJson(data1, TableData.class);
                            String str1 = tableData.getClassRoom() + "@" + tableData.getCourse();
                            String str2 = "周" + tableData.getWeek() + "@第" + tableData.getSection() + "节";
                            notification(str1, str2);
                        } catch (JsonSyntaxException e) {
                            notification("获取数据出错，请检查网络", "");
                        }
                    }
                    @Override
                    public void onError(Exception error) {

                    }
                });

            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void notification(String data, String str) {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.sendNotification(str, data);

    }
}

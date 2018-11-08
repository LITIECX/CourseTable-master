package example.com.universitytimetable.table;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import example.com.universitytimetable.OkHttp.IHttpClient;
import example.com.universitytimetable.OkHttp.IRequest;
import example.com.universitytimetable.OkHttp.IResponse;
import example.com.universitytimetable.OkHttp.MyOkHttpClient;
import example.com.universitytimetable.OkHttp.MyRequest;
import example.com.universitytimetable.OkHttp.MyResponse;
import example.com.universitytimetable.OkHttp.OnResultListener;

public class TableUtils {

    TableData tableData =new TableData();

    /**
     * 查找下一次课程
     * @param id   学生的学号
     * @return   返回查到的课表数据类
     */
    public TableData nestTable(String id){
        String str = "";
        IRequest request = new MyRequest("http://47.100.13.155:8080/TimeTable/nextTable");
        request.setBody("userId", id);
        IHttpClient mHttpClient = new MyOkHttpClient();
        IResponse result = mHttpClient.post(request);
        Gson gson = new Gson();
        tableData = gson.fromJson(result.getData(), TableData.class);
        return tableData ;
    }


}

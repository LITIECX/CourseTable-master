package example.com.universitytimetable.workers;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import example.com.universitytimetable.table.TableData;
import example.com.universitytimetable.table.TableUtils;

public class TableWorkerF extends Worker{

    public TableWorkerF(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }


    @NonNull
    @Override
    public Worker.Result doWork() {
        Context applicationContext = getApplicationContext();
        String title = "智能提醒准时为你关闭";
        String message = "请打开APP查课表，记得明天早起打开APP唤醒我";
        WorkerUtils.makeStatusNotification(title, message, applicationContext,1);  //发出通知


        return Worker.Result.SUCCESS;
    }
}

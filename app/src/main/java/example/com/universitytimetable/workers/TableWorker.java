package example.com.universitytimetable.workers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;
import example.com.universitytimetable.table.TableData;
import example.com.universitytimetable.table.TableUtils;

public class TableWorker extends Worker {

    public TableWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }


    @NonNull
    @Override
    public Worker.Result doWork() {
        String id = getInputData().getString("data");
        Context applicationContext = getApplicationContext();
        String title = "智能提醒开始工作";
        String message = "我会在第一时间提醒你要上的课";
        WorkerUtils.makeStatusNotification(title, message, applicationContext,1);  //发出通知
        Log.d("lttt", "doWork:第一个通知 ");
        TableData tableData = new TableUtils().nestTable(id);
        String content1 = tableData.getClassRoom() + "@" + tableData.getCourse();
        String title1 = "周" + tableData.getWeek() + "@第" + tableData.getSection() + "节";
        WorkerUtils.sleep(); //沉睡15s
        WorkerUtils.makeStatusNotification(title1, content1, applicationContext,2);  //发出通知
        Log.d("lttt", "doWork:第二个通知 ");
        return Worker.Result.SUCCESS;
    }
}

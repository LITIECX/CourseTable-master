package example.com.universitytimetable.workers;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import example.com.universitytimetable.table.TableData;
import example.com.universitytimetable.table.TableUtils;

public class TableWorkerB extends Worker {


    public TableWorkerB(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Worker.Result doWork() {
        Context applicationContext = getApplicationContext();
        String id = getInputData().getString("data");
        TableData tableData = new TableUtils().nestTable(id);
        String content = tableData.getClassRoom() + "@" + tableData.getCourse();
        String title = "周" + tableData.getWeek() + "@第" + tableData.getSection() + "节";

        WorkerUtils.makeStatusNotification(title, content, applicationContext,2);  //发出通知
        Data responseData = new Data.Builder().putString("response", "ok").build();  //返回结果
        setOutputData(responseData);

        return Worker.Result.SUCCESS;
    }
}

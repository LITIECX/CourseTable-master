package example.com.universitytimetable.workers;

import android.content.Context;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;
import example.com.universitytimetable.MyApplication;


public class RequstModel {
    private WorkManager mWorkManager;
    WorkContinuation workContinuation;


    /**
     * 开启后台任务，实时更新通知栏内容
     *
     * @param id 查询学生的学号
     */
    public void model(String id) {
        mWorkManager = WorkManager.getInstance();
        cancelWork();  // 取消所有的定时任务
        Data data = new Data.Builder()  //传入每个Worker的数据
                .putString("data", id)
                .build();
        int cou = 0;
        Calendar c1 = Calendar.getInstance();
        int hour = c1.get(Calendar.HOUR_OF_DAY);
        int minute = c1.get(Calendar.MINUTE);
        int time = hour * 100 + minute;
        if (time >= 0 && time <= 830) {
            cou = 1;
        } else if (time > 830 && time <= 1030) {
            cou = 2;
        } else if (time > 1030 && time <= 1400) {
            cou = 3;
        } else if (time > 1400 && time <= 1600) {
            cou = 4;
        } else if (time > 1600 && time <= 1740) {
            cou = 5;
        } else {
            cou = 6;
        }
        cou = 1;
        OneTimeWorkRequest testWorker = new OneTimeWorkRequest.Builder(TableWorker.class)
                .setInputData(data)
                .addTag("cleanup")  //为请求设置tag以方便获取任务执行'状态和取消
                .build();
        workContinuation = mWorkManager.beginWith(testWorker);
        switch (cou) {
            case 1:
                OneTimeWorkRequest testWorkerA = new OneTimeWorkRequest.Builder(TableWorkerA.class)
                        .setInputData(data)
                        .addTag("cleanup")  //为请求设置tag以方便获取任务执行'状态和取消
                        .setInitialDelay(setAlarm(9, 10), TimeUnit.SECONDS)  //延时执行worker831
                        .build();
                workContinuation = workContinuation.then(testWorkerA);
            case 2:
                OneTimeWorkRequest testWorkerB = new OneTimeWorkRequest.Builder(TableWorkerB.class)
                        .setInputData(data)
                        .addTag("cleanup")  //为请求设置tag以方便获取任务执行'状态和取消
                        .setInitialDelay(setAlarm(9, 15), TimeUnit.SECONDS)  //延时执行worker 1031
                        .build();
                workContinuation = workContinuation.then(testWorkerB);
            case 3:
                OneTimeWorkRequest testWorkerC = new OneTimeWorkRequest.Builder(TableWorkerC.class)
                        .setInputData(data)
                        .addTag("cleanup")  //为请求设置tag以方便获取任务执行'状态和取消
                        .setInitialDelay(setAlarm(17, 20), TimeUnit.SECONDS)  //延时执行worker 1401
                        .build();
                workContinuation = workContinuation.then(testWorkerC);
            case 4:
                OneTimeWorkRequest testWorkerD = new OneTimeWorkRequest.Builder(TableWorkerD.class)
                        .setInputData(data)
                        .addTag("cleanup")  //为请求设置tag以方便获取任务执行'状态和取消
                        .setInitialDelay(setAlarm(17, 25), TimeUnit.SECONDS)  //延时执行worker 1601
                        .build();
                workContinuation = workContinuation.then(testWorkerD);
            case 5:
                OneTimeWorkRequest testWorkerE = new OneTimeWorkRequest.Builder(TableWorkerE.class)
                        .setInputData(data)
                        .addTag("cleanup")  //为请求设置tag以方便获取任务执行'状态和取消
                        .setInitialDelay(setAlarm(11, 15), TimeUnit.SECONDS)  //延时执行worker 1701
                        .build();
                workContinuation = workContinuation.then(testWorkerE);
                workContinuation.enqueue();
                break;
            case 6:
                OneTimeWorkRequest testWorkerF = new OneTimeWorkRequest.Builder(TableWorkerF.class)
                        .addTag("cleanup")  //为请求设置tag以方便获取任务执行'状态和取消
                        .setInitialDelay(30, TimeUnit.SECONDS)  //延时执行worker
                        .build();
                workContinuation = workContinuation.then(testWorkerF);
                workContinuation.enqueue();
                break;
        }

    }

    public void cancelWork() {
//      mWorkManager.cancelUniqueWork(""); //取消唯一任务。
        mWorkManager.cancelAllWork();    //取消所有任务
//       mWorkManager.cancelAllWorkByTag("cleanup");//取消一组带有相同标签的任务
    }


    /**
     * 设置定时时间
     *
     * @param hour   24小时格式
     * @param minute
     * @return
     */
    public long setAlarm(int hour, int minute) {
        long second = 0;
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get
                (Calendar.DAY_OF_MONTH), hour, minute, 10);

        second = (calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) / 1000;
        return second;
    }


}

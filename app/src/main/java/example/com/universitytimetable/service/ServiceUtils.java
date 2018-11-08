package example.com.universitytimetable.service;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import java.util.ArrayList;

public class ServiceUtils {
        /**
         * 判断服务是否开启
         *
         * @return
         */
        public  boolean isServiceRunning(Context context, String ServiceName) {
            if (("").equals(ServiceName) || ServiceName == null){
                Log.d("lttt", "isServiceRunning: 服务判断启动");
                return false;
            }
            ActivityManager myManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                    .getRunningServices(120);
            for (int i = 0; i < runningService.size(); i++) {
                if (runningService.get(i).service.getClassName()
                        .equals(ServiceName)) {
                    Log.d("lttt", "isServiceRunning: 服务@启动");
                    return true;
                }
            }
            return false;
        }

}

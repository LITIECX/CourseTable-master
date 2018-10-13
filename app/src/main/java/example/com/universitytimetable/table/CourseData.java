package example.com.universitytimetable.table;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weihuajian on 16/6/12.
 */
public class CourseData {

    public static List<CourseModel>[] getCourseData(String tableData) {

        Gson gson = new Gson();
        List<TableData> tableDataLis = gson.fromJson(tableData,new TypeToken<List<TableData>>(){}.getType());

        List<CourseModel> courseModels[] = new ArrayList[7];
        for (int i = 0; i < courseModels.length; i++) {
            courseModels[i] = new ArrayList<>();
        }
        for (TableData e : tableDataLis) {
            switch (e.getWeek()) {
                case 1:
                    courseModels[0].add(new CourseModel(e.getId(), e.getCourse(), e.getSection(), e.getSectionSpan(), e.getWeek(), e.getClassRoom(), (int) (Math.random() * 10)));
                    break;
                case 2:
                    courseModels[1].add(new CourseModel(e.getId(), e.getCourse(), e.getSection(), e.getSectionSpan(), e.getWeek(), e.getClassRoom(), (int) (Math.random() * 10)));
                    break;
                case 3:
                    courseModels[2].add(new CourseModel(e.getId(), e.getCourse(), e.getSection(), e.getSectionSpan(), e.getWeek(), e.getClassRoom(), (int) (Math.random() * 10)));
                    break;
                case 4:
                    courseModels[3].add(new CourseModel(e.getId(), e.getCourse(), e.getSection(), e.getSectionSpan(), e.getWeek(), e.getClassRoom(), (int) (Math.random() * 10)));
                    break;
                case 5:
                    courseModels[4].add(new CourseModel(e.getId(), e.getCourse(), e.getSection(), e.getSectionSpan(), e.getWeek(), e.getClassRoom(), (int) (Math.random() * 10)));
                    break;
                case 6:
                    courseModels[5].add(new CourseModel(e.getId(), e.getCourse(), e.getSection(), e.getSectionSpan(), e.getWeek(), e.getClassRoom(), (int) (Math.random() * 10)));
                    break;
                case 7:
                    courseModels[6].add(new CourseModel(e.getId(), e.getCourse(), e.getSection(), e.getSectionSpan(), e.getWeek(), e.getClassRoom(), (int) (Math.random() * 10)));
                    break;
            }
        }
        return courseModels;
    }
}

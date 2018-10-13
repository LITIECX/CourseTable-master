package example.com.universitytimetable.table;

public class TableData {

    private long id;

    private String studentId;

    private String name;

    private String course;

    private String teacher;

    private int oneTow;

    private int starWeek;

    private int endWeek;

    private String classRoom;
    //课程定位坐标 X:week ;Y:section ,sectionSpan
    private int week; //周几
    private int section; //从第几节课开始
    private int sectionSpan; //跨几节课


    public int getOneTow() {
        return oneTow;
    }

    public void setOneTow(int oneTow) {
        this.oneTow = oneTow;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getStarWeek() {
        return starWeek;
    }

    public void setStarWeek(int starWeek) {
        this.starWeek = starWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getSectionSpan() {
        return sectionSpan;
    }

    public void setSectionSpan(int sectionSpan) {
        this.sectionSpan = sectionSpan;
    }
}

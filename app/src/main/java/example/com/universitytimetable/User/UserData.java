package example.com.universitytimetable.User;

public class UserData {

    private long id;
    private String studentId;
    private String name;
    private String password;
    private String date;
    public UserData() {  //无参构造

    }

    public UserData(String studentId, String name, String password, String date) {
        this.studentId = studentId;
        this.name = name;
        this.password = password;
        this.date = date;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}

package bean;

import java.util.HashMap;
import java.util.Map;

public class TestListSubject {
    private int entYear;
    private String studentNo;
    private String studentName;
    private String classNum;
    private Map<Integer, Integer> pointsByNo = new HashMap<>();
    private Integer point1;
    private Integer point2;

    // 年度
    public int getEntYear() {
        return entYear;
    }

    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    // 学生番号
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    // 学生名
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // クラス番号
    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }


	 // 得点を取得
    public Map<Integer, Integer> getPointsByNo() {
        return pointsByNo;
    }

    public void setPointForNo(int no, int point) {
        this.pointsByNo.put(no, point);
    }

    public Integer getPoint1() {
        return point1;
    }

    public void setPoint1(Integer point1) {
        this.point1 = point1;
    }

    public Integer getPoint2() {
        return point2;
    }

    public void setPoint2(Integer point2) {
        this.point2 = point2;
    }
}

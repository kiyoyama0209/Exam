package bean;

public class TestListSubject {
    private int entYear;
    private String studentNo;
    private String studentName;
    private String classNum;
    private int points;

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
	    public int getPoint() {
	    	return points;
	    }

	    // 得点を設定
	    public void setPoint(int points) {
	    	this.points = points;

	    }
	}

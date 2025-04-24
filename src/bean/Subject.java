package bean;

import java.io.Serializable;

public class Subject implements Serializable {
    private String cd;       // 科目コード
    private String name;       // 科目名
    private String schoolCd;   // 学校コード
    public String getCode() { return cd; }
    public void setCode(String cd) { this.cd = cd; }
    public String getCd() {
        return cd;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSchoolCd() { return schoolCd; }
    public void setSchoolCd(String schoolCd) { this.schoolCd = schoolCd; }
}

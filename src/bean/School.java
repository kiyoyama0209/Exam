package bean;

import java.io.Serializable;

public class School implements Serializable {
    private String schoolCd;  // 学校コード
    private String name;      // 学校名

    public String getSchoolCd() {
        return schoolCd;
    }
    public void setSchoolCd(String schoolCd) {
        this.schoolCd = schoolCd;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

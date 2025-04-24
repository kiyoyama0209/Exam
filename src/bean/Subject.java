package bean;

import java.io.Serializable;

public class Subject implements Serializable {
    private String cd;
    private String name;
    private String schoolCd;

    public String getCode() { return cd; }
    public void setCode(String cd) { this.cd = cd; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSchoolCd() { return schoolCd; }
    public void setSchoolCd(String schoolCd) { this.schoolCd = schoolCd; }
}

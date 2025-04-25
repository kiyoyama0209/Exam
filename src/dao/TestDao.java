package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Test;

public class TestDao extends Dao {

    public List<Test> filter(String entYear, String classNum, String subjectCd, int no, String schoolCd) throws Exception {
        List<Test> list = new ArrayList<>();

        String sql = "SELECT STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM "
                   + "FROM TEST WHERE CLASS_NUM = ? AND SUBJECT_CD = ? AND NO = ? AND SCHOOL_CD = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, classNum);
            ps.setString(2, subjectCd);
            ps.setInt(3, no);
            ps.setString(4, schoolCd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Test t = new Test();
                t.setStudentNo(rs.getString("STUDENT_NO"));
                t.setSubjectCd(rs.getString("SUBJECT_CD"));
                t.setSchoolCd(rs.getString("SCHOOL_CD"));
                t.setNo(rs.getInt("NO"));
                t.setPoint(rs.getInt("POINT"));
                t.setClassNum(rs.getString("CLASS_NUM"));
                list.add(t);
            }
        }
        return list;
    }
}

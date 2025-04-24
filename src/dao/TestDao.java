package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import bean.Test;

public class TestDao extends Dao {

    // 成績の削除（複数対応）
    public void deleteTests(List<Test> tests) throws Exception {
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND NO = ? AND SCHOOL_CD = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            for (Test t : tests) {
                ps.setString(1, t.getStudentNo());
                ps.setString(2, t.getSubjectCd());
                ps.setInt(3, t.getNo());
                ps.setString(4, t.getSchoolCd());
                ps.executeUpdate();
            }
        }
    }
}

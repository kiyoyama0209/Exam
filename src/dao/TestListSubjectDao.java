package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
	private String baseSql = "SELECT " +
	        "s.ent_year, " +
	        "s.class_num, " +
	        "t.student_no, " +
	        "s.name AS student_name, " +
	        "t.point " +
	        "FROM " +
	        "student s " +
	        "JOIN " +
	        "test t ON s.no = t.student_no " +
	        "WHERE " +
	        "s.ent_year = ? " +
	        "AND s.class_num = ? " +
	        "AND t.subject_cd = ? " +
	        "AND s.school_cd = ?";
    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) {
        List<TestListSubject> list = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = baseSql;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, entYear);
                stmt.setString(2, classNum);
                stmt.setString(3, subject.getCode());
                stmt.setString(4, school.getSchoolCd());

                try (ResultSet rs = stmt.executeQuery()) {
                    list = postFilter(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private List<TestListSubject> postFilter(ResultSet rs) throws SQLException {
        List<TestListSubject> list = new ArrayList<>();

        while (rs.next()) {
            TestListSubject test = new TestListSubject();
            test.setEntYear(rs.getInt("ent_year"));
            test.setStudentNo(rs.getString("student_no"));
            test.setStudentName(rs.getString("student_name"));
            test.setClassNum(rs.getString("class_num"));

            // 1つのpointを取得して設定
            int point = rs.getInt("point");
            if (!rs.wasNull()) {

                test.setPoint(point);
            }

            list.add(test);
        }


        return list;
    }
}
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {
	private final String baseSql =
		    "SELECT sub.name AS subject_name, " +
		    "sub.cd AS subject_cd, " +
		    "t.no, " +
		    "t.point, " +
		    "s.name AS student_name, " +
	        "t.student_no " +
		    "FROM test t " +
		    "JOIN subject sub ON t.subject_cd = sub.cd " +
		    "JOIN student s ON t.student_no = s.no " +
		    "WHERE t.student_no = ? " +
		    "GROUP BY sub.name, sub.cd, t.no, t.point, s.name, t.student_no " +
		    "ORDER BY t.subject_cd, t.no";

    public List<TestListStudent> filter(Student student) {
        List<TestListStudent> list = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(baseSql)) {

            stmt.setString(1, student.getNo());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TestListStudent t = new TestListStudent();
                t.setSubjectName(rs.getString("subject_name"));
                t.setSubjectCd(rs.getString("subject_cd"));
                t.setStudentName(rs.getString("student_name"));
                t.setStudentNo(rs.getString("student_no"));
                t.setNum(rs.getInt("no"));
                t.setPoint(rs.getInt("point"));

                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

}

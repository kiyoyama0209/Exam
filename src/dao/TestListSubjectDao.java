package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;


public class TestListSubjectDao extends Dao {
	private String baseSql = "SELECT " +
	        "s.ent_year, " +
	        "s.class_num, " +
	        "t.student_no, " +
	        "s.name AS student_name, " +
	        "t.no, " +
	        "t.point " +
	        "FROM " +
	        "student s " +
	        "JOIN " +
	        "test t ON s.no = t.student_no " +
	        "WHERE " +
	        "s.ent_year = ? " +
	        "AND s.class_num = ? " +
	        "AND t.subject_cd = ? " +
	        "AND s.school_cd = ? " +
    		"ORDER BY t.student_no, t.no";
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
    	 Map<String, TestListSubject> map = new LinkedHashMap<>();

    	    while (rs.next()) {
    	        String studentNo = rs.getString("student_no");
    	        int no = rs.getInt("no");
    	        int point = rs.getInt("point");

    	        // studentNoがすでに存在する場合はそのTestListSubjectを利用
    	        TestListSubject test = map.get(studentNo);
    	        if (test == null) {
    	            // 新しくTestListSubjectを作成
    	            test = new TestListSubject();
    	            test.setEntYear(rs.getInt("ent_year"));
    	            test.setClassNum(rs.getString("class_num"));
    	            test.setStudentNo(studentNo);
    	            test.setStudentName(rs.getString("student_name"));
    	            map.put(studentNo, test);
    	        }

    	        // 得点を回数ごとに保存
    	        test.setPointForNo(no, point);

    	    }
    	    for (TestListSubject test : map.values()) {
    	        Map<Integer, Integer> points = test.getPointsByNo();
    	        test.setPoint1(points.get(1)); // 1回目
    	        test.setPoint2(points.get(2)); // 2回目
    	    }

    	    return new ArrayList<>(map.values());
    }
}
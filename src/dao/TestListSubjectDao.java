package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    // SQLのベース（必要に応じて修正）
    private final String baseSql = "SELECT * FROM test_list_subject";

    // ResultSet → List<TestListSubject> に変換
    public List<TestListSubject> postFilter(ResultSet rs) throws SQLException {
        List<TestListSubject> list = new ArrayList<>();

        while (rs.next()) {
            TestListSubject tls = new TestListSubject();

            tls.setEntYear(rs.getInt("ent_year"));
            tls.setStudentNo(rs.getString("student_no"));
            tls.setStudentName(rs.getString("student_name"));
            tls.setClassNum(rs.getString("class_num"));

            // Subject（教科）
            Subject subject = new Subject();
            subject.setSchoolCd(rs.getString("subject_cd"));
            subject.setName(rs.getString("subject_name")); // このカラムが無ければ削除してOK
            tls.setSubject(subject);

            // 得点（point1〜point5）マップ化
            Map<Integer, Integer> points = new HashMap<>();
            for (int i = 1; i <= 5; i++) {
                try {
                    points.put(i, rs.getInt("point" + i));
                } catch (SQLException e) {
                    // point列がない場合はスキップ
                }
            }
            tls.setPoints(points);

            list.add(tls);
        }

        return list;
    }

    // 条件付きフィルタ
    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        List<TestListSubject> list;

        try (Connection conn = getConnection()) {
            String sql = baseSql + " WHERE ent_year = ? AND class_num = ? AND subject_cd = ? AND school_cd = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, entYear);
                stmt.setString(2, classNum);
                stmt.setString(3, subject.getCd());
                stmt.setString(4, school.getSchoolCd());

                try (ResultSet rs = stmt.executeQuery()) {
                    list = postFilter(rs);
                }
            }
        }

        return list;
    }
}
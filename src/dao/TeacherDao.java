package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
import bean.Teacher;

public class TeacherDao extends Dao {

    public Teacher get(String id) throws Exception {
        Teacher teacher = null;

        // SQL文
        String sql = "SELECT * FROM TEACHER WHERE ID = ?";

        // try-with-resources で Connection を取得
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                teacher = new Teacher();
                teacher.setId(rs.getString("ID"));
                teacher.setPassword(rs.getString("PASSWORD"));
                teacher.setName(rs.getString("NAME"));

                // 学校コードをセット（Schoolオブジェクトに）
                School school = new School();
                school.setSchoolCd(rs.getString("SCHOOL_CD"));
                teacher.setSchool(school);
            }
        }

        return teacher;
    }
}

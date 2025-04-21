package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;

public class SchoolDao extends Dao {

    /**
     * 学校コードを指定して1件取得
     */
    public School get(String schoolCd) throws Exception {
        School school = null;
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM SCHOOL WHERE SCHOOL_CD=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, schoolCd);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                school = new School();
                school.setSchoolCd(rs.getString("SCHOOL_CD"));
                school.setName(rs.getString("NAME"));
            }
        }
        return school;
    }

    // 必要であれば一覧取得（任意）
    public List<School> getAll() throws Exception {
        List<School> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM SCHOOL ORDER BY SCHOOL_CD";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                School school = new School();
                school.setSchoolCd(rs.getString("SCHOOL_CD"));
                school.setName(rs.getString("NAME"));
                list.add(school);
            }
        }
        return list;
    }
}

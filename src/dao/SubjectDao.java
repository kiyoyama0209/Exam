package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;

public class SubjectDao extends Dao {

    // 科目一覧取得
    public List<Subject> filter(String schoolCd) throws Exception {
        List<Subject> list = new ArrayList<>();

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CODE";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, schoolCd);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Subject s = new Subject();
                s.setCode(rs.getString("CODE"));
                s.setName(rs.getString("NAME"));
                s.setSchoolCd(rs.getString("SCHOOL_CD"));
                list.add(s);
            }
        }
        return list;
    }

    // 科目詳細取得
    public Subject get(String code) throws Exception {
        Subject s = null;
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM SUBJECT WHERE CODE = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s = new Subject();
                s.setCode(rs.getString("CODE"));
                s.setName(rs.getString("NAME"));
                s.setSchoolCd(rs.getString("SCHOOL_CD"));
            }
        }
        return s;
    }

    // 科目登録
    public boolean save(Subject subject) throws Exception {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO SUBJECT (CODE, NAME, SCHOOL_CD) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, subject.getCode());
            ps.setString(2, subject.getName());
            ps.setString(3, subject.getSchoolCd());
            return ps.executeUpdate() > 0;
        }
    }

    // 科目削除（物理削除）
    public boolean delete(String code) throws Exception {
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM SUBJECT WHERE CODE = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, code);
            return ps.executeUpdate() > 0;
        }
    }

    // 科目編集
    public boolean update(Subject subject) throws Exception {
        try (Connection con = getConnection()) {
            String sql = "UPDATE SUBJECT SET NAME = ? WHERE CODE = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, subject.getName());
            ps.setString(2, subject.getCode());
            return ps.executeUpdate() > 0;
        }
    }
}

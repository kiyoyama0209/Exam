package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;


public class SubjectDao extends Dao {

    // 科目一覧取得（学校コードで絞り込み）
    public List<Subject> filter(String schoolCd) throws Exception {
        List<Subject> list = new ArrayList<>();

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, schoolCd);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Subject s = new Subject();
                s.setCode(rs.getString("CD"));
                s.setName(rs.getString("NAME"));
                s.setSchoolCd(rs.getString("SCHOOL_CD"));
                list.add(s);
            }
        }

        return list;
    }

    // 科目詳細取得
    public Subject get(String cd) throws Exception {
        Subject s = null;

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM SUBJECT WHERE CD = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cd);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                s = new Subject();
                s.setCode(rs.getString("CD"));
                s.setName(rs.getString("NAME"));
                s.setSchoolCd(rs.getString("SCHOOL_CD"));
            }
        }

        return s;
    }

    // 科目登録
    public boolean save(Subject subject) throws Exception {
        boolean result = false;

        try (Connection con = getConnection()) {
            String sql = "INSERT INTO SUBJECT (CD, NAME, SCHOOL_CD) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, subject.getCd());
            ps.setString(2, subject.getName());
            ps.setString(3, subject.getSchoolCd());

            int count = ps.executeUpdate();
            result = count > 0;
        }

        return result;
    }

    // 科目削除
    public boolean delete(String cd) throws Exception {
        boolean result = false;

        try (Connection con = getConnection()) {
            String sql = "DELETE FROM SUBJECT WHERE CD = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cd);
            int count = ps.executeUpdate();
            result = count > 0;
        }

        return result;
    }
 // 科目編集（名前の更新）
    public boolean update(Subject subject) throws Exception {
        boolean result = false;

        try (Connection con = getConnection()) {
            String sql = "UPDATE SUBJECT SET NAME = ? WHERE CD = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, subject.getName());
            ps.setString(2, subject.getCd());

            int count = ps.executeUpdate();
            result = count > 0;
        }

        return result;
    }

}

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
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO SUBJECT (CD, NAME, SCHOOL_CD) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, subject.getCode());
            ps.setString(2, subject.getName());
            ps.setString(3, subject.getSchoolCd());
            return ps.executeUpdate() > 0;
        }
    }

    // 科目削除（TESTも連動削除）
    public boolean delete(String cd) throws Exception {
        boolean result = false;

        try (Connection con = getConnection()) {
            con.setAutoCommit(false); // トランザクション開始

            // ① TESTテーブルから削除
            String sql1 = "DELETE FROM TEST WHERE SUBJECT_CD = ?";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setString(1, cd);
            ps1.executeUpdate();

            // ② SUBJECTテーブルから削除
            String sql2 = "DELETE FROM SUBJECT WHERE CD = ?";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setString(1, cd);
            int count = ps2.executeUpdate();

            con.commit(); // コミット
            result = count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    // 科目編集
    public boolean update(Subject subject) throws Exception {
        try (Connection con = getConnection()) {
            String sql = "UPDATE SUBJECT SET NAME = ? WHERE CD = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, subject.getName());
            ps.setString(2, subject.getCode());
            return ps.executeUpdate() > 0;
        }
    }
}

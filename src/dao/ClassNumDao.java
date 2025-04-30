package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

/**
 * CLASS_NUM テーブル DAO
 */
public class ClassNumDao extends Dao {

    /*------------- 取得系 -------------*/

    /** 主キー取得（学校コード＋クラス番号） */
    public ClassNum get(String classNum, String schoolCd) throws Exception {
        ClassNum cn = null;
        String sql = "SELECT * FROM CLASS_NUM WHERE SCHOOL_CD = ? AND CLASS_NUM = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, schoolCd);
            ps.setString(2, classNum);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cn = new ClassNum();
                cn.setSchoolCd(rs.getString("SCHOOL_CD"));
                cn.setClassNum(rs.getString("CLASS_NUM"));
            }
        }
        return cn;
    }

    /** 学校コード → 一覧取得（ラッパー） */
    public List<ClassNum> filter(String schoolCd) throws Exception {
        School school = new School();
        school.setSchoolCd(schoolCd);
        return filter(school);
    }

    /** 学校単位で一覧取得（メイン） */
    public List<ClassNum> filter(School school) throws Exception {
        List<ClassNum> list = new ArrayList<>();
        String sql = "SELECT * FROM CLASS_NUM WHERE SCHOOL_CD = ? ORDER BY CLASS_NUM";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, school.getSchoolCd());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ClassNum c = new ClassNum();
                c.setSchoolCd(rs.getString("SCHOOL_CD"));
                c.setClassNum(rs.getString("CLASS_NUM"));
                list.add(c);
            }
        }
        return list;
    }

    /*------------- 登録／更新 -------------*/

    /** 追加 */
    public void insert(ClassNum classNum) throws Exception {
        String sql = "INSERT INTO CLASS_NUM (SCHOOL_CD, CLASS_NUM) VALUES (?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, classNum.getSchoolCd());
            ps.setString(2, classNum.getClassNum());
            ps.executeUpdate();
        }
    }

    /** 主キー（クラス番号）更新 */
    public boolean update(String oldClassNum, String newClassNum, String schoolCd) throws Exception {
        String sql = "UPDATE CLASS_NUM SET CLASS_NUM = ? " +
                     "WHERE SCHOOL_CD = ? AND CLASS_NUM = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newClassNum);
            ps.setString(2, schoolCd);
            ps.setString(3, oldClassNum);
            return ps.executeUpdate() > 0;
        }
    }

    /*------------- 付録 -------------*/

    /** 学校コード一覧（重複なし） */
    public List<String> getSchoolCdList() throws Exception {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT SCHOOL_CD FROM CLASS_NUM ORDER BY SCHOOL_CD";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(rs.getString("SCHOOL_CD"));
        }
        return list;
    }
}

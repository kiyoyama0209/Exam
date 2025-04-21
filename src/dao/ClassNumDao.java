package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

    // クラス情報1件取得
    public ClassNum get(String class_num, School school) throws Exception {
        // TODO: SQL実装
        return null;
    }

    // 学校コードからクラス番号一覧を取得（ラッパー）
    public List<ClassNum> filter(String schoolCd) throws Exception {
        School school = new School();
        school.setSchoolCd(schoolCd);
        return filter(school);
    }

    // クラス一覧を取得（メイン処理）
    public List<ClassNum> filter(School school) throws Exception {
        List<ClassNum> list = new ArrayList<>();

        String sql = "SELECT * FROM CLASS_NUM WHERE SCHOOL_CD = ? ORDER BY CLASS_NUM";

        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, school.getSchoolCd());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            ClassNum classNum = new ClassNum();
            classNum.setSchoolCd(rs.getString("SCHOOL_CD"));
            classNum.setClassNum(rs.getString("CLASS_NUM"));
            list.add(classNum);
        }

        rs.close();
        stmt.close();
        conn.close();

        return list;
    }

    public boolean save(ClassNum classNum) throws Exception {
        // TODO: DB処理
        return false;
    }

    public boolean save(ClassNum classNum, String newClassNum) throws Exception {
        // TODO: DB処理
        return false;
    }
}

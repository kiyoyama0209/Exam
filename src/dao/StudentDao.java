package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;

/**
 * STUDENT テーブル DAO
 * 主キー: NO (学籍番号)
 */
public class StudentDao extends Dao {

    /*───────────────── 1件取得 ─────────────────*/

    public Student get(String no) throws Exception {
        Student st = null;
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM STUDENT WHERE NO = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, no);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) st = map(rs);
            }
        }
        return st;
    }

    /*───────────────── 一覧取得（固定条件） ─────────────────*/

    /** 全件（学校コードのみ） */
    public List<Student> filterAll(String schoolCd) throws Exception {
        String sql = "SELECT * FROM STUDENT "
                   + "WHERE SCHOOL_CD = ? "
                   + "ORDER BY NO";
        return runQuery(sql, ps -> ps.setString(1, schoolCd));
    }

    /** 学校＋入学年度 */
    public List<Student> filter(String schoolCd, int entYear) throws Exception {
        String sql = "SELECT * FROM STUDENT "
                   + "WHERE SCHOOL_CD = ? AND ENT_YEAR = ? "
                   + "AND IS_ATTEND = TRUE ORDER BY NO";
        return runQuery(sql, ps -> {
            ps.setString(1, schoolCd);
            ps.setInt   (2, entYear);
        });
    }

    /** 学校＋入学年度＋クラス */
    public List<Student> filter(String schoolCd, int entYear, String classNum) throws Exception {
        String sql = "SELECT * FROM STUDENT "
                   + "WHERE SCHOOL_CD = ? AND ENT_YEAR = ? "
                   + "AND CLASS_NUM = ? AND IS_ATTEND = TRUE ORDER BY NO";
        return runQuery(sql, ps -> {
            ps.setString(1, schoolCd);
            ps.setInt   (2, entYear);
            ps.setString(3, classNum);
        });
    }

    /*───────────────── ★ 可変条件版（年度・クラスどちらか空可） ─────────────────*/

    /**
     * 動的条件付き検索
     * <ul>
     *   <li><code>entYear</code> が <code>null</code> → 年度条件を外す</li>
     *   <li><code>classNum</code> が <code>null</code> または空文字 → クラス条件を外す</li>
     *   <li>常に <code>IS_ATTEND = TRUE</code> を付与</li>
     * </ul>
     */
    public List<Student> filter(String schoolCd,
                                Integer entYear,
                                String  classNum) throws Exception {

        StringBuilder sql = new StringBuilder(
            "SELECT * FROM STUDENT WHERE SCHOOL_CD = ? AND IS_ATTEND = TRUE");

        List<Object> params = new ArrayList<>();
        params.add(schoolCd);

        if (entYear != null) {
            sql.append(" AND ENT_YEAR = ?");
            params.add(entYear);
        }
        if (classNum != null && !classNum.isEmpty()) {
            sql.append(" AND CLASS_NUM = ?");
            params.add(classNum);
        }
        sql.append(" ORDER BY NO");

        return runQuery(sql.toString(), ps -> {
            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof String) ps.setString(i + 1, (String) p);
                else if (p instanceof Integer) ps.setInt(i + 1, (Integer) p);
            }
        });
    }

    /*───────────────── 一覧取得（クラスのみ指定） ─────────────────*/

    /** 学校＋クラス（年度指定なし） */
    public List<Student> filter(String schoolCd, String classNum) throws Exception {
        return filter(schoolCd, null, classNum);
    }

    /*───────────────── 登録／更新 ─────────────────*/

    public boolean save(Student student) throws Exception {
        boolean result;
        boolean exists = (get(student.getNo()) != null);

        String sql = exists
            ? "UPDATE STUDENT SET NAME=?, ENT_YEAR=?, CLASS_NUM=?, IS_ATTEND=?, SCHOOL_CD=? WHERE NO=?"
            : "INSERT INTO STUDENT (NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND, SCHOOL_CD) VALUES (?,?,?,?,?,?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setInt   (2, student.getEntYear());
            ps.setString(3, student.getClassNum());
            ps.setBoolean(4, student.isAttend());
            ps.setString(5, student.getSchoolCd());
            ps.setString(6, student.getNo());

            result = ps.executeUpdate() > 0;
        }
        return result;
    }

    /*───────────────── クラス番号一括更新 ─────────────────*/

    public int updateClassNum(String oldClassNum, String newClassNum, String schoolCd) throws Exception {
        String sql = "UPDATE STUDENT SET CLASS_NUM = ? "
                   + "WHERE SCHOOL_CD = ? AND CLASS_NUM = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newClassNum);
            ps.setString(2, schoolCd);
            ps.setString(3, oldClassNum);
            return ps.executeUpdate();
        }
    }

    /*───────────────── 内部汎用ヘルパ ─────────────────*/

    /** ResultSet → Student 1件マッピング */
    private Student map(ResultSet rs) throws Exception {
        Student st = new Student();
        st.setNo       (rs.getString ("NO"));
        st.setName     (rs.getString ("NAME"));
        st.setEntYear  (rs.getInt    ("ENT_YEAR"));
        st.setClassNum (rs.getString ("CLASS_NUM"));
        st.setAttend   (rs.getBoolean("IS_ATTEND"));
        st.setSchoolCd (rs.getString ("SCHOOL_CD"));
        return st;
    }

    /** SQL とパラメータ設定ラムダで共通検索 */
    private interface ParamBinder { void bind(PreparedStatement ps) throws Exception; }

    private List<Student> runQuery(String sql, ParamBinder binder) throws Exception {
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            binder.bind(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }
}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Test;

/**
 * TEST テーブル DAO
 * 複合 PK : STUDENT_NO + SUBJECT_CD + SCHOOL_CD + NO
 */
public class TestDao extends Dao {

    /*─────────────────────────── 取得系 ───────────────────────────*/

    /**
     * 成績一覧を条件取得
     * ※ ENT_YEAR は TEST テーブルに保持していないため、呼び出し側で
     * 　 「同一入学年度の学生だけを対象にした classNum」を渡す想定
     */
    public List<Test> filter(String entYear,      // ★設計書の引数に合わせて残しておく
                             String classNum,
                             String subjectCd,
                             int    no,
                             String schoolCd) throws Exception {

        List<Test> list = new ArrayList<>();

        String sql = "SELECT STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM "
                   + "FROM TEST "
                   + "WHERE CLASS_NUM = ? "
                   + "  AND SUBJECT_CD = ? "
                   + "  AND NO         = ? "
                   + "  AND SCHOOL_CD  = ? "
                   + "ORDER BY STUDENT_NO";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, classNum);
            ps.setString(2, subjectCd);
            ps.setInt   (3, no);
            ps.setString(4, schoolCd);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Test t = new Test();
                t.setStudentNo(rs.getString("STUDENT_NO"));
                t.setSubjectCd(rs.getString("SUBJECT_CD"));
                t.setSchoolCd (rs.getString("SCHOOL_CD"));
                t.setNo       (rs.getInt   ("NO"));
                t.setPoint    (rs.getInt   ("POINT"));
                t.setClassNum (rs.getString("CLASS_NUM"));
                list.add(t);
            }
        }
        return list;
    }

    /*──────────────────────── 登録／更新系 ────────────────────────*/

    /**
     * 1 レコード保存（INSERT or UPDATE）
     * H2 の MERGE を使い、複合 PK が存在すれば UPDATE、無ければ INSERT。
     * @return true…挿入または更新 1 行以上成功
     */
    public boolean save(Test test) throws Exception {

        String sql = "MERGE INTO TEST "
                   + "(STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) "
                   + "KEY (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO) "
                   + "VALUES (?,?,?,?,?,?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, test.getStudentNo());
            ps.setString(2, test.getSubjectCd());
            ps.setString(3, test.getSchoolCd());
            ps.setInt   (4, test.getNo());
            ps.setInt   (5, test.getPoint());
            ps.setString(6, test.getClassNum());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * 複数一覧を一括保存
     * @return 成功件数
     */
    public int save(List<Test> tests) throws Exception {
        int cnt = 0;
        for (Test t : tests) {
            if (save(t)) cnt++;
        }
        return cnt;
    }
}

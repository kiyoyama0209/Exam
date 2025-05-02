package scoremanager.main;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

/**
 * 成績登録 検索／登録アクション (TSTR002 / TSTR003)
 * URL : /TestRegistExecute.action
 */
public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        /* ① ログイン確認 */
        HttpSession ses = req.getSession();
        Teacher teacher = (Teacher) ses.getAttribute("user");
        if (teacher == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        String schoolCd = teacher.getSchoolCd();

        /* ② 共通プルダウン再生成 */
        buildPulldowns(req, schoolCd);

        /* ③ パラメータ取得 */
        String mode       = req.getParameter("mode");        // "register" のとき得点登録
        String entYearStr = req.getParameter("entYear");
        String classNum   = req.getParameter("classNum");
        String subjectCd  = req.getParameter("subjectCd");
        String noStr      = req.getParameter("no");

        Integer entYear = null;
        Integer no      = null;
        try {
            entYear = (entYearStr == null || entYearStr.isEmpty()) ? null : Integer.parseInt(entYearStr);
            no      = (noStr      == null || noStr.isEmpty())      ? null : Integer.parseInt(noStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "検索条件が不正です");
            req.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(req, res);
            return;
        }

        /* ======== 検索モード ======== */
        if (!"register".equals(mode)) {

            StudentDao sDao = new StudentDao();
            List<Student> students = sDao.filter(schoolCd, entYear, classNum); // ← 可変条件版

            // 既存得点を取得
            Map<String,Integer> pointMap = fetchPointMap(entYear, classNum, subjectCd, no, schoolCd);

            /* 検索条件と結果を保持 */
            req.setAttribute("students",     students);
            req.setAttribute("pointMap",     pointMap);
            req.setAttribute("selEntYear",   entYear);
            req.setAttribute("selClassNum",  classNum);
            req.setAttribute("selSubjectCd", subjectCd);
            req.setAttribute("selNo",        no);

            req.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(req, res);
            return;
        }

        /* ======== 登録モード ======== */

        if (entYear == null || classNum == null || subjectCd == null || no == null) {
            req.setAttribute("error", "検索条件が不足しています");
            req.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(req, res);
            return;
        }

        TestDao tDao = new TestDao();
        int success = 0, fail = 0;

        String[] studentNos = req.getParameterValues("studentNo");
        if (studentNos != null) {
            for (String stuNo : studentNos) {
                String pStr = req.getParameter("point_" + stuNo);
                if (pStr == null || pStr.isEmpty()) continue;
                try {
                    int point = Integer.parseInt(pStr);
                    if (point < 0 || point > 100) throw new NumberFormatException();

                    Test t = new Test();
                    t.setStudentNo(stuNo);
                    t.setSubjectCd(subjectCd);
                    t.setSchoolCd (schoolCd);
                    t.setNo       (no);
                    t.setPoint    (point);
                    t.setClassNum (classNum);

                    tDao.save(t);
                    success++;

                } catch (NumberFormatException e) {
                    fail++;
                }
            }
        }

        /* 完了メッセージ */
        String msg = "登録完了 (成功：" + success + " 件／エラー：" + fail + " 件)";
        req.setAttribute("message", msg);

        /* 検索モードと同じデータを再表示 */
        StudentDao sDao = new StudentDao();
        List<Student> students = sDao.filter(schoolCd, entYear, classNum);
        Map<String,Integer> pointMap = fetchPointMap(entYear, classNum, subjectCd, no, schoolCd);

        req.setAttribute("students",     students);
        req.setAttribute("pointMap",     pointMap);
        req.setAttribute("selEntYear",   entYear);
        req.setAttribute("selClassNum",  classNum);
        req.setAttribute("selSubjectCd", subjectCd);
        req.setAttribute("selNo",        no);

        req.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(req, res);
    }

    /*───────────────── ヘルパ ─────────────────*/

    /** 年度・クラス・科目・回数で既存得点を Map 化 */
    private Map<String,Integer> fetchPointMap(Integer entYear,
                                              String classNum,
                                              String subjectCd,
                                              Integer no,
                                              String schoolCd) throws Exception {

        Map<String,Integer> map = new HashMap<>();
        if (classNum == null || subjectCd == null || no == null) return map;

        TestDao tDao = new TestDao();
        List<Test> tests = tDao.filter(
            String.valueOf(entYear != null ? entYear : ""), // entYear は使われないが引数合わせ
            classNum, subjectCd, no, schoolCd);

        for (Test t : tests) map.put(t.getStudentNo(), t.getPoint());
        return map;
    }

    /** プルダウンリストを生成してリクエストに格納 */
    private void buildPulldowns(HttpServletRequest req, String schoolCd) throws Exception {
        List<Integer> years = new ArrayList<>();
        int now = Year.now().getValue();
        for (int i = -10; i <= 1; i++) years.add(now + i);

        ClassNumDao cDao = new ClassNumDao();
        SubjectDao  sDao = new SubjectDao();

        req.setAttribute("years",     years);
        req.setAttribute("classNums", cDao.filter(schoolCd));
        req.setAttribute("subjects",  sDao.filter(schoolCd));
    }
}

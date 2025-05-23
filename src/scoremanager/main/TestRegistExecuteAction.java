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

        /* 1. ログイン確認 */
        HttpSession ses = req.getSession();
        Teacher teacher = (Teacher) ses.getAttribute("user");
        if (teacher == null) {
            res.sendRedirect(req.getContextPath() + "/scoremanager/main/login.jsp");
            return;
        }
        String schoolCd = teacher.getSchoolCd();

        /* 2. 共通プルダウン再生成 */
        buildPulldowns(req, schoolCd);

        /* 3. パラメータ取得 */
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
            req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
               .forward(req, res);
            return;
        }

        /* ---------- 検索モード ---------- */
        if (!"register".equals(mode)) {

            /* ★ 必須入力チェック */
            if (entYear == null
                    || classNum == null || classNum.isEmpty()
                    || subjectCd == null || subjectCd.isEmpty()
                    || no == null) {

                req.setAttribute("error", "入学年度とクラスと科目と回数を選択してください");
                req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
                   .forward(req, res);
                return;
            }

            /* 学生一覧 + 既存点数取得 */
            StudentDao sDao = new StudentDao();
            List<Student> students = sDao.filter(schoolCd, entYear, classNum);

            Map<String,Integer> pointMap =
                fetchPointMap(entYear, classNum, subjectCd, no, schoolCd);

            /* 検索条件と結果を保持 */
            req.setAttribute("students",     students);
            req.setAttribute("pointMap",     pointMap);
            req.setAttribute("selEntYear",   entYear);
            req.setAttribute("selClassNum",  classNum);
            req.setAttribute("selSubjectCd", subjectCd);
            req.setAttribute("selNo",        no);

            req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
               .forward(req, res);
            return;
        }

        /* ---------- 登録モード ---------- */

        if (entYear == null || classNum == null || subjectCd == null || no == null) {
            req.setAttribute("error", "検索条件が不足しています");
            req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
               .forward(req, res);
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

        /* 登録完了メッセージ */
        req.setAttribute("message",
            String.format("登録 %d 件、エラー %d 件で完了しました。", success, fail));
        req.getRequestDispatcher("/scoremanager/main/test_regist_done.jsp")
           .forward(req, res);
    }

    /* ===== ヘルパ ===== */

    /** 既存得点を Map 化 */
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

    /** プルダウンリスト生成 */
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

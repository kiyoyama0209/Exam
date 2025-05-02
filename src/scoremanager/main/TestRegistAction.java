package scoremanager.main;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

/**
 * 成績登録 画面表示 (TSTR001)
 * URL : /TestRegist.action
 */
public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        /* ① ログインチェック */
        HttpSession ses = req.getSession();
        Teacher teacher = (Teacher) ses.getAttribute("user");
        if (teacher == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String schoolCd = teacher.getSchoolCd();

        /* ② プルダウン共通生成 ― 年度・クラス・科目 ― */
        List<Integer> years = new ArrayList<>();
        int now = Year.now().getValue();
        for (int i = -10; i <= 1; i++) years.add(now + i);

        ClassNumDao  cDao = new ClassNumDao();
        SubjectDao   sDao = new SubjectDao();
        List<ClassNum> classNums = cDao.filter(schoolCd);
        List<Subject>  subjects  = sDao.filter(schoolCd);

        req.setAttribute("years",     years);
        req.setAttribute("classNums", classNums);
        req.setAttribute("subjects",  subjects);

        /* ③ 画面へ */
        req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
           .forward(req, res);
    }
}

package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

/**
 * 科目変更 画面表示 (SBJT004)
 * URL : /SubjectUpdate.action
 */
public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        /* ① ログインチェック */
        HttpSession ses = req.getSession();
        Teacher teacher = (Teacher) ses.getAttribute("user");
        if (teacher == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        /* ② エラー再表示時は request に subject が載っている */
        Subject subject = (Subject) req.getAttribute("subject");
        if (subject == null) {
            /* 初回表示時はパラメータの cd から 1 件取得 */
            String cd = req.getParameter("cd");
            if (cd == null || cd.isEmpty()) {
                res.sendRedirect(req.getContextPath() + "/SubjectList.action");
                return;
            }

            SubjectDao dao = new SubjectDao();
            subject = dao.get(cd);
            if (subject == null) {
                res.sendRedirect(req.getContextPath() + "/SubjectList.action");
                return;
            }
        }

        /* ③ JSP へ */
        req.setAttribute("subject", subject);
        req.getRequestDispatcher("/scoremanager/main/subject_update.jsp")
           .forward(req, res);
    }
}

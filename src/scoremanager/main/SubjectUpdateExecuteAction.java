package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

/**
 * 科目変更 実行 (SBJT005 → SBJT006)
 * URL : /SubjectUpdateExecute.action
 */
public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        /* ① ログイン確認 */
        HttpSession ses = req.getSession();
        Teacher teacher = (Teacher) ses.getAttribute("user");
        if (teacher == null) {
        	req.getRequestDispatcher("../login.jsp").forward(req, res);
        	return;
        }

        /* ② パラメータ取得 */
        String cd   = req.getParameter("cd");   // 主キー
        String name = req.getParameter("name");

        /* ③ 入力バリデーション */
        boolean hasErr = false;
        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("errorName", "科目名を入力してください");
            hasErr = true;
        }

        if (hasErr) {
            /* 入力済み値を保持して画面へ戻す */
            Subject backup = new Subject();
            backup.setCode(cd);
            backup.setName(name);
            backup.setSchoolCd(teacher.getSchoolCd());

            req.setAttribute("subject", backup);
            req.getRequestDispatcher("SubjectUpdate.action").forward(req, res);
            return;
        }

        /* ④ 更新処理 */
        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(cd);
        if (subject == null) {
            res.sendRedirect("SubjectList.action");
            return;
        }
        subject.setName(name);
        dao.update(subject);

        /* ⑤ 完了画面へ */
        req.setAttribute("message", "科目情報の変更が完了しました");
        req.getRequestDispatcher("subject_update_done.jsp")
           .forward(req, res);
    }
}

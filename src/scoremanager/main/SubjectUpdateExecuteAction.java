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
            backup.setSchoolCd(teacher.getSchoolCd()); // Important for re-display consistency

            req.setAttribute("subject", backup);
            // Forward to SubjectUpdate.action which will render the update page
            req.getRequestDispatcher("SubjectUpdate.action").forward(req, res);
            return;
        }

        /* ④ 更新処理 */
        SubjectDao dao = new SubjectDao();
        Subject subjectToUpdate = dao.get(cd); // Try to get the subject from DB

        if (subjectToUpdate == null) {
            // Subject was not found, likely deleted after the form was loaded.
            req.setAttribute("errorGeneral", "科目が存在しません");

            // Create a subject bean with the data the user attempted to submit
            // so the form can be re-populated with their entries.
            Subject attemptedSubject = new Subject();
            attemptedSubject.setCode(cd);
            attemptedSubject.setName(name);
            attemptedSubject.setSchoolCd(teacher.getSchoolCd());

            req.setAttribute("subject", attemptedSubject); // Pass this back to the form

            // Forward to SubjectUpdate.action, which will display subject_update.jsp
            // The 'subject' and 'errorGeneral' attributes will be available.
            req.getRequestDispatcher("SubjectUpdate.action").forward(req, res);
            return;
        }

        // If subject exists, proceed with update
        subjectToUpdate.setName(name);
        // The schoolCd of the subjectToUpdate should already be correct from the DB
        // subjectToUpdate.setSchoolCd(teacher.getSchoolCd()); // Usually not changed here

        dao.update(subjectToUpdate);

        /* ⑤ 完了画面へ */
        req.setAttribute("message", "科目情報の変更が完了しました");
        req.getRequestDispatcher("subject_update_done.jsp")
           .forward(req, res);
    }
}
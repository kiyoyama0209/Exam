package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションから教員情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログインの場合、ログイン画面へ
        if (teacher == null) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // リクエストから科目コード取得
        String code = request.getParameter("code");

        // 科目を削除
        SubjectDao subjectDao = new SubjectDao();
        boolean success = subjectDao.delete(code);

        // 完了メッセージを設定
        if (success) {
            request.setAttribute("message", "科目を削除しました。");
        } else {
            request.setAttribute("message", "科目の削除に失敗しました。");
        }

        // 完了画面へ
        request.getRequestDispatcher("scoremanager/main/subject_delete_done.jsp").forward(request, response);
    }
}

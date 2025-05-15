package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // ①ログインチェック
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ②パラメータ取得
        String code = request.getParameter("code");
        String name = request.getParameter("name");

        // ③登録処理準備
        Subject subject = new Subject();
        subject.setCode(code);
        subject.setName(name);
        subject.setSchoolCd(teacher.getSchoolCd());

        // ④保存処理と例外処理
        try {
            SubjectDao dao = new SubjectDao();
            dao.save(subject);

            // ⑤成功時は完了画面へ遷移
            request.setAttribute("message", "科目の登録が完了しました。");
            request.getRequestDispatcher("/scoremanager/main/subject_create_done.jsp")
                   .forward(request, response);
        } catch (Exception e) {
            // 重複エラー等の例外時は入力画面へ戻す
            request.setAttribute("errorNo", "※この科目コードは既に使用されています。");
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.getRequestDispatcher("/scoremanager/main/subject_create.jsp")
                   .forward(request, response);
        }
    }
}

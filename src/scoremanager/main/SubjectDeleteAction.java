package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;



public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションから教員情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログインの場合、ログイン画面へ
        if (teacher == null) {
        	request.getRequestDispatcher("../login.jsp").forward(request, response);
        	return;
        }

        // パラメータから科目コードを取得
        String code = request.getParameter("code");

        // 科目情報を取得
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(code);

        // 該当科目がなければエラー表示
        if (subject == null) {
            request.setAttribute("error", "指定された科目が存在しません。");
            request.getRequestDispatcher("subject_list.jsp").forward(request, response);
            return;
        }

        // JSPに科目情報を渡す
        request.setAttribute("subject", subject);
        request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
    }
}

package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class LoginExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // パラメータ取得
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        // 未入力チェック
        if (id == null || id.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "このフィールドを入力してください");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // 教員データ取得
        TeacherDao dao = new TeacherDao();
        Teacher teacher = dao.get(id);

        if (teacher == null || !teacher.getPassword().equals(password)) {
            // エラー処理
            request.setAttribute("error", "IDまたはパスワードが確認できませんでした");
            request.setAttribute("id", id);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // ここで認証フラグをtrueにする
        teacher.setAuthenticated(true);

        // セッションに格納
        HttpSession session = request.getSession();
        session.setAttribute("user", teacher);

        // メニュー画面にリダイレクト
        response.sendRedirect("main/Menu.action");
    }
}
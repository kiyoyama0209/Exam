package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッション破棄
        HttpSession session = request.getSession();
        session.invalidate();

        // ログイン画面にリダイレクト
        response.sendRedirect("login.jsp");
    }
}

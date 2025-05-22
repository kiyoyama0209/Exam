package scoremanager.main;

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

        // ログアウト画面を表示
        request.getRequestDispatcher("logout.jsp").forward(request, response);
    }
}

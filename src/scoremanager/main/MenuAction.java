package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

/**
 * メインメニュー表示アクション（MMNU001）
 * URL : /Menu.action
 */
public class MenuAction extends Action {

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        // 画面をそのまま表示させるだけ
        request.getRequestDispatcher("/scoremanager/main/menu.jsp")
               .forward(request, response);
    }
}

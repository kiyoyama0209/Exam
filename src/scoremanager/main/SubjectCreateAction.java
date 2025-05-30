package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import tool.Action;

public class SubjectCreateAction extends Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response
			) throws Exception {
        // ①ログインチェック
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) {
        	request.getRequestDispatcher("../login.jsp").forward(request, response);
        	return;
        }
        request.getRequestDispatcher("subject_create.jsp"
        		).forward(request, response);
	}
}

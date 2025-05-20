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
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            request.getRequestDispatcher("/scoremanager/main/login.jsp").forward(request, response);
            return;
        }

        String code = request.getParameter("code");
        SubjectDao subjectDao = new SubjectDao();
        boolean result = subjectDao.delete(code);

        if (result) {
            request.setAttribute("message", "科目を削除しました。");
        } else {
            request.setAttribute("message", "科目の削除に失敗しました。");
        }

        request.getRequestDispatcher("scoremanager/main/subject_delete_done.jsp").forward(request, response);
    }
}
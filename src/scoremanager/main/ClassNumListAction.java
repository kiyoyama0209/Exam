package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassNumListAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        String schoolCd = teacher.getSchoolCd();

        ClassNumDao classNumDao = new ClassNumDao();
        List<ClassNum> list = classNumDao.filter(schoolCd);

        request.setAttribute("list", list);

        request.getRequestDispatcher("/scoremanager/main/classnum_list.jsp").forward(request, response);
    }
}

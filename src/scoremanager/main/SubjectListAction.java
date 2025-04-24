package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        String schoolCd = teacher.getSchoolCd();
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subject = subjectDao.filter(schoolCd);

        request.setAttribute("subject", subject);
        request.getRequestDispatcher("scoremanager/main/subject_list.jsp").forward(request, response);
    }
}
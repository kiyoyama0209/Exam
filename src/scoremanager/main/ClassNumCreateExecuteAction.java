package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassNumCreateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        String classNumStr = request.getParameter("classNum");

        ClassNum classNum = new ClassNum();
        classNum.setClassNum(classNumStr);
        classNum.setSchoolCd(teacher.getSchoolCd());  // ログイン中の先生の学校コードをセット

        ClassNumDao classNumDao = new ClassNumDao();
        classNumDao.insert(classNum);  // INSERT処理（↓下で説明）

        request.getRequestDispatcher("/scoremanager/main/classnum_create_done.jsp").forward(request, response);
    }
}

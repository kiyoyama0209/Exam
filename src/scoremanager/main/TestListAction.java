package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.School;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションからログイン中の学校情報を取得
        School school = (School) request.getSession().getAttribute("user");

        if (school == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 学生情報取得
        StudentDao studentDao = new StudentDao();
        List<Student> students = studentDao.filter(school);

        // クラス番号一覧取得
        ClassNumDao classNumDao = new ClassNumDao();
        List<ClassNum> classNums = classNumDao.filter(school);

        // リクエスト属性にセット
        request.setAttribute("students", students);
        request.setAttribute("classNums", classNums);

        // 表示用JSPにフォワード
        request.getRequestDispatcher("test_list.jsp").forward(request, response);
    }
}
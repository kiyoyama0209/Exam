package scoremanager.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
        	HttpSession session = req.getSession();
            Teacher teacher = (Teacher) session.getAttribute("user");

            String schoolCd = teacher.getSchoolCd();

            // パラメータの取得
            int entYear = Integer.parseInt(req.getParameter("entYear"));
            String classNum = req.getParameter("classNum");
            String subjectCd = req.getParameter("subjectCd");


            // Subject, School オブジェクトの生成
            Subject subject = new Subject();
            subject.setCode(subjectCd);

            School school = new School();
            school.setSchoolCd(schoolCd);

            // DAO から成績一覧取得
            TestListSubjectDao dao = new TestListSubjectDao();
            List<TestListSubject> list = dao.filter(entYear, classNum, subject, school);



            // 取得結果をリクエストに格納
            req.setAttribute("testListSubject", list);

            // JSP へフォワード
            RequestDispatcher dispatcher = req.getRequestDispatcher("/scoremanager/main/test_list_subject.jsp");
            dispatcher.forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "成績一覧の取得中にエラーが発生しました。");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/error.jsp");
            dispatcher.forward(req, res);
        }
    }
}
package scoremanager.main;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.TestListSubject;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // リクエストパラメータ取得
        int entYear = Integer.parseInt(request.getParameter("ent_year"));
        String classNum = request.getParameter("class_num");
        String subjectCd = request.getParameter("subject_cd");
        String schoolCd = request.getParameter("school_cd");

        // SubjectとSchoolをセット
        Subject subject = new Subject();
        subject.setSchoolCd(subjectCd); // ←ここ注意：schoolCdじゃなくてsubjectCd使うならフィールドと合わせてね
        subject.setCode(subjectCd);

        School school = new School();
        school.setSchoolCd(schoolCd);

        // DAO呼び出し
        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> list = dao.filter(entYear, classNum, subject, school);

        // リクエストスコープに格納
        request.setAttribute("testListSubjectList", list);

        // JSPへフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("test_list_subject.jsp");
        dispatcher.forward(request, response);
    }
}
package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.Test;
import dao.TestDao;
import tool.Action;

public class TestDeleteExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        String schoolCd = teacher.getSchoolCd();
        String subjectCd = request.getParameter("subjectCd");
        int no = Integer.parseInt(request.getParameter("no"));

        String[] studentNos = request.getParameterValues("studentNo");

        List<Test> deleteList = new ArrayList<>();
        for (String studentNo : studentNos) {
            Test t = new Test();
            t.setStudentNo(studentNo);
            t.setSubjectCd(subjectCd);
            t.setNo(no);
            t.setSchoolCd(schoolCd);
            deleteList.add(t);
        }

        TestDao dao = new TestDao();
        dao.deleteTests(deleteList);

        request.setAttribute("message", "削除が完了しました。");
        request.getRequestDispatcher("/scoremanager/main/test_delete_done.jsp").forward(request, response);
    }
}

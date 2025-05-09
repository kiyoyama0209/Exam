package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import bean.TestListStudent;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 学生番号を取得
        String studentNo = request.getParameter("studentId");

        // 入力がない場合は終了
        if (studentNo == null || studentNo.isEmpty()) {
            request.setAttribute("message", "学生番号を入力してください。");
            request.getRequestDispatcher("/scoremanager/main/test_list.jsp").forward(request, response);
            return;
        }

        // 学生Beanの作成
        Student student = new Student();
        student.setNo(studentNo);

        // DAOで検索
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> list = dao.filter(student);

        // 結果をリクエストスコープにセット
        request.setAttribute("testListStudent", list);
        request.setAttribute("studentId", studentNo); // 再表示用

        // JSPへフォワード
        request.getRequestDispatcher("/scoremanager/main/test_list_student.jsp").forward(request, response);
    }
}
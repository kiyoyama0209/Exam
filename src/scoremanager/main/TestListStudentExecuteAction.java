package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String schoolCd = teacher.getSchoolCd();

     // フィルタパラメータ
        String entYearStr = request.getParameter("f1");

        Integer ent_Year = null;

        // 入学年度
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                ent_Year = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                ent_Year = null;
            }
        }
     // 年度プルダウン（今年±10年）
        List<Integer> years = new ArrayList<>();
        int currentYear = java.time.Year.now().getValue();
        for (int i = -10; i <= 1; i++) {
            years.add(currentYear + i);
        }
        request.setAttribute("years", years);

        // クラス番号プルダウン
        ClassNumDao classNumDao = new ClassNumDao();
        List<ClassNum> classNums = classNumDao.filter(schoolCd);
        request.setAttribute("classNums", classNums);

     // ★ 科目プルダウン
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjects = subjectDao.filter(schoolCd); // schoolCdでフィルタ
        request.setAttribute("subjects", subjects);

        // 学生番号を取得
        String studentNo = request.getParameter("studentId");

        StudentDao studentDao = new StudentDao();
        Student student = null;
        try {
            student = studentDao.get(studentNo);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 学生Beanの作成
        student.setNo(studentNo);

        // DAOで検索
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> list = dao.filter(student);

        // 結果をリクエストスコープにセット
        request.setAttribute("testListStudent", list);
        request.setAttribute("studentId", studentNo); // 再表示用
        request.setAttribute("student", student);

        // JSPへフォワード
        request.getRequestDispatcher("test_list_student.jsp").forward(request, response);
    }
}
package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// ログイン情報
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // teacherがnullなら未ログイン扱い
        if (teacher == null) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // 学校コードを取得
        String schoolCd = teacher.getSchoolCd();

        // フィルタパラメータ
        String entYearStr = request.getParameter("f1");

        Integer entYear = null;

        // 入学年度
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                entYear = null;
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


        // 表示用JSPにフォワード

        request.getRequestDispatcher("scoremanager/main/test_list.jsp").forward(request, response);
    }
}
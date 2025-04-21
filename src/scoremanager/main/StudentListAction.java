package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {
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
        String classNum = request.getParameter("f2");
        String isAttendStr = request.getParameter("f3");

        Integer entYear = null;
        Boolean isAttend = null;

        // 入学年度
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                entYear = null;
            }
        }

        // 在学中フラグ
        if (isAttendStr != null && !isAttendStr.isEmpty()) {
            isAttend = Boolean.parseBoolean(isAttendStr);
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

        // 学生一覧
        StudentDao studentDao = new StudentDao();
        List<Student> students;

        // 分岐
        if (entYear == null && (classNum == null || classNum.isEmpty()) && isAttend == null) {
            students = studentDao.filterAll(schoolCd); // 全件取得
        } else if (entYear != null && (classNum == null || classNum.isEmpty()) && isAttend == null) {
            students = studentDao.filter(schoolCd, entYear);
        } else if (entYear != null && classNum != null && !classNum.isEmpty() && isAttend == null) {
            students = studentDao.filter(schoolCd, entYear, classNum);
        } else if (entYear != null && (classNum == null || classNum.isEmpty()) && isAttend != null) {
            students = studentDao.filter(schoolCd, entYear, isAttend);
        } else if (entYear != null && classNum != null && !classNum.isEmpty() && isAttend != null) {
            students = studentDao.filter(schoolCd, entYear, classNum, isAttend);
        } else if (entYear == null && (classNum == null || classNum.isEmpty()) && isAttend != null) {
            students = studentDao.filter(schoolCd, isAttend);
        } else {
            students = studentDao.filterAll(schoolCd); // fallback も全件取得
        }

        // リクエストに詰めてJSPへ
        request.setAttribute("students", students);
        request.getRequestDispatcher("scoremanager/main/student_list.jsp").forward(request, response);
    }
}

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

        // ログインチェック
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
        	request.getRequestDispatcher("../login.jsp").forward(request, response);           
        	return;
        }

        // 学校コード取得
        String schoolCd = teacher.getSchoolCd();

        // パラメータ取得
        String entYearStr  = request.getParameter("f1");
        String classNum    = request.getParameter("f2");
        String isAttendStr = request.getParameter("f3");

        Integer entYear = null;
        Boolean isAttend = null;

        // 入学年度パース
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                entYear = null;
            }
        }

        // 在学中パース
        if (isAttendStr != null && !isAttendStr.isEmpty()) {
            isAttend = Boolean.parseBoolean(isAttendStr);
        }

        // 年度プルダウン生成
        List<Integer> years = new ArrayList<>();
        int currentYear = java.time.Year.now().getValue();
        for (int i = -10; i <= 1; i++) {
            years.add(currentYear + i);
        }
        request.setAttribute("years", years);

        // クラス番号プルダウン生成
        ClassNumDao classNumDao = new ClassNumDao();
        List<ClassNum> classNums = classNumDao.filter(schoolCd);
        request.setAttribute("classNums", classNums);

        // ★ クラスのみ指定され、入学年度が未指定の場合 → エラー表示して終了
        if ((entYear == null || entYearStr.isEmpty()) && classNum != null && !classNum.isEmpty()) {
            request.setAttribute("error", "クラスを指定する場合は入学年度も指定してください");
            request.setAttribute("students", new ArrayList<Student>());  // 空のリストを渡す
            request.getRequestDispatcher("student_list.jsp").forward(request, response);
            return;
        }

        // 学生リスト取得
        StudentDao studentDao = new StudentDao();
        List<Student> students;

        // 条件分岐
        if (entYear == null && (classNum == null || classNum.isEmpty()) && isAttend == null) {
            students = studentDao.filterAll(schoolCd);
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
            students = studentDao.filterAll(schoolCd);
        }

        // リクエスト属性にセットしてJSPへ
        request.setAttribute("students", students);
        request.getRequestDispatcher("student_list.jsp").forward(request, response);
    }
}

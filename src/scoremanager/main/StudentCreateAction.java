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
import tool.Action;

public class StudentCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // ログイン中の教員情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログインならログイン画面に戻す
        if (teacher == null) {
        	request.getRequestDispatcher("../login.jsp").forward(request, response);           
        	return;
        }

        // 学校コード取得
        String schoolCd = teacher.getSchoolCd();

        // 年度リスト生成（現在年 ±10年）
        List<Integer> years = new ArrayList<>();
        int currentYear = java.time.Year.now().getValue();
        for (int i = -10; i <= 1; i++) {
            years.add(currentYear + i);
        }

        // クラス一覧取得
        ClassNumDao classNumDao = new ClassNumDao();
        List<ClassNum> classNums = classNumDao.filter(schoolCd);

        // 空のStudentインスタンス（フォーム初期値）
        Student student = new Student();
        student.setSchoolCd(schoolCd); // デフォルトで学校コードだけ入れておく

        // JSPへ渡す
        request.setAttribute("years", years);
        request.setAttribute("classNums", classNums);
        request.setAttribute("student", student);

        request.getRequestDispatcher("student_create.jsp").forward(request, response);
    }
}

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

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        /* ① ログインチェック */
        HttpSession ses = req.getSession();
        Teacher t = (Teacher) ses.getAttribute("user");
        if (t == null) {
            res.sendRedirect(req.getContextPath() + "/scoremanager/main/login.jsp");
            return;
        }

        /* ② 既に student が渡っていれば再利用（エラー再表示時） */
        Student student = (Student) req.getAttribute("student");
        if (student == null) {
            String no = req.getParameter("no");
            if (no == null || no.isEmpty()) {
                res.sendRedirect(req.getContextPath() + "/StudentList.action");
                return;
            }
            /* ③ DB 取得 */
            StudentDao sDao = new StudentDao();
            student = sDao.get(no);
            if (student == null) {
                res.sendRedirect(req.getContextPath() + "/StudentList.action");
                return;
            }
        }

        /* ④ プルダウンデータ共通生成 */
        List<Integer> years = new ArrayList<>();
        int now = java.time.Year.now().getValue();
        for (int i = -10; i <= 1; i++) years.add(now + i);

        ClassNumDao cDao = new ClassNumDao();
        List<ClassNum> classNums = cDao.filter(student.getSchoolCd());

        /* ⑤ JSP へ */
        req.setAttribute("years", years);
        req.setAttribute("classNums", classNums);
        req.setAttribute("student", student);

        req.getRequestDispatcher("/scoremanager/main/student_update.jsp").forward(req, res);
    }
}

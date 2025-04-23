package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {
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


        // 科目一覧
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subject = subjectDao.filter(schoolCd); //学校コードをもとに科目情報をsubjectに入れる

        // リクエストに詰めてJSPへ
        request.setAttribute("subject", subject);
        request.getRequestDispatcher("scoremanager/main/subject_list.jsp").forward(request, response);
    }
}
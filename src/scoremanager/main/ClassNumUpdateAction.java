package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

/** クラス番号変更 画面表示 (CLSN004) */
public class ClassNumUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        /* ① ログイン確認 */
        HttpSession ses = req.getSession();
        Teacher teacher = (Teacher) ses.getAttribute("user");
        if (teacher == null) {
        	req.getRequestDispatcher("../login.jsp").forward(req, res);
        	return;
        }

        /* ② 変更対象を取得 */
        String classNum = req.getParameter("classNum");
        if (classNum == null || classNum.isEmpty()) {
            res.sendRedirect("ClassNumList.action");
            return;
        }

        ClassNumDao dao = new ClassNumDao();
        ClassNum cn = dao.get(classNum, teacher.getSchoolCd());
        if (cn == null) {
            res.sendRedirect("ClassNumList.action");
            return;
        }

        /* ③ JSPへ */
        req.setAttribute("classNum", cn);
        req.getRequestDispatcher("classnum_update.jsp")
           .forward(req, res);
    }
}

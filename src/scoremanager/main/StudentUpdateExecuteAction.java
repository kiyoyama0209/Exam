package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        /* ① ログイン確認 */
        HttpSession ses = req.getSession();
        Teacher t = (Teacher) ses.getAttribute("user");
        if (t == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        /* ② パラメータ取得 */
        String no       = req.getParameter("no");
        String name     = req.getParameter("name");
        String classNum = req.getParameter("classNum");
        boolean attend  = (req.getParameter("isAttend") != null);

        /* ③ バリデーション */
        boolean hasErr = false;
        if (name == null || name.isEmpty()) {
            req.setAttribute("errorName", "氏名を入力してください");
            hasErr = true;
        }
        if (classNum == null || classNum.isEmpty()) {
            req.setAttribute("errorClass", "クラスを選択してください");
            hasErr = true;
        }

        if (hasErr) {
            /* 入力済み保持 */
            Student st = new Student();
            st.setNo(no); st.setName(name);
            st.setClassNum(classNum); st.setAttend(attend);

            StudentDao dao = new StudentDao();
            Student org = dao.get(no);
            if (org != null) {
                st.setEntYear(org.getEntYear());
                st.setSchoolCd(org.getSchoolCd());
            }
            req.setAttribute("student", st);

            /* StudentUpdateAction へフォワード（no は student に入っているので不要） */
            req.getRequestDispatcher("/StudentUpdate.action").forward(req, res);
            return;
        }

        /* ④ 更新 */
        StudentDao dao = new StudentDao();
        Student st = dao.get(no);
        if (st == null) {
            res.sendRedirect(req.getContextPath() + "/StudentList.action");
            return;
        }
        st.setName(name); st.setClassNum(classNum); st.setAttend(attend);
        dao.save(st);

        /* ⑤ 完了画面へ ― パス typo 修正 ― */
        req.setAttribute("message", "学生情報の変更が完了しました");
        req.getRequestDispatcher("/scoremanager/main/student_update_done.jsp")
           .forward(req, res);
    }
}

package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

/** クラス番号変更 実行 (CLSN005 → CLSN006) */
public class ClassNumUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        /* ① ログイン確認 */
        HttpSession ses = req.getSession();
        Teacher teacher = (Teacher) ses.getAttribute("user");
        if (teacher == null) {
            res.sendRedirect(req.getContextPath() + "/scoremanager/main/login.jsp");
            return;
        }

        /* ② パラメータ取得 */
        String oldClassNum = req.getParameter("oldClassNum"); // hidden
        String newClassNum = req.getParameter("classNum");
        String schoolCd    = teacher.getSchoolCd();

        /* ③ バリデーション */
        boolean hasErr = false;
        if (newClassNum == null || newClassNum.trim().isEmpty()) {
            req.setAttribute("errorClassNum", "クラス番号を入力してください");
            hasErr = true;
        }

        ClassNumDao cDao = new ClassNumDao();
        if (!hasErr && cDao.get(newClassNum, schoolCd) != null) {
            req.setAttribute("errorClassNum", "そのクラス番号は既に存在します");
            hasErr = true;
        }

        if (hasErr) {
            req.getRequestDispatcher("/ClassNumUpdate.action")
               .forward(req, res);
            return;
        }

        /* ④ クラス番号テーブル更新 */
        cDao.update(oldClassNum, newClassNum, schoolCd);

        /* ⑤ STUDENT テーブルのクラス番号を一括更新 */
        StudentDao sDao = new StudentDao();
        int updatedRows = sDao.updateClassNum(oldClassNum, newClassNum, schoolCd);

        /* ⑥ 完了画面へ */
        String msg = "クラス番号を「" + oldClassNum + " → " + newClassNum + "」へ変更しました。"
                   + "学生 " + updatedRows + " 名の所属クラスも更新しました。";
        req.setAttribute("message", msg);
        req.getRequestDispatcher("/scoremanager/main/classnum_update_done.jsp")
           .forward(req, res);
    }
}

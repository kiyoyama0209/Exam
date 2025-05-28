package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

/** クラス番号新規登録 実行 (CLSN002 → CLSN003) */
public class ClassNumCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {

        /* ① ログイン確認 */
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) {
            response.sendRedirect("/teamE/scoremanager/login.jsp");
            return;
        }

        /* ② パラメータ取得 */
        String classNumStr = request.getParameter("classNum");

        /* ③ バリデーション */
        boolean hasErr = false;
        ClassNumDao dao = new ClassNumDao();

        if (classNumStr == null || classNumStr.trim().isEmpty()) {
            request.setAttribute("errorClassNum", "クラス番号を入力してください");
            hasErr = true;

        // ★ 追加：半角数字のみ許可
        } else if (!classNumStr.matches("\\d+")) {
            request.setAttribute("errorClassNum", "クラス番号は半角数字のみで入力してください");
            hasErr = true;

        } else if (dao.get(classNumStr, teacher.getSchoolCd()) != null) {
            request.setAttribute("errorClassNum", "そのクラス番号は既に存在します");
            hasErr = true;
        }

        if (hasErr) {
            /* 入力済み値を保持して画面へ戻す */
            ClassNum backup = new ClassNum();
            backup.setClassNum(classNumStr);
            backup.setSchoolCd(teacher.getSchoolCd());
            request.setAttribute("classNum", backup);

            // 直接 JSP へフォワード（Action 経由ではなく）
            request.getRequestDispatcher("classnum_create.jsp")
                   .forward(request, response);
            return;
        }

        /* ④ 追加処理 */
        ClassNum classNum = new ClassNum();
        classNum.setClassNum(classNumStr);
        classNum.setSchoolCd(teacher.getSchoolCd());
        dao.insert(classNum);

        /* ⑤ 完了画面へ */
        request.getRequestDispatcher("classnum_create_done.jsp")
               .forward(request, response);
    }
}

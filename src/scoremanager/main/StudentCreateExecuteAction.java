package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

/**
 * 学生新規登録 実行アクション
 * 画面ID : STDM002 → STDM003
 */
public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {

        /* ===== ① ログインチェック ===== */
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) {
            // 未ログイン → ログイン画面へ
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        /* ===== ② パラメータ取得 ===== */
        String no         = request.getParameter("no");
        String name       = request.getParameter("name");
        String entYearStr = request.getParameter("entYear");
        String classNum   = request.getParameter("classNum");
        boolean isAttend  = "true".equals(request.getParameter("isAttend"));

        int  entYear  = 0;
        boolean hasError = false;

        /* =========================================================
         * ③ 入力バリデーション
         * ======================================================= */

        // ★ 学籍番号：必須 & 半角数字のみ
        if (no == null || no.isEmpty()) {
            request.setAttribute("errorNo", "学籍番号を入力してください");
            hasError = true;
        } else if (!no.matches("\\d+")) {            // ← 数字以外を弾く
            request.setAttribute("errorNo", "学籍番号は半角数字のみで入力してください");
            hasError = true;
        } else if (no.length() > 10) {
            request.setAttribute("errorNo", "学籍番号は10文字以内で入力してください");
            hasError = true;
        }

        // 入学年度チェック（必須 & 数値）
        if (entYearStr == null || entYearStr.isEmpty()) {
            request.setAttribute("errorEntYear", "入学年度を選択してください");
            hasError = true;
        } else {
            try {
                entYear = Integer.parseInt(entYearStr);
                if (entYear == 0) {
                    request.setAttribute("errorEntYear", "入学年度を選択してください");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorEntYear", "入学年度が不正です");
                hasError = true;
            }
        }

        // 学生番号の重複チェック（★ 数字判定に通った後で実施）
        StudentDao dao = new StudentDao();
        if (!hasError && dao.get(no) != null) {
            request.setAttribute("errorNo", "学籍番号は既に登録されています");
            hasError = true;
        }


        // ★ 氏名の長さチェック
        if (name != null && name.length() > 30) {
            request.setAttribute("errorName", "氏名は30文字以内で入力してください");
            hasError = true;
        }
/* ===== ④ エラー時は登録画面へ戻す ===== */
        if (hasError) {
            // 入力済み値を保持
            Student backup = new Student();
            backup.setNo(no);
            backup.setName(name);
            backup.setEntYear(entYear);
            backup.setClassNum(classNum);
            backup.setAttend(isAttend);
            backup.setSchoolCd(teacher.getSchoolCd());
            request.setAttribute("student", backup);

            // リスト再生成のため StudentCreateAction にフォワード
            request.getRequestDispatcher("/StudentCreate.action")
                   .forward(request, response);
            return;
        }

        /* ===== ⑤ 登録処理 ===== */
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchoolCd(teacher.getSchoolCd());

        dao.save(student);

        /* ===== ⑥ 完了画面へ遷移 ===== */
        request.setAttribute("message", "学生の登録が完了しました");
        request.getRequestDispatcher("/scoremanager/main/student_create_done.jsp")
               .forward(request, response);
    }
}

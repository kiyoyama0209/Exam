package scoremanager.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
        	HttpSession session = req.getSession();
            Teacher teacher = (Teacher) session.getAttribute("user");

            String schoolCd = teacher.getSchoolCd();

         // フィルタパラメータ
            String entYearStr = req.getParameter("f1");

            Integer ent_Year = null;

            // 入学年度
            if (entYearStr != null && !entYearStr.isEmpty()) {
                try {
                    ent_Year = Integer.parseInt(entYearStr);
                } catch (NumberFormatException e) {
                    ent_Year = null;
                }
            }
         // 年度プルダウン（今年±10年）
            List<Integer> years = new ArrayList<>();
            int currentYear = java.time.Year.now().getValue();
            for (int i = -10; i <= 1; i++) {
                years.add(currentYear + i);
            }
            req.setAttribute("years", years);

            // クラス番号プルダウン
            ClassNumDao classNumDao = new ClassNumDao();
            List<ClassNum> classNums = classNumDao.filter(schoolCd);
            req.setAttribute("classNums", classNums);

         // ★ 科目プルダウン
            SubjectDao subjectDao = new SubjectDao();
            List<Subject> subjects = subjectDao.filter(schoolCd); // schoolCdでフィルタ
            req.setAttribute("subjects", subjects);



            // パラメータの取得
            int entYear = Integer.parseInt(req.getParameter("entYear"));
            String classNum = req.getParameter("classNum");
            String subjectCd = req.getParameter("subjectCd");

            Subject selSubject = subjectDao.get(subjectCd);         // ←★ここを追加
            req.setAttribute("selSubject", selSubject);


            // Subject, School オブジェクトの生成
            Subject subject = new Subject();
            subject.setCode(subjectCd);

            School school = new School();
            school.setSchoolCd(schoolCd);

            // DAO から成績一覧取得
            TestListSubjectDao dao = new TestListSubjectDao();
            List<TestListSubject> list = dao.filter(entYear, classNum, subject, school);



            // 取得結果をリクエストに格納
            req.setAttribute("testListSubject", list);

            // JSP へフォワード
            RequestDispatcher dispatcher = req.getRequestDispatcher("test_list_subject.jsp");
            dispatcher.forward(req, res);

        } catch (Exception e) {
            // 重複エラー等の例外時は入力画面へ戻す
            req.setAttribute("errorNo", "入学年度とクラスと科目を選択してください");
            req.getRequestDispatcher("test_list.jsp")
                   .forward(req, res);
        }

    }

}
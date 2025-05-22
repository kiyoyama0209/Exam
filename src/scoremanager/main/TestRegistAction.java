package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

/**
 * 成績登録 画面表示 (TSTR001)
 * URL : /TestRegist.action
 */
public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	    // パラメータ取得
	    String entYearStr = req.getParameter("f1");
	    String classNum   = req.getParameter("f2");
	    String subjectCd  = req.getParameter("f3");
	    String noStr      = req.getParameter("f4");

	    // チェック：どれか1つでも未入力ならエラー
	    if (entYearStr == null || entYearStr.isEmpty() ||
	        classNum == null   || classNum.isEmpty()   ||
	        subjectCd == null  || subjectCd.isEmpty()  ||
	        noStr == null      || noStr.isEmpty()) {

	        req.setAttribute("error", "入学年度とクラスと科目と回数を選択してください");

	        // プルダウン用データを再設定（TestRegistAction と同様）
	        HttpSession ses = req.getSession();
	        Teacher teacher = (Teacher) ses.getAttribute("user");
	        String schoolCd = teacher.getSchoolCd();

	        List<Integer> years = new ArrayList<>();
	        int now = java.time.Year.now().getValue();
	        for (int i = -10; i <= 1; i++) years.add(now + i);

	        ClassNumDao cDao = new ClassNumDao();
	        SubjectDao sDao = new SubjectDao();
	        List<ClassNum> classNums = cDao.filter(schoolCd);
	        List<Subject> subjects = sDao.filter(schoolCd);

	        req.setAttribute("years", years);
	        req.setAttribute("classNums", classNums);
	        req.setAttribute("subjects", subjects);

	        req.getRequestDispatcher("test_regist.jsp")
	           .forward(req, res);
	        return;
	    }
	}
}

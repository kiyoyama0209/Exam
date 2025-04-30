package tool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import scoremanager.LoginAction;
import scoremanager.LoginExecuteAction;
import scoremanager.LogoutAction;
import scoremanager.main.ClassNumCreateAction;
import scoremanager.main.ClassNumCreateExecuteAction;
import scoremanager.main.ClassNumListAction;
import scoremanager.main.ClassNumUpdateAction;
import scoremanager.main.ClassNumUpdateExecuteAction;
import scoremanager.main.MenuAction;
import scoremanager.main.StudentCreateAction;
import scoremanager.main.StudentCreateExecuteAction;
import scoremanager.main.StudentListAction;
import scoremanager.main.StudentUpdateAction;
import scoremanager.main.StudentUpdateExecuteAction;
import scoremanager.main.SubjectCreateAction;
import scoremanager.main.SubjectCreateExecuteAction;
import scoremanager.main.SubjectDeleteAction;
import scoremanager.main.SubjectDeleteExecuteAction;
import scoremanager.main.SubjectListAction;
import scoremanager.main.SubjectUpdateAction;
import scoremanager.main.SubjectUpdateExecuteAction;
import scoremanager.main.TestListAction;

@WebServlet("*.action")
public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Map<String, Action> actionMap = new HashMap<>();

    static {
        // パスとクラスを紐づけ
        actionMap.put("/StudentList.action", new StudentListAction());
        actionMap.put("/Login.action", new LoginAction());
        actionMap.put("/LoginExecute.action", new LoginExecuteAction());
        actionMap.put("/StudentCreate.action", new StudentCreateAction());
        actionMap.put("/StudentCreateExecute.action", new StudentCreateExecuteAction());
        actionMap.put("/Logout.action", new LogoutAction());
        actionMap.put("/Menu.action",          new MenuAction());
        actionMap.put("/StudentUpdate.action", new StudentUpdateAction());
        actionMap.put("/StudentUpdateExecute.action", new StudentUpdateExecuteAction());
        actionMap.put("/SubjectList.action", new SubjectListAction());
        actionMap.put("/SubjectCreate.action", new SubjectCreateAction());
        actionMap.put("/SubjectCreateExecute.action", new SubjectCreateExecuteAction());
        actionMap.put("/SubjectDelete.action", new SubjectDeleteAction());
        actionMap.put("/SubjectDeleteExecute.action", new SubjectDeleteExecuteAction());
        actionMap.put("/SubjectUpdate.action", new SubjectUpdateAction());
        actionMap.put("/SubjectUpdateExecute.action", new SubjectUpdateExecuteAction());
        actionMap.put("/ClassNumList.action", new ClassNumListAction());
        actionMap.put("/ClassNumCreate.action", new ClassNumCreateAction());
        actionMap.put("/ClassNumUpdate.action",          new ClassNumUpdateAction());
        actionMap.put("/ClassNumUpdateExecute.action",   new ClassNumUpdateExecuteAction());
        actionMap.put("/ClassNumCreateExecute.action", new ClassNumCreateExecuteAction());
        actionMap.put("/TestList.action", new TestListAction());
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath(); // 例: "/StudentList.action"

        Action action = actionMap.get(path);
        if (action == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            action.execute(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}

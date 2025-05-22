//フロントコントローラーは一か所でアノテーションをする
//URLを指定する指示役

//①全ての行き先を一旦引き受ける
//②受け取ったURLをパッケージ名.クラス名に変換
//③各処理を呼び出し,渡すURLを受け取る
//④受け取ったURLへフォワード
package tool;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//jspに記述するURLは○○.action
@WebServlet(urlPatterns={"*.action"})
public class FrontController extends HttpServlet{

	public void doPost(
			HttpServletRequest request,HttpServletResponse response
		)throws ServletException, IOException{
		PrintWriter out=response.getWriter();
		try{
			//jsp側で指定されたURL()を受け取る
			String path=request.getServletPath().substring(1);

			//受け取ったURLを.a→A /→.に置き換え
			//受け取ったURL ch23/Serch.action : フォルダ名/ファイル名
			//変換後のURL ch23.SerchAction : パッケージ名.クラス名
			String name=path.replace(".a", "A").replace('/', '.');

			//変換したクラス名を使用してインスタンス化
			Action action=(Action)Class.forName(name).
					getDeclaredConstructor().newInstance();

			action.execute(request, response);
		}catch (Exception e){
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
		response.getWriter().append("Servlet at:").append(request.getContextPath());
	}

	public void doGet(
			HttpServletRequest request,HttpServletResponse response
			)throws ServletException, IOException{
			doPost(request, response);
	}
}

package tool;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
// ユーザ (クライアント) からサーバへのリクエスト
import javax.servlet.ServletRequest;
// サーバからユーザへのレスポンス
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

// アノテーション:URL指定 * :全て (共通部分)
@WebFilter(urlPatterns={"/*"})
// Filter(インターフェース):抽象クラスのようなもの
public class EncodingFilter implements Filter {


	public void doFilter(
		ServletRequest request, ServletResponse response,
		FilterChain chain
	)throws IOException,ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("フィルタの前処理");
		// ↑↑サーブレットやJSPを呼び出す"前"の処理
		chain.doFilter(request, response);
		// ↓↓サーブレットやJSPを呼び出す"後"の処理

		// 後処理 開始
		System.out.println("フィルタの後処理");
		// 後処理 終了
	}
		public void init(FilterConfig filterConfig) {}
		public void destroy(){}
}

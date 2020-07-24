package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 銘柄登録画面を開く前に処理を行うサーブレット
 * Servlet implementation class Newcontroller
 */
@WebServlet("/new")
public class Newcontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @see HttpServlet#HttpServlet()
     */
    public Newcontroller() {
        super();
    }

	/**
	 * ログイン情報がセッションスコープに格納されていることを確認したのち、
	 * new.jspへ遷移する
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); //文字コード系
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession(); //ログインチェック
		String login_user_id = (String)session.getAttribute("ログインユーザーID");
		if(login_user_id == null) {
			response.sendRedirect("index");
			return;
		}

		RequestDispatcher rd = request.getRequestDispatcher("new.jsp");
		rd.forward(request, response); //フォワード
	}

	/**
	 * 通常、使用されないメソッド
	 * doGetを実行する
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

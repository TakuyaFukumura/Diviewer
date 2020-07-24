package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PossessionDao;

/**
 * ティッカー編集画面を表示する前の処理を行うサーブレット
 */
@WebServlet("/edit")
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditController() {
        super();
    }

	/**
	 * ログイン画面に飛ばす
	 * 通常、使用されことはない
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index"); //通常操作でGETされることはない
	}

	/**
	 * 編集ボタンが押された時
	 * ティッカーシンボル情報からデータを取得してして編集ページを開く
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); //文字コード系
		response.setContentType("text/html; charset=UTF-8");

		//ユーザー情報受け取り（ログインチェックを兼ねる予定）
		HttpSession session = request.getSession();
		String login_user_id = (String)session.getAttribute("ログインユーザーID");
		if(login_user_id == null) {
			response.sendRedirect("index");
			return;
		}

		//パラメータ受け取り
		String ticker_symbol = request.getParameter("ticker_symbol");

		//所持データ内から該当データゲット&データセット&ページ移動
		request.setAttribute("詳細データ", new PossessionDao().getPossessionBySymbolId(ticker_symbol, login_user_id));
		RequestDispatcher rd = request.getRequestDispatcher("edit.jsp");
		rd.forward(request, response); //フォワード
	}

}

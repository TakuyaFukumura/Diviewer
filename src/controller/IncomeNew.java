package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.DividendIncomeDto;

/**
 * 配当の登録画面へ遷移するサーブレット
 */
@WebServlet("/incomeNew")
public class IncomeNew extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @see HttpServlet#HttpServlet()
	 */
	public IncomeNew() {
		super();
	}

	/**
	 * ティッカーIDを含むDividendIncomeDtoを用意したうえで、
	 * incomeNew.jspへ遷移する
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); //文字コード系
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession(); //ログインチェック
		String login_user_id = (String) session.getAttribute("ログインユーザーID");
		if (login_user_id == null) {
			response.sendRedirect("index");
			return;
		}

		DividendIncomeDto dividendIncomeDto = new DividendIncomeDto();
		dividendIncomeDto.setTicker_id(Integer.parseInt(request.getParameter("ticker_id")));
		request.setAttribute("新規配当データ", dividendIncomeDto);

		RequestDispatcher rd = request.getRequestDispatcher("incomeNew.jsp");
		rd.forward(request, response); //フォワード
	}

	/**
	 * 使用しないメソッド
	 * GETを呼び出す
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

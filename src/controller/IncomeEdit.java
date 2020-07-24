package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DividendIncomeDao;
import dto.DividendIncomeDto;

/**
 * 配当情報の編集画面に遷移する前の処理
 */
@WebServlet("/incomeEdit")
public class IncomeEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @see HttpServlet#HttpServlet()
     */
    public IncomeEdit() {
        super();
    }

	/**
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); //文字コード系
		response.setContentType("text/html; charset=UTF-8");

		//ユーザー情報受け取り（ログインチェックを兼ねる）
		HttpSession session = request.getSession();
		String login_user_id = (String)session.getAttribute("ログインユーザーID");
		if(login_user_id == null) {
			response.sendRedirect("index");
			return;
		}

		//パラメータ取得
		int dividend_income_id = Integer.parseInt(request.getParameter("dividend_income_id"));

		//インカムIDをもとにデータ取得
		DividendIncomeDto dividendIncomeDto = new DividendIncomeDao().getDataById(dividend_income_id);

		request.setAttribute("配当データ", dividendIncomeDto);
		RequestDispatcher rd = request.getRequestDispatcher("incomeEdit.jsp");
		rd.forward(request, response); //フォワード
	}

	/**
	 * POST時の処理
	 * doGetメソッドを呼び出す。
	 * 通常使用される予定はない
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

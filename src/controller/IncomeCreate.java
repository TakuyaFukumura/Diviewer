package controller;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DividendIncomeDao;
import dao.TickerDao;
import dto.TickerDto;

/**
 * 配当や分配金の登録処理を呼び出すサーブレット
 */
@WebServlet("/incomeCreate")
public class IncomeCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @see HttpServlet#HttpServlet()
     */
    public IncomeCreate() {
        super();
    }

	/**
	 * 使用しないメソッド
	 * GETされたらログインページにリダイレクトする
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 配当テーブルに情報を追加し、
	 * 詳細画面に遷移する
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		int ticker_id = Integer.parseInt(request.getParameter("ticker_id"));
		BigDecimal aftertax_income = new BigDecimal(request.getParameter("aftertax_income"));
		String receipt_date = request.getParameter("receipt_date"); //2020-06-17

		//インサート処理
		if(new DividendIncomeDao().insert(login_user_id, ticker_id,
				receipt_date, aftertax_income)) { //実行
			//成功
		}else {
			//失敗
		}

		//ティッカーシンボル取得
		TickerDto tickerDto = new TickerDao().getTickerSymbolById(ticker_id);

		//GETでshowに移動、ティッカーシンボルが必要
		response.sendRedirect("show?ticker_symbol=" + tickerDto.getTicker_symbol());
	}

}

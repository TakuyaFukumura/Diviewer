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
import dto.DividendIncomeDto;
import dto.TickerDto;

/**
 * 配当の更新と削除処理を行うサーブレット
 * 処理後に銘柄詳細画面へ遷移する
 */
@WebServlet("/incomeOperation")
public class IncomeOperation extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @see HttpServlet#HttpServlet()
     */
    public IncomeOperation() {
        super();
    }

	/**
	 * GET時はログインページへリダイレクトする
	 * 通常操作では使用されることの無いメソッド
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}

	/**
	 * リクエストに応じて配当情報の削除か更新を行う
	 * その後、銘柄詳細ページへ遷移する
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
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
		int dividend_income_id = Integer.parseInt(request.getParameter("dividend_income_id()"));
		BigDecimal aftertax_income = new BigDecimal(request.getParameter("aftertax_income"));
		String receipt_date = request.getParameter("receipt_date");//2020-06-16
		DividendIncomeDto dividendIncomeDto = new DividendIncomeDao().getDataById(dividend_income_id);

		if(request.getParameter("incomeDelete") != null) { //削除処理の場合
			if(new DividendIncomeDao().delete( dividend_income_id )) { //削除処理実行
				//成功
			}else {
				//失敗 エラーページへ飛ばすorエラーメッセージセットすべき
			}
		}else {
			//更新処理
			if(new DividendIncomeDao().update(dividend_income_id, aftertax_income, receipt_date)) {
				//成功
			}else {
				//失敗 エラーページへ飛ばすorエラーメッセージセットすべき
			}
		}

		//ティッカーシンボル取得
		TickerDto tickerDto = new TickerDao().getTickerSymbolById(dividendIncomeDto.getTicker_id());

		//GETでshowに移動、ティッカーシンボルが必要
		response.sendRedirect("show?ticker_symbol=" + tickerDto.getTicker_symbol());


	}

}

package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PossessionDao;
import dao.TickerDao;
import dto.PossessionDto;

/**
 * ティッカー情報と所持情報の登録処理を呼び出すサーブレット
 * 状態に応じて3種類のページに遷移する(index, new.jsp, show)
 * Servlet implementation class CreateController
 */
@WebServlet("/create")
public class CreateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * コンストラクタ
     * @see HttpServlet#HttpServlet()
     */
    public CreateController() {
        super();
    }

	/**
	 * GET時の処理、ログインページへ遷移させる
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}

	/**
	 * POST時の処理
	 * ログイン情報がなければログインページへ遷移する
	 * すでに所有している銘柄で有れば銘柄登録ページへ戻る
	 * 未登録銘柄で有ればテーブルに追加する
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); //文字コード系
		response.setContentType("text/html; charset=UTF-8");

		//ログイン情報受け取り＆ログインチェック
		HttpSession session = request.getSession();
		String login_user_id = (String)session.getAttribute("ログインユーザーID");
		if(login_user_id == null) {
			response.sendRedirect("index");
			return;
		}

		//パラメータ受け取り
		String ticker_symbol = request.getParameter("ticker_symbol");
		BigDecimal unit = new BigDecimal(request.getParameter("unit"));
		BigDecimal average_unit_cost = new BigDecimal(request.getParameter("average_unit_cost"));

		//ユーザーの所有するティッカーデータを検索して既に存在していたら元のページに戻る
		if(new PossessionDao().getPossessionBySymbolId(ticker_symbol, login_user_id) != null) {
			PossessionDto possessionDto = new PossessionDto();
			possessionDto.setTicker_symbol(ticker_symbol);
			possessionDto.setUnit(unit);
			possessionDto.setAverage_unit_cost(average_unit_cost); //データセット
			request.setAttribute("バックデータ", possessionDto);
			RequestDispatcher rd = request.getRequestDispatcher("new.jsp");
			rd.forward(request, response); //フォワード
			return ;
		}

		//ティッカー検索して存在しないことが確認された時
		if(new TickerDao().getTickerldBySymbol(ticker_symbol) == null) {
			if(new TickerDao().insert(ticker_symbol)) { //ティッカー新規登録処理
				//登録成功
			}else {
				//登録失敗
			}
		}

		//ティッカーIDを取得して
		int ticker_id = new TickerDao().getTickerldBySymbol(ticker_symbol).getTicker_id();

		//所持データ登録（必要情報：ユーザID、ティッカーID、口数、取得単価のみ、日付はsysdate）
		if(new PossessionDao().insert(login_user_id, ticker_id, unit, average_unit_cost)) {//登録成功するかどうか
			PossessionDto possessionDto = new PossessionDto(login_user_id, ticker_id,
					ticker_symbol, unit, average_unit_cost, new Date(), new Date());
			request.setAttribute("詳細データ", possessionDto);//showに所持データ送る（インスタンス）
			RequestDispatcher rd = request.getRequestDispatcher("show");
			rd.forward(request, response); //フォワード
		}else {
			//登録失敗
		}
	}
}

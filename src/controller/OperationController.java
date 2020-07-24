package controller;

import java.io.IOException;
import java.math.BigDecimal;

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
import model.Model;

/**
 * ティッカー編集画面から開かれるサーブレット
 * 削除と更新処理を行う
 */
@WebServlet("/operation")
public class OperationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @see HttpServlet#HttpServlet()
     */
    public OperationController() {
        super();
    }

	/**
	 * GETは通常使用されない
	 * ログインページへリダイレクトする
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}

	/**
	 * リクエストに応じて更新か削除を行う
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

		//入力データ受け取り（インスタンス）
		int old_ticker_id = Integer.parseInt(request.getParameter("old_ticker_id"));
		String ticker_symbol = request.getParameter("new_ticker_symbol");
		String old_ticker_symbol = request.getParameter("old_ticker_symbol");
		BigDecimal unit = new BigDecimal(request.getParameter("unit"));
		BigDecimal average_unit_cost = new BigDecimal(request.getParameter("average_unit_cost"));
		String created_at = request.getParameter("created_at");//2020-06-16

		if(request.getParameter("delete") != null) { //削除処理の場合
			if(new Model().deleteTicker(login_user_id, old_ticker_symbol, old_ticker_id)) { //処理実行
				//配当情報の削除も行う
				response.sendRedirect("top");
				return; //削除成功時TOPページへリダイレクト
			}else {
				//削除失敗
			}
		}else if(request.getParameter("update") != null){ //更新処理の場合
			//新しい銘柄なら登録する(ティッカー検索して存在しないことを確認する)
			if(new TickerDao().getTickerldBySymbol(ticker_symbol) == null) {
				if(new TickerDao().insert(ticker_symbol)) { //ティッカー新規登録処理実行
					//登録成功
				}else {
					//登録失敗
				}
			}
			//既に所有している銘柄なら元のページに返却する（ユーザーの所有するティッカーデータを検索して既に存在しているか確認する）
			if( !old_ticker_symbol.equals(ticker_symbol) && new PossessionDao().getPossessionBySymbolId(ticker_symbol, login_user_id) != null) {
				//所持データ内から該当データゲット
				PossessionDto possessionDto = new PossessionDao().getPossessionBySymbolId(old_ticker_symbol, login_user_id);
				//ベースのデータに今回入力されたデータ上書きセット
				possessionDto.setTicker_symbol(ticker_symbol + "は既存銘柄です");
				possessionDto.setUnit(unit);
				possessionDto.setAverage_unit_cost(average_unit_cost);
				request.setAttribute("詳細データ", possessionDto);
				RequestDispatcher rd = request.getRequestDispatcher("edit.jsp");
				rd.forward(request, response); //フォワード
				return ;
			}

			//更新処理
			if(new PossessionDao().update(login_user_id, old_ticker_id, ticker_symbol,
					unit, average_unit_cost, created_at)) {
				//更新成功
			}else {
				//更新失敗
			}
			//更新後のデータ取得
			PossessionDto possessionDto = new PossessionDao().getPossessionBySymbolId(ticker_symbol, login_user_id);
			request.setAttribute("詳細データ", possessionDto); //詳細画面へ飛ぶ
			RequestDispatcher rd = request.getRequestDispatcher("show");
			rd.forward(request, response); //フォワード
		}
	}

}

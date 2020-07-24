package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DividendIncomeDao;
import dao.PossessionDao;
import dto.DividendIncomeDto;
import dto.PossessionDto;

/**
 * ティッカー保有情報詳細閲覧画面を開く前処理
 */
@WebServlet("/show")
public class ShowController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @see HttpServlet#HttpServlet()
     */
    public ShowController() {
        super();
    }

	/**
	 * 一覧画面でティッカーシンボルがクリックされた時の処理
	 * 銘柄の所持情報と配当情報をスコープに入れた後、
	 * show.jspへ遷移する
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); //文字コード系
		response.setContentType("text/html; charset=UTF-8");

		//ユーザー情報受け取り（ログインチェックを兼ねる）
		HttpSession session = request.getSession();
		String login_user_id = (String)session.getAttribute("ログインユーザーID");
		String ticker_symbol = request.getParameter("ticker_symbol");
		if(login_user_id == null || ticker_symbol == null) {
			response.sendRedirect("index");
			return;
		}

		PossessionDto possessionDto = new PossessionDao().getPossessionBySymbolId(ticker_symbol, login_user_id);

		//ユーザ情報とティッカーシンボルで個別所持情報ゲットしてセット
		request.setAttribute("詳細データ", possessionDto );

		//配当リスト取得
		List<DividendIncomeDto> incomeList = new DividendIncomeDao().findIncomList(login_user_id, possessionDto.getTicker_id());
		request.setAttribute("配当情報", incomeList);

		RequestDispatcher rd = request.getRequestDispatcher("show.jsp");
		rd.forward(request, response); //フォワード
	}

	/**
	 * 新規作成や更新後の表示に使う
	 * 銘柄の所持情報と配当情報をスコープに入れた後、
	 * show.jspへ遷移する
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

		//ユーザ情報とティッカーシンボルで個別所持情報ゲットしてセット
		PossessionDto possessionDto = (PossessionDto)request.getAttribute("詳細データ");

		//配当リスト取得
		List<DividendIncomeDto> incomeList = new DividendIncomeDao().findIncomList(login_user_id, possessionDto.getTicker_id());

		request.setAttribute("配当情報", incomeList);
		request.setAttribute("詳細データ", possessionDto);
		RequestDispatcher rd = request.getRequestDispatcher("show.jsp");
		rd.forward(request, response); //フォワード
	}

}

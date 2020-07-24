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

import dao.PossessionDao;
import dto.PossessionDto;

/**
 * 検索処理を行いlist.jspを開くサーブレット
 */
@WebServlet("/search")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @see HttpServlet#HttpServlet()
     */
    public SearchController() {
        super();
    }

	/**
	 * 通常使用されないメソッド
	 * GETされたらログインページへ飛ばす
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}

	/**
	 * 検索キーワードを使用して
	 * ユーザの所持情報をリストで取得した後、
	 * list.jspへ遷移する
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); //文字コード系
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();//ログイン情報取得
		String login_user_id = (String)session.getAttribute("ログインユーザーID");

		String ticker_symbol = request.getParameter("ticker_symbol");

		//所持データから曖昧検索してリストを取得
		List<PossessionDto> possessionList = new PossessionDao().searchPossession(login_user_id, ticker_symbol);

		request.setAttribute("検索結果", possessionList); //配当DTO
		RequestDispatcher rd = request.getRequestDispatcher("list.jsp");
		rd.forward(request, response); //フォワード
	}

}

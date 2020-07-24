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
import dto.DividendIncomeDto;
import model.Model;

/**
 * トップ画面を表示する前処理を行うサーブレット
 */
@WebServlet("/top")
public class TopController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     */
    public TopController() {
        super();
    }

	/**
	 * 新着所持データリストとグラフデータをセットした後、
	 * top.jspへ遷移する
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); //文字コード系
		response.setContentType("text/html; charset=UTF-8");

		//ログイン情報ゲット＆チェック
		HttpSession session = request.getSession();
		String login_user_id = (String)session.getAttribute("ログインユーザーID");
		if(login_user_id == null) {
			response.sendRedirect("index");
			return;
		}

		//新着所持データ取得 セット欄に直接入れてもいいが分かりづらくなりそう
		List<DividendIncomeDto> incomeList = new DividendIncomeDao().findNewIncom(login_user_id);

		Model md = new Model(); //ここの処理をモデルに移したい
		String incomeData2019 = md.getCartDataString(md.getSumIncomeList( login_user_id, 2019 ));
		String incomeData2020 = md.getCartDataString(md.getSumIncomeList( login_user_id, 2020 ));
		String[] incomeData = {incomeData2019,incomeData2020};
		request.setAttribute("年別月毎配当金額", incomeData );

		request.setAttribute("新着配当データ", incomeList);
		RequestDispatcher rd = request.getRequestDispatcher("top.jsp");
		rd.forward(request, response);
	}

	/**
	 * ログイン画面から入力された情報を受け取る
	 * DB情報と入力情報を照合して遷移先が決まる
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); //文字コード系
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Model md = new Model();//メソッド使うために呼び出している

		//パラメータ受取＆チェック
		String user_id = request.getParameter("loginUserId");
		String user_pass = request.getParameter("loginPassword");
		if( user_id == null) { //ログインチェック
			response.sendRedirect("index");
			return;
		}

		//受取データをDBと照合してログイン画面かトップページのURLをセット
		String url = md.decideUrl(user_id, user_pass);

		List<DividendIncomeDto> incomeList = new DividendIncomeDao().findNewIncom(user_id);
		request.setAttribute("新着配当データ", incomeList);

		String incomeData2019 = md.getCartDataString(md.getSumIncomeList( user_id, 2019 ));
		String incomeData2020 = md.getCartDataString(md.getSumIncomeList( user_id, 2020 ));
		String[] incomeData = {incomeData2019,incomeData2020};
		request.setAttribute("年別月毎配当金額", incomeData );

		session.setAttribute("ログインユーザーID", user_id);//ログイン情報をセッションスコープに保存
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response); //フォワード
	}
}

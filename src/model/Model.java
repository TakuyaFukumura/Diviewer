/**
 *
 */
package model;

import dao.DividendIncomeDao;
import dao.PossessionDao;
import dao.UserDao;
import dto.UserDto;

/**
 * @author bx0045
 * 様々な処理を含むクラス
 */
public class Model {

	/**
	 * ユーザーの保有する指定されたティッカー保有情報を削除する。
	 * また、それに関連した配当情報も削除する
	 * @param login_user_id ユーザID
	 * @param old_ticker_symbol ティッカーシンボル
	 * @param ticker_id ティッカーID
	 * @return 削除成功true 削除失敗false
	 */
	public boolean deleteTicker(String login_user_id, String old_ticker_symbol, int ticker_id) {
		boolean flag =false;
		new DividendIncomeDao().deleteWithSymbol(login_user_id, ticker_id);
		flag = new PossessionDao().delete(old_ticker_symbol,login_user_id);
		return flag;
	}

	/**
	 * 指定した年の月毎配当合計額をString配列で取得
	 * @param user_id ユーザID
	 * @param year 取得したい年（西暦）
	 * @return String[]月毎の配当額
	 */
	public String[] getSumIncomeList(String user_id, int year) {
		String[] sumList = new String[12];
		DividendIncomeDao did = new DividendIncomeDao();
		int[] month = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		for(int i = 0; i < month.length; i++) {
			sumList[i] = did.getSumIncomeByIdAndMonth( user_id, year, month[i] ).toString();
		}
		return sumList;
	}

	/**
	 * 文字列の配列を一つの文字列に合成する。
	 * なお、合成する際には各要素の間に「,」を入れる
	 * （グラフ描画用に用いる）
	 * 例： " 0.7, 2.2, 4.0, ..... 9.87"
	 * 引数がnullの場合は「0,0,0,0,0,0,0,0,0,0,0,0」を返す
	 * @param getSumIncomeList 月毎配当額情報 文字列配列型
	 * @return Stringグラフ描画用文字列
	 */
	public String getCartDataString(String[] getSumIncomeList) {
		String result = "";
		if(getSumIncomeList != null) {
			result += getSumIncomeList[0];
			for(int i = 1; i < getSumIncomeList.length; i++) {
				result += ",";
				result += getSumIncomeList[i];
			}
		}else {
			result = "0,0,0,0,0,0,0,0,0,0,0,0";
		}
		return result;
	}

	/**
	 * ログインできたかどうかを判断する
	 * @param user_id ユーザID
	 * @param user_pass ユーザーパスワード
	 * @return 成功true 失敗false
	 */
	public boolean loginCheck(String user_id, String user_pass) {
		boolean flag = false;
		UserDto userDto = new UserDao().getUserById(user_id);
		if(userDto != null) {
			if(user_pass.equals(userDto.getUser_pass())) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * ログイン状態に応じてurlを返す
	 * @param user_id ユーザID
	 * @param user_pass パスワード
	 * @return ログインしていればtop.jsp そうでなければindex
	 */
	public String decideUrl(String user_id, String user_pass) {
		String url = "index";
		if(loginCheck(user_id, user_pass)) {
			url = "top.jsp";
		}
		return url;
	}


}

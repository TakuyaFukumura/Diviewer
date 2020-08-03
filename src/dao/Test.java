/**
 *
 */
package dao;

import java.math.BigDecimal;
import java.util.List;

import dto.DividendIncomeDto;
import dto.PossessionDto;
import dto.TickerDto;
import dto.UserDto;
import model.Model;

/**
 * @author bx0045
 * mainメソッドを使用して開発中のDAOの動作を見る
 */
public class Test {

	/**
	 * @param args javaコマンド実行時に渡されるパラメーターが入っています。
	 */
	public static void main(String[] args) {
//		findAllUser();
//		System.out.println("---------------------------------");
//		getUserById();
//		System.out.println("---------------------------------");
//		findNewIncom();
//		System.out.println("---------------------------------");
//		getPossessionBySymbolId();
//		System.out.println("---------------------------------");
//		getTickerldBySymbol();
//		insert();
//		insertTicker();
//		deletePossession();
//		updatePossession();
//		searchPossession();
//		findIncomList();
//		insertDividendIncome();
//		getDataById();
//		deleteDividendIncome();
//		updateDividendIncome();
//		getSumIncomeByIdAndMonth();
//		sumList();
//		getCartDataString();
//		getUserById2();
		getTickerAll();
//		System.out.println(new CsvModel().outputTickerCSV());
//		getUserAll();
//		System.out.println(new CsvModel().outputUserCSV());
//		getPossessionAll();
//		System.out.println(new CsvModel().outputPossessionCSV());
//		getDividendIncomeAll();
//		System.out.println(new CsvModel().outputDividendIncomeCSV());
	}

	/**
	 * dividend_income_table全件取得
	 * テスト結果：成功
	 * 実施日付：2020/08/02 22:18
	 */
	public static void getDividendIncomeAll() {
		List<DividendIncomeDto> dividendIncomeList = new DividendIncomeDao().getDividendIncomeAll();
		for (DividendIncomeDto s: dividendIncomeList) {
            System.out.println(s.getUpdate_at());
        }
	}
	/**
	 * Possession_TABLE全件取得
	 * テスト結果：成功
	 * 実施日付：2020/08/02 2:08
	 */
	public static void getPossessionAll() {
		List<PossessionDto> possessionList = new PossessionDao().getPossessionAll();
		for (PossessionDto s: possessionList) {
            System.out.println(s.getUpdate_at());
        }
	}
	/**
	 * USER_TABLE全件取得
	 * テスト結果：成功
	 * 実施日付：2020/08/01 22:50
	 */
	public static void getUserAll() {
		List<UserDto> userList = new UserDao().getUserAll();
		for (UserDto s: userList) {
            System.out.print(s.getUser_id());
            System.out.print(",");
            System.out.print(s.getUser_pass());
            System.out.print(",");
            System.out.print(s.getNickname());
            System.out.print(",");
            System.out.print(s.getCreated_at());
            System.out.print(",");
            System.out.println(s.getUpdate_at());
        }
	}
	/**
	 * TICKER_TABLE全件取得
	 * テスト結果：成功
	 * 実施日付：2020/07/30 23:13
	 */
	public static void getTickerAll() {
		List<TickerDto> tickerList = new TickerDao().getTickerAll();
		for (TickerDto s: tickerList) {
            System.out.print(s.getTicker_id());
            System.out.print(",");
            System.out.println(s.getTicker_symbol());
        }
	}

	/**
	 * 実験用
	 * IDでユーザ情報を取得する
	 * テスト結果：成功
	 * 実施日付：2020/06/19 14:56
	 */
	public static void getUserById2() {
		UserDto ud = new UserDao().getUserById("fukumura");
		System.out.println( ud.getNickname() );
	}


	/**
	 * グラフ描画用文字列生成
	 * テスト結果：成功
	 * 実施日付：2020/06/18 18:10
	 */
	public static void getCartDataString() {

		String[] sumList = new Model().getSumIncomeList( "fukumura", 2020 );
		String rsult = new Model().getCartDataString(sumList);
		System.out.println( rsult );
	}

	/**
	 * 指定年の月別配当受取額データ取得
	 * テスト結果：成功
	 * 実施日付：2020/06/17 18:10
	 */
	public static void sumList() {
		String[] sumList = new Model().getSumIncomeList( "fukumura", 2020 );
		for(int i = 0; i < sumList.length; i++) {
			System.out.println( i+1 + "月" + sumList[i] +"ドル" );
		}
	}


	/**
	 * 配当合計額取得
	 * テスト結果：成功
	 * 実施日付：2020/06/17 18:10
	 */
	public static void getSumIncomeByIdAndMonth() {
		BigDecimal sum = new DividendIncomeDao().getSumIncomeByIdAndMonth( "fukumura", 2020, 1 );
		System.out.println( sum );
	}


	/**
	 * 配当データの更新
	 * テスト結果：成功
	 * 実施日付：2020/06/16 16:35
	 */
	public static void updateDividendIncome() {
		boolean flag = new DividendIncomeDao().update(4, new BigDecimal("0.3"), "2022-09-23");
		System.out.println(flag);
	}

	/**
	 * 配当データの削除
	 * テスト結果：成功
	 * 日付：2020/06/16 16:23
	 */
	public static void deleteDividendIncome() {
		boolean flag = new DividendIncomeDao().delete( 10 );
		System.out.println(flag);
	}


	/**
	 * 配当情報取得
	 * テスト結果：成功
	 * 実施日付：2020/06/17 15:57
	 */
	public static void getDataById() {
		DividendIncomeDto dividendIncomeDto = new DividendIncomeDao().getDataById( 7 );
		System.out.println(dividendIncomeDto.getReceipt_date());
		System.out.println(dividendIncomeDto.getAftertax_income());
	}

	/**
	 * 配当情報登録
	 * テスト結果：成功
	 * 実施日付：2020/06/17 14:14
	 */
	public static void insertDividendIncome() {
		//String user_id, int ticker_id, String receipt_date, BigDecimal aftertax_income
		boolean flag = new DividendIncomeDao().insert("fukumura", 2,
				"2020-01-10", new BigDecimal("13.01"));
		System.out.println(flag);
	}

	/**
	 * 銘柄の配当情報をリスト形式で取得
	 * テスト結果：成功
	 * 実施日付：2020/06/17 11:15
	 */
	public static void findIncomList() {
		DividendIncomeDao dividendIncomeDao = new DividendIncomeDao();
		List<DividendIncomeDto> incomeList = dividendIncomeDao.findIncomList("fukumura", 2);
		for (DividendIncomeDto s: incomeList) {
            System.out.println(s.getDividend_income_id());
            System.out.println(s.getUser_id());
            System.out.println(s.getTicker_id());
            System.out.println(s.getReceipt_date());
            System.out.println(s.getAftertax_income());
            System.out.println(s.getCreated_at());
            System.out.println(s.getUpdate_at());
        }
	}

	/**
	 * 所持データの銘柄検索（曖昧検索対応版）
	 * テスト結果：成功
	 * 実施日付：2020/06/16 17:57
	 */
	public static void searchPossession() {
		List<PossessionDto> possessionList = new PossessionDao().searchPossession("fukumura","M");

		for (PossessionDto s: possessionList) {
            System.out.println(s.getUser_id());
            System.out.println(s.getTicker_id());
            System.out.println(s.getTicker_symbol());
            System.out.println(s.getUnit());
            System.out.println(s.getAverage_unit_cost());
            System.out.println(s.getCreated_at());
            System.out.println(s.getUpdate_at());
            System.out.println("***********************");
        }
	}

	/**
	 * 所持データの更新
	 * テスト結果：成功
	 * 実施日付：2020/06/16 15:30
	 */
	public static void updatePossession() {
		boolean flag = new PossessionDao().update("fukumura", 28, "MAIN",
				new BigDecimal("30"), new BigDecimal("33.07"), "2020-06-16");
		System.out.println(flag);
	}

	/**
	 * 所持データの削除
	 * テスト結果：成功
	 * 日付：2020/06/16 11:59
	 */
	public static void deletePossession() {
		boolean flag = new PossessionDao().delete("BND","fukumura");
		System.out.println(flag);
	}

	/**
	 * ティッカーテーブルへのデータ追加
	 * テスト成功2020/0615/18:42
	 */
	public static void insertTicker() {
		boolean flag = new TickerDao().insert("HDV");
		System.out.println(flag);
	}

	public static void getUserById() {
		UserDto userDto = new UserDao().getUserById("fukumura");

		System.out.println(userDto.getUser_id());
		System.out.println(userDto.getUser_pass());
		System.out.println(userDto.getNickname());
		System.out.println(userDto.getCreated_at());
		System.out.println(userDto.getUpdate_at());
	}

//	public static void findAllUser() {
//		UserDao userDao = new UserDao();
//		List<UserDto> sd = userDao.findAll();
//
//        for (UserDto s: sd) {
//            System.out.println(s.getUser_id());
//            System.out.println(s.getUser_pass());
//            System.out.println(s.getNickname());
//            System.out.println(s.getCreated_at());
//            System.out.println(s.getUpdate_at());
//        }
//	}

	public static void findNewIncom() {
		DividendIncomeDao dividendIncomeDao = new DividendIncomeDao();
		List<DividendIncomeDto> incomeList = dividendIncomeDao.findNewIncom("fukumura");
		for (DividendIncomeDto s: incomeList) {
            //System.out.println(s.getDividend_income_id());
            //System.out.println(s.getUser_id());
            //System.out.println(s.getTicker_id());
            System.out.println(s.getTicker_symbol());
            System.out.println(s.getReceipt_date());
            System.out.println(s.getAftertax_income());
            //System.out.println(s.getCreated_at());
            //System.out.println(s.getUpdate_at());
        }
	}

	/**
	 * ティッカーとユーザIDで検索する
	 */
	public static void getPossessionBySymbolId() {
		PossessionDto possessionDto = new PossessionDao().getPossessionBySymbolId("SPYD", "fukumura");
		System.out.println(possessionDto.getUnit());
	}

	/**
	 * ティッカーシンボルでティッカーIDを取得するテスト：正常系
	 * 成功
	 */
	public static void getTickerldBySymbol() {
		TickerDto tickerDto = new TickerDao().getTickerldBySymbol("SPYD");
		System.out.println(tickerDto.getTicker_id());
	}


	/**
	 * 所持データの登録テスト
	 * 必要情報：ユーザID、ティッカーID、口数、取得単価のみ、日付はsysdate
	 * 成功0615
	 */
	public static void insert() {
		boolean flag = new PossessionDao().insert("fukumura", 13, new BigDecimal("1"), new BigDecimal("100.11"));
		System.out.println(flag);
	}

}

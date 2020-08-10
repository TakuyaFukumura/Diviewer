package test.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dao.TickerDao;
import dto.TickerDto;
import model.CsvModel;

class TickerDaoTest {
	private static CsvModel cm = new CsvModel();
	private TickerDao tickerDao = new TickerDao();
	private static boolean flag = false;

	@BeforeAll
	static void beforeAll() {
		backupAllCSV();
		readeTestCSV();
	}

	/**
	 * テスト前にバックアップ作成
	 * @return 成功true 失敗false
	 */
	static boolean backupAllCSV() {
		flag = cm.writeDividendIncomeCSV("test/dividend_income_table_bk.csv");
		if(flag) flag = cm.writeTickerCSV("test/ticker_table_bk.csv");
		if(flag) flag = cm.writeUserCSV("test/user_table_bk.csv");
		if(flag) flag = cm.writePossessionCSV("test/possession_table_bk.csv");
		return flag;
	}
	/**
	 * テストに使うDBを用意する
	 * @return 成功true 失敗false
	 */
	static boolean readeTestCSV() {
		flag = cm.initializationTable();
		if(flag) flag = cm.readeTickerCSV("test/ticker_table.csv");
		if(flag) flag = cm.readeUserCSV("test/user_table.csv");
		if(flag) flag = cm.readePossessionCSV("test/possession_table.csv");
		if(flag) flag = cm.readeIncomeCSV("test/dividend_income_table.csv");
		return flag;
	}
	/**
	 * バックアップからロードしてDB復元
	 * @return 成功true 失敗false
	 */
	@AfterAll
	static void readeBackupCSV() {
		cm.initializationTable();
		cm.readeTickerCSV("test/ticker_table_bk.csv");
		cm.readeUserCSV("test/user_table_bk.csv");
		cm.readePossessionCSV("test/possession_table_bk.csv");
		cm.readeIncomeCSV("test/dividend_income_table_bk.csv");
	}

	/**
	 * ティッカー情報の入力と削除を行う
	 * ティッカーシンボルAAPLを追加し削除する
	 * 両方が成功した場合はtrueを返す
	 */
	@Test
	void testInsertAndDelete() {
		boolean actual = false;
		if(tickerDao.insert("AAPL") && tickerDao.delete("AAPL") ) {
			actual = true;
		}
		assertTrue(actual);
	}
//	/**
//	 * ティッカー情報の削除を行う
//	 * ドライバ名が不正でDBに接続できない場合
//	 * falseが返ることを確かめる
//	 */
//	@Test
//	void testInsertAndDeleteFalse() {
//		boolean actual = true;
//		TickerDao tickerDao = new TickerDao();
//		tickerDao.CLASSNAME_ORACLE_DRIVER = "test";
//		actual = tickerDao.delete("AAPL");
//		assertFalse(actual);
//	}


	/**
	 * ティッカーシンボルVTを使用して、
	 * ティッカーIDを取得する
	 */
	@Test
	void testGetTickerldBySymbol() {
		int expected = 5;
		int actual =  tickerDao.getTickerldBySymbol("PFFD").getTicker_id();
		assertEquals(expected, actual);
	}
	/**
	 * 存在しないティッカーシンボルZZZを使用して、
	 * nullが返ることを確認する
	 */
	@Test
	void testGetTickerldBySymbolNull() {
		TickerDto expected = null;
		TickerDto actual =  tickerDao.getTickerldBySymbol("ZZZ");
		assertEquals(expected, actual);
	}
//	/**
//	 * 存在しないティッカーシンボルZZZを使用して、
//	 * nullが返ることを確認する
//	 */
//	@Test
//	void testGetTickerldBySymbolNull2() {
//		TickerDao tickerDao = new TickerDao();
//		TickerDto expected = null;
//		tickerDao.CLASSNAME_ORACLE_DRIVER = "test";
//		TickerDto actual =  tickerDao.getTickerldBySymbol("ZZZ");
//		assertEquals(expected, actual);
//	}
//	/**
//	 * 引数がnullの時
//	 * nullが返ることを確認する
//	 */
//	@Test
//	void testGetTickerldBySymbolNull3() {
//		TickerDao tickerDao = new TickerDao();
//		TickerDto expected = null;
//		tickerDao.CLASSNAME_ORACLE_DRIVER = "test";
//		TickerDto actual =  tickerDao.getTickerldBySymbol(null);
//		assertEquals(expected, actual);
//	}

//	/**
//	 * 不正なクエリが発行された際、
//	 * nullが返ることを確認する
//	 */
//	@Test
//	void testGetTickerldBySymbolNull4() {
//		TickerDao tickerDao = new TickerDao();
//		TickerDto expected = null;
//		tickerDao.getTickerldBySymbol_sql = "test";
//		TickerDto actual =  tickerDao.getTickerldBySymbol(null);
//		assertEquals(expected, actual);
//	}

	/**
	 * ティッカーID23を使用して、
	 * ティッカーシンボルVTを取得する
	 */
	@Test
	void testGetTickerSymbolById() {
		String expected = "PFF";
		String actual =  tickerDao.getTickerSymbolById(9).getTicker_symbol();
		assertEquals(expected, actual);
	}

	/**
	 * 存在しないティッカーID99を使用して、
	 * nullが返ることを確かめる
	 */
	@Test
	void testGetTickerSymbolByIdNull() {
		TickerDto expected = null;
		TickerDto actual =  tickerDao.getTickerSymbolById(99);
		assertEquals(expected, actual);
	}

}

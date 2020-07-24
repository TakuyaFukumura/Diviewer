package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dto.TickerDto;

class TickerDaoTest {

	/**
	 * ティッカー情報の入力と削除を行う
	 * ティッカーシンボルAAPLを追加し削除する
	 * 両方が成功した場合はtrueを返す
	 */
	@Test
	void testInsertAndDelete() {
		boolean actual = false;
		TickerDao tickerDao = new TickerDao();
		if(tickerDao.insert("AAPL") &&
				tickerDao.delete("AAPL") )
			actual = true;
		assertTrue(actual);
	}
	/**
	 * ティッカー情報の削除を行う
	 * ドライバ名が不正でDBに接続できない場合
	 * falseが返ることを確かめる
	 */
	@Test
	void testInsertAndDeleteFalse() {
		boolean actual = true;
		TickerDao tickerDao = new TickerDao();
		tickerDao.CLASSNAME_ORACLE_DRIVER = "test";
		actual = tickerDao.delete("AAPL");
		assertFalse(actual);
	}


	/**
	 * ティッカーシンボルVTを使用して、
	 * ティッカーIDを取得する
	 */
	@Test
	void testGetTickerldBySymbol() {
		TickerDao tickerDao = new TickerDao();
		int expected = 23;
		int actual =  tickerDao.getTickerldBySymbol("VT").getTicker_id();
		assertEquals(expected, actual);
	}
	/**
	 * 存在しないティッカーシンボルZZZを使用して、
	 * nullが返ることを確認する
	 */
	@Test
	void testGetTickerldBySymbolNull() {
		TickerDao tickerDao = new TickerDao();
		TickerDto expected = null;
		TickerDto actual =  tickerDao.getTickerldBySymbol("ZZZ");
		assertEquals(expected, actual);
	}
	/**
	 * 存在しないティッカーシンボルZZZを使用して、
	 * nullが返ることを確認する
	 */
	@Test
	void testGetTickerldBySymbolNull2() {
		TickerDao tickerDao = new TickerDao();
		TickerDto expected = null;
		tickerDao.CLASSNAME_ORACLE_DRIVER = "test";
		TickerDto actual =  tickerDao.getTickerldBySymbol("ZZZ");
		assertEquals(expected, actual);
	}
	/**
	 * 引数がnullの時
	 * nullが返ることを確認する
	 */
	@Test
	void testGetTickerldBySymbolNull3() {
		TickerDao tickerDao = new TickerDao();
		TickerDto expected = null;
		tickerDao.CLASSNAME_ORACLE_DRIVER = "test";
		TickerDto actual =  tickerDao.getTickerldBySymbol(null);
		assertEquals(expected, actual);
	}

	/**
	 * 不正なクエリが発行された際、
	 * nullが返ることを確認する
	 */
	@Test
	void testGetTickerldBySymbolNull4() {
		TickerDao tickerDao = new TickerDao();
		TickerDto expected = null;
		tickerDao.getTickerldBySymbol_sql = "test";
		TickerDto actual =  tickerDao.getTickerldBySymbol(null);
		assertEquals(expected, actual);
	}

	/**
	 * ティッカーID23を使用して、
	 * ティッカーシンボルVTを取得する
	 */
	@Test
	void testGetTickerSymbolById() {
		TickerDao tickerDao = new TickerDao();
		String expected = "VT";
		String actual =  tickerDao.getTickerSymbolById(23).getTicker_symbol();
		assertEquals(expected, actual);
	}

	/**
	 * 存在しないティッカーID99を使用して、
	 * nullが返ることを確かめる
	 */
	@Test
	void testGetTickerSymbolByIdNull() {
		TickerDto expected = null;
		TickerDao tickerDao = new TickerDao();
		TickerDto actual =  tickerDao.getTickerSymbolById(99);
		assertEquals(expected, actual);
	}

}

package test.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dao.UserDao;
import dto.UserDto;
import model.CsvModel;

class UserDaoTest {
	private static CsvModel cm = new CsvModel();
	private UserDao userDao = new UserDao();
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
	 * IDを使用してユーザ情報を取得する。
	 * ユーザID「fukumura」のユーザ情報が持つ
	 * ニックネーム「福村」を取り出せるか検証する
	 */
	@Test
	void testGetUserById() {
		//backupAllCSV();
		//readeTestCSV();
		String expected = "46kumaBC";
		String actual =  userDao.getUserById("fukumura").getNickname();
		//readeBackupCSV();
		assertEquals(expected, actual);
	}

//	/**
//	 * pstmtがnullの場合は何も処理されないことを確認する
//	 */
//	@Test
//	void testExecuteQueryAndField() {
//		boolean actual = false;
//		userDao.pstmt = null;
//		try {
//			userDao.executeQuery();
//			actual = true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		assertTrue(actual);
//	}


	/**
	 * nullの場合は何も起きないことを確認する
	 */
	@Test
	void testPrintSQLException() {
		SQLException e = null;
		assertFalse(userDao.printSQLException(e));
	}

//	/**
//	 * DB接続処理で、
//	 * 不正なクエリを使用した際に
//	 * 結果がnullとなることを確認する
//	 */
//	@Test
//	void testGetUserById3() {
//		UserDao uda = new UserDao();
//		UserDto expected = null;
//		UserDto actual = new UserDto();
//		uda.setSql("select * fro");
//		actual =  uda.getUserById("fukumura");
//		assertEquals(expected, actual);
//	}

//	/**
//	 * DB接続処理で、
//	 * oracleドライバー名が不正で接続に失敗した際
//	 * 結果がnullとなることを確認する
//	 */
//	@Test
//	void testGetUserById4() {
//		UserDao uda = new UserDao();
//		UserDto expected = null;
//		UserDto actual = new UserDto();
//		uda.setSql("select * fro");
//		uda.CLASSNAME_ORACLE_DRIVER = "fuku";
//		actual =  uda.getUserById("fukumura");
//		assertEquals(expected, actual);
//	}


//	/**
//	 * DB接続処理で、
//	 * 不正なクエリを使用した際に
//	 * 結果がfalseとなることを確認する
//	 */
//	@Test
//	void testOpenConnectionFalse() {
//		UserDao ud = new UserDao();
//		//boolean expected = "福村";
//		ud.setSql("select * fro");
//		boolean actual =  ud.openConnection();
//		assertFalse(actual);
//	}



//	/**
//	 * IDを使用してユーザ情報を取得する。
//	 * 存在しないユーザIDを引数としたとき、
//	 * 戻り値がnullとなることを確認する
//	 */
//	@Test
//	void testGetUserById2() {
//		UserDao ud = new UserDao();
//		UserDto expected = null;
//		UserDto actual =  ud.getUserById("tanjiro");
//		assertEquals(expected, actual);
//	}
	/**
	 * IDを使用してユーザ情報を取得する。
	 * 引数がnullのとき、
	 * 戻り値がnullとなることを確認する
	 */
	@Test
	void testGetUserByIdNull() {
		UserDto expected = null;
		UserDto actual = userDao.getUserById(null);
		assertEquals(expected, actual);
	}
}

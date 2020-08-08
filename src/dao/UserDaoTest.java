package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import dto.UserDto;

class UserDaoTest {


	/**
	 * pstmtがnullの場合は何も処理されないことを確認する
	 */
	@Test
	void testExecuteQueryAndField() {
		UserDao uda = new UserDao();
		uda.pstmt = null;
		try {
			uda.executeQuery();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}


	/**
	 * nullの場合は何も起きないことを確認する
	 */
	@Test
	void testPrintSQLException() {
		UserDao uda = new UserDao();
		SQLException e = null;
		uda.printSQLException(e);
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

	/**
	 * IDを使用してユーザ情報を取得する。
	 * ユーザID「fukumura」のユーザ情報が持つ
	 * ニックネーム「福村」を取り出せるか検証する
	 */
	@Test
	void testGetUserById() {
		UserDao ud = new UserDao();
		String expected = "福村";
		String actual =  ud.getUserById("fukumura").getNickname();
		assertEquals(expected, actual);
	}

	/**
	 * IDを使用してユーザ情報を取得する。
	 * 存在しないユーザIDを引数としたとき、
	 * 戻り値がnullとなることを確認する
	 */
	@Test
	void testGetUserById2() {
		UserDao ud = new UserDao();
		UserDto expected = null;
		UserDto actual =  ud.getUserById("tanjiro");
		assertEquals(expected, actual);
	}

	/**
	 * IDを使用してユーザ情報を取得する。
	 * 引数がnullのとき、
	 * 戻り値がnullとなることを確認する
	 */
	@Test
	void testGetUserByIdNull() {
		UserDao ud = new UserDao();
		UserDto expected = null;
		UserDto actual =  ud.getUserById(null);
		assertEquals(expected, actual);
	}

}

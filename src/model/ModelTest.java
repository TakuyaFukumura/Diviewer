/**
 *
 */
package model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import dao.DividendIncomeDao;
import dao.PossessionDao;

/**
 * @author bx0045
 *
 */
class ModelTest {


	/**
	 * ティカー情報の削除を行い。
	 * 戻り値がtrueであることを確認する
	 */
	@Test
	void testDeleteTicker() {
		Model m = new Model();
		//データ追加
		new PossessionDao().insert("fukumura", 25, new BigDecimal("0"), new BigDecimal("0"));
		new DividendIncomeDao().insert("fukumura", 25, "2222-12-22", new BigDecimal("0"));
		//ティッカーID取得
		boolean actual =  m.deleteTicker("fukumura", "BND", 25);
		assertTrue(actual);
	}

	/**
	 * 月ごとの配当受取額を配列で取得するテスト
	 * ユーザfukumuraが2020年に受け取ったデータの取得を試みて
	 * 期待値と一致するか検証する
	 * {@link model.Model#getSumIncomeList(java.lang.String, int)} のためのテスト・メソッド。
	 */
	@Test
	void testGetSumIncomeList() {
		//4.26,0.28,1.71,1.48,1.95,0.7,0,0,0,0,0,0
		Model m = new Model();
		String[] expected = {"4.26","0.28","1.71","1.48","1.95","0.7","0","0","0","0","0","0"};
		String[] actual =  m.getSumIncomeList("fukumura", 2020);
		assertArrayEquals(expected, actual);
	}
	/**
	 * 月ごとの配当受取額を配列で取得するテスト
	 * ユーザがNULLの場合、データの存在しない年の場合
	 * 全ての要素が「0」になることを確かめる
	 * {@link model.Model#getSumIncomeList(java.lang.String, int)} のためのテスト・メソッド。
	 * @param user_id ユーザID
	 * @param year 取得したい年
	 */
	@ParameterizedTest
	@CsvSource({"null,2020","fukumura,3020"	})
	@DisplayName("月毎配当受取額を配列取得するテスト")
	void testGetSumIncomeListNull(String user_id, int year) {
		Model m = new Model();
		String[] expected = {"0","0","0","0","0","0","0","0","0","0","0","0"};
		String[] actual =  m.getSumIncomeList(null, 2020);
		assertArrayEquals(expected, actual);
	}//0パターンを纏めて記述したい

	/**
	 * 文字列の配列を一つの文字列に合成するテスト
	 * testGetSumIncomeListで取得した値をもとに期待値と比較を行う
	 * {@link model.Model#getCartDataString(java.lang.String[])} のためのテスト・メソッド。
	 */
	@Test
	void testGetCartDataString() {
		Model m = new Model();
		String expected = "4.26,0.28,1.71,1.48,1.95,0.7,0,0,0,0,0,0";
		String actual =  m.getCartDataString(m.getSumIncomeList("fukumura", 2020));
		assertEquals(expected, actual);
	}

	/**
	 * 文字列の配列を一つの文字列に合成するテスト
	 * 引数がnullの時に空文字が返されることを確かめる
	 * {@link model.Model#getCartDataString(java.lang.String[])} のためのテスト・メソッド。
	 */
	@Test
	void testGetCartDataStringNull() {
		Model m = new Model();
		String expected = "0,0,0,0,0,0,0,0,0,0,0,0";
		String actual =  m.getCartDataString(null);
		assertEquals(expected, actual);
	}

	/**
	 * ログインできるかどうか確かめるテスト
	 * ユーザID「fukumura」
	 * パスワード「1114」
	 * でtrueが返ることを確かめる
	 * {@link model.Model#loginCheck(java.lang.String, java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	void testLoginCheck() {
		Model m = new Model();
		boolean actual = m.loginCheck("fukumura","1114");
		assertTrue(actual);
	}

	/**
	 * ログインできるかどうか確かめるテスト
	 * 存在しないユーザ情報
	 * ユーザID「sato」
	 * パスワード「1030」
	 * を引数としてfalseが返ることを確かめる
	 * {@link model.Model#loginCheck(java.lang.String, java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	void testLoginCheckFalse() {
		Model m = new Model();
		boolean actual = m.loginCheck("sato","1030");
		assertFalse(actual);
	}

	/**
	 * 登録済みのユーザID「fukumura」と
	 * パスワード「1114」を入力して
	 * トップページのURLが返されることを確認する
	 * {@link model.Model#decideUrl(java.lang.String, java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	void testDecideUrl() {
		Model m = new Model();
		String expected = "top.jsp";
		String actual =  m.decideUrl("fukumura", "1114");
		assertEquals(expected, actual);
	}

	/**
	 * 登録されていないのユーザID「tanaka」と
	 * パスワード「9877」を入力して
	 * ログインページのURLが返されることを確認する
	 * {@link model.Model#decideUrl(java.lang.String, java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	void testDecideUrlFalse() {
		Model m = new Model();
		String expected = "index";
		String actual =  m.decideUrl("tanaka", "9877");
		assertEquals(expected, actual);
	}

}

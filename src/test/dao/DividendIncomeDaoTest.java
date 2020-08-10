package test.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dao.DividendIncomeDao;
import dto.DividendIncomeDto;
import test.BaseTest;

class DividendIncomeDaoTest extends BaseTest{
	private DividendIncomeDao did = new DividendIncomeDao();
	private List<DividendIncomeDto> incomeList = new ArrayList<>();

	/**
	 *
	 */
	@Test
	void testDeleteWithSymbol() {
		flag = did.deleteWithSymbol("fukumura", 11);
		readeTestCSV();
		assertTrue(flag);
	}

	/**
	 * ユーザーIDを使用して指定した年月の配当合計額を取得する。
	 * ユーザID「fukumura」,2020年、1月を引数として実行した結果を期待値と比較する
	 */
	@Test
	void testGetSumIncomeByIdAndMonth() {
		BigDecimal expected = new BigDecimal("0.51");
		BigDecimal actual = did.getSumIncomeByIdAndMonth( "fukumura", 2020, 1 );
		assertEquals(expected, actual);
	}

	/**
	 * ユーザーIDを使用して指定した年月の配当合計額を取得する。
	 * ユーザID「fukumura」,2020年、1月を引数として実行した結果を期待値と比較する
	 */
	@Test
	void testGetSumIncomeByIdAndMonth3() {
		BigDecimal expected = new BigDecimal("0");
		BigDecimal actual = did.getSumIncomeByIdAndMonth( "fukumura", 2019, 12 );
		assertEquals(expected, actual);
	}

//	/**
//	 * ユーザーIDを使用して指定した年月の配当合計額を取得する。
//	 * ユーザID「fukumura」,2020年、1月を引数として実行する際に
//	 * DB接続が失敗した場合0が返ることを確認する
//	 */
//	@Test
//	void testGetSumIncomeByIdAndMonth2() {
//		BigDecimal expected = new BigDecimal("0");
//		DividendIncomeDao did = new DividendIncomeDao();
//		did.CLASSNAME_ORACLE_DRIVER = "test";
//		BigDecimal actual = did.getSumIncomeByIdAndMonth( "fukumura", 2020, 1 );
//		assertEquals(expected, actual);
//	}

	/**
	 * 配当情報をテーブルに追加して削除するテスト
	 * "fukumura", 23, "2222-02-11", new BigDecimal("0.26")を引数として情報を追加した後、
	 * ユーザIDとティッカーIDを指定して該当情報を削除する
	 * 処理に成功した場合はtrueが返る
	 */
	@Test
	void testInsertAndDelete() {
		boolean flag = false;
		if(did.insert("fukumura", 15, "2222-02-11", new BigDecimal("0.26")) &&
				did.delete(did.findIncomList("fukumura",15).get(0).getDividend_income_id()))
				flag = true;
		readeTestCSV();
		assertTrue(flag);
	}

//	/**
//	 * 配当情報をテーブルに追加して削除するテスト
//	 * "fukumura", 23, "2222-02-11", new BigDecimal("0.26")を引数として情報を追加した後、
//	 * ユーザIDとティッカーIDを指定して該当情報を削除する
//	 * 処理に成功した場合はtrueが返る
//	 */
//	@Test
//	void testDeleteFalse() {
//		DividendIncomeDao did = new DividendIncomeDao();
//		did.CLASSNAME_ORACLE_DRIVER = "test";
//		boolean flag = did.delete(99);
//		assertFalse(flag);
//	}

	/**
	 * 配当情報の1カラムを更新する
	 * 成功時にtrueが返る
	 */
	@Test
	void testUpdate() {
		flag = false;
		flag = did.update(23, new BigDecimal("0.1"), "2020-05-18");
		readeTestCSV();
		assertTrue(flag);
	}

//	/**
//	 * 配当情報の1カラムを更新する
//	 * DB接続失敗時にfalseが返る
//	 */
//	@Test
//	void testUpdateFalse() {
//		DividendIncomeDao did = new DividendIncomeDao();
//		did.CLASSNAME_ORACLE_DRIVER = "test";
//		boolean flag = did.update(23, new BigDecimal("0.1"), "2020-05-18");
//		assertFalse(flag);
//	}

//	@Test
//	void testDelete() {
//		DividendIncomeDao did = new DividendIncomeDao();
//		boolean flag = false;
//		flag = did.delete(did.findIncomList("fukumura",23).get(0).getDividend_income_id());
//		assertTrue(flag);
//	}

	/**
	 * 配当IDを使用してデータを取り出す。
	 * 期待値と同じものが取得できているか検証する。
	 */
	@Test
	void testGetDataById() {
		int expected = 12;
		int actual =  did.getDataById(23).getTicker_id();
		assertEquals(expected, actual);
	}

//	/**
//	 * 配当IDを使用してデータを取り出す。
//	 * DB接続失敗時にnullが返ることを確かめる
//	 */
//	@Test
//	void testGetDataById2() {
//		DividendIncomeDto expected = null;
//		DividendIncomeDao did = new DividendIncomeDao();
//		did.CLASSNAME_ORACLE_DRIVER = "test";
//		DividendIncomeDto actual =  did.getDataById(23);
//		assertEquals(expected, actual);
//	}



	/**
	 * ユーザIDとティッカーIDを使用して配当データの全件取得を行う。
	 * 期待したデータが取り出せているか検証する。
	 */
	@Test
	void testFindIncomList() {
		incomeList = did.findIncomList("fukumura", 14); //MA
		BigDecimal expected = new BigDecimal("0.35");
		BigDecimal actual =  incomeList.get(0).getAftertax_income();
		assertEquals(expected, actual);
	}

	/**
	 * 新しく追加した配当情報をリスト形式で取得する
	 * 期待したデータを取り出せているか確認する
	 */
	@Test
	void testFindNewIncom() {
		incomeList = did.findNewIncom("fukumura");
		String expected = "MAIN";
		String actual =  incomeList.get(0).getTicker_symbol();
		assertEquals(expected, actual);
	}

}

/**
 *
 */
package test.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import dao.PossessionDao;
import test.BaseTest;

/**
 * @author bx0045
 * 所持情報の取得編集処理のテストクラス
 */
class PossessionDaoTest extends BaseTest {
	private PossessionDao pd = new PossessionDao();

	/**
	 * 所持情報の曖昧検索を行えているか検証する
	 * 銘柄MAINを検索して、
	 * その銘柄の所持数1を正しく取り出せているか確認する
	 * {@link dao.PossessionDao#searchPossession(java.lang.String, java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	void testSearchPossession() {
		BigDecimal expected = new BigDecimal("1");
		BigDecimal actual =  pd.searchPossession("fukumura", "MAIN").get(0).getUnit();
		assertEquals(expected, actual);
	}

//	/**
//	 * ドライバ名が不正で、
//	 * DB接続できなかった場合、
//	 * Nullが返ることを確認する
//	 */
//	@Test
//	void testSearchPossessionNull() {
//		List<PossessionDto> expected = new ArrayList<>();
//		PossessionDao pd = new PossessionDao();
//		pd.CLASSNAME_ORACLE_DRIVER = "test";
//		List<PossessionDto> actual =  pd.searchPossession("fukumura", "MAIN");
//		assertEquals(expected, actual);
//	}

//	/**
//	 * 更新処理
//	 * ドライバ名が不正で、
//	 * DB接続できなかった場合、
//	 * falseが返ることを確認する
//	 */
//	@Test
//	void testUpdateFalse() {
//		PossessionDao pd = new PossessionDao();
//		pd.CLASSNAME_ORACLE_DRIVER = "test";
//		boolean actual =  pd.update("fukumura", 23, "VT",
//				new BigDecimal("2"), new BigDecimal("0.3"), "2020-02-01");
//		assertFalse(actual);
//	}

//	/**
//	 * 削除処理
//	 * ドライバ名が不正で、
//	 * DB接続できなかった場合、
//	 * falseが返ることを確認する
//	 */
//	@Test
//	void testDeleteFalse() {
//		PossessionDao pd = new PossessionDao();
//		pd.CLASSNAME_ORACLE_DRIVER = "test";
//		boolean actual =  pd.delete("fukumura", "VT");
//		assertFalse(actual);
//	}

//	/**
//	 * 追加処理
//	 * ドライバ名が不正で、
//	 * DB接続できなかった場合、
//	 * falseが返ることを確認する
//	 */
//	@Test
//	void testInsertFalse() {
//		PossessionDao pd = new PossessionDao();
//		pd.CLASSNAME_ORACLE_DRIVER = "test";
//		boolean actual =  pd.insert("fukumura", 23, new BigDecimal("1"), new BigDecimal("0.26"));
//		assertFalse(actual);
//	}



//	/**
//	 * {@link dao.PossessionDao#update(java.lang.String, int, java.lang.String, java.math.BigDecimal, java.math.BigDecimal, java.lang.String)} のためのテスト・メソッド。
//	 */
//	@Test
//	void testUpdate() {
//		PossessionDao pd = new PossessionDao();
//		boolean flag = false;
//		flag = pd.update(23, new BigDecimal("0.1"), "2020-05-18");
//		assertTrue(flag);
//	}

	/**
	 * 所持情報の追加、更新、削除といった、
	 * 一連の流れを実行できるか検証する。
	 * 各プロセスで処理が成功した際はtrueを返すようになっているため、
	 * 全プロセスでtrueとなっていることを確認する。
	 * {@link dao.PossessionDao#insert(java.lang.String, int, java.math.BigDecimal, java.math.BigDecimal)} のためのテスト・メソッド。
	 * 所持データインサート
	 * （必要情報：StringユーザID、intティッカーID、BigDecimal口数、BigDecimal取得単価、String受領日）
	 */
	@Test
	void testInsertAndUpdateAndDelete() {
		boolean flag = false;
		if(pd.insert("fukumura", 6, new BigDecimal("1"), new BigDecimal("0.26")) &&
				pd.update("fukumura", 6, "SPDY",
						new BigDecimal("2"), new BigDecimal("0.3"), "2020-02-01") &&
				pd.delete("SPDY","fukumura"))
				flag = true;
		assertTrue(flag);
	}

	/**
	 * 引数を使って所持データを取り出すテストである。
	 * ユーザfukumuraの所持する銘柄MAINの所持情報を取得し、
	 * そのデータが持っているべき保有数量(30)を期待値として検証する
	 * {@link dao.PossessionDao#getPossessionBySymbolId(java.lang.String, java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	void testGetPossessionBySymbolId() {
		BigDecimal expected = new BigDecimal("1");
		BigDecimal actual =  pd.getPossessionBySymbolId("MAIN", "fukumura").getUnit();
		assertEquals(expected, actual);
	}
//	/**
//	 * DB接続に失敗した場合、
//	 * nullが返ることを確認する
//	 */
//	@Test
//	void testGetPossessionBySymbolIdNull() {
//		PossessionDto expected = null;
//		PossessionDao pd = new PossessionDao();
//		pd.CLASSNAME_ORACLE_DRIVER = "test";
//		PossessionDto actual =  pd.getPossessionBySymbolId("MAIN", "fukumura");
//		assertEquals(expected, actual);
//	}

}

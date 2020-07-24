package dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;

class PossessionDtoTest {

	/**
	 * インスタンスを生成できるか確認
	 * ユーザID「fukumura」、ティッカーID「1」、保有数量100、平均取得単価26.15、
	 * データ作成日と更新日は現在日を入れる
	 */
	@Test
	void testPossessionDtoStringIntBigDecimalBigDecimalDateDate() {
		PossessionDto pd = new PossessionDto("fukumura",1,new BigDecimal("100"),
				new BigDecimal("26.15"),new Date(),new Date());
	}

	/**
	 * インスタンスを生成できるか確認
	 * ユーザID「fukumura」、ティッカーID「1」、ティッカーシンボル「PFF」
	 * 保有数量100、平均取得単価26.15
	 * データ作成日と更新日は現在日を入れる
	 */
	@Test
	void testPossessionDtoStringIntStringBigDecimalBigDecimalDateDate() {
		PossessionDto pd = new PossessionDto("fukumura",1,"PFF",new BigDecimal("100"),
				new BigDecimal("26.15"),new Date(),new Date());
	}

	/**
	 * フィールドにユーザID「fukumura」を格納した後、
	 * 取り出すことができるか確認
	 */
	@Test
	void testSetGetUser_id() {
		PossessionDto pd = new PossessionDto();
		String expected = "fukumura";
		pd.setUser_id("fukumura");
		String actual =  pd.getUser_id();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにティッカーID「0」を格納した後、
	 * 取り出せるかどうか確認
	 */
	@Test
	void testSetGetTicker_id() {
		PossessionDto pd = new PossessionDto();
		int expected = 0;
		pd.setTicker_id(0);
		int actual =  pd.getTicker_id();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドに保有数量0をセットした後、
	 * その値を取り出せるか確認する
	 */
	@Test
	void testSetGetUnit() {
		PossessionDto pd = new PossessionDto();
		BigDecimal expected = new BigDecimal("0");
		pd.setUnit(new BigDecimal("0"));
		BigDecimal actual =  pd.getUnit();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドに平均取得単価0を入力して、
	 * その値を取り出せるか確認する
	 */
	@Test
	void testSetGetAverage_unit_cost() {
		PossessionDto pd = new PossessionDto();
		BigDecimal expected = new BigDecimal("0");
		pd.setAverage_unit_cost(new BigDecimal("0"));
		BigDecimal actual =  pd.getAverage_unit_cost();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにデータ作成日情報を格納した後、
	 * その値を取り出せるか確認する
	 */
	@Test
	void testSetGetCreated_at() {
		PossessionDto pd = new PossessionDto();
		Date expected = new Date();
		pd.setCreated_at(new Date());
		Date actual =  pd.getCreated_at();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにデータ更新日情報を格納した後、
	 * その値を取り出せるか確認する
	 */
	@Test
	void testSetGetUpdate_at() {
		PossessionDto pd = new PossessionDto();
		Date expected = new Date();
		pd.setUpdate_at(new Date());
		Date actual =  pd.getUpdate_at();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにティッカーシンボルVTを格納した後、
	 * その情報を取り出すことができるかどうか確認する
	 */
	@Test
	void testSetGetTicker_symbol() {
		PossessionDto pd = new PossessionDto();
		String expected = "VT";
		pd.setTicker_symbol("VT");
		String actual =  pd.getTicker_symbol();
		assertEquals(expected, actual);
	}

}

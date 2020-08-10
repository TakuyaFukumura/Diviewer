package test.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;

import dto.DividendIncomeDto;

class DividendIncomeDtoTest {

	/**
	 * インスタンスを生成できること確かめる
	 */
	@Test
	void testDividendIncomeDtoIntStringIntDateBigDecimalDateDate() {
		DividendIncomeDto did = new DividendIncomeDto(1,"fukumura",1,new Date(),
				new BigDecimal("0.25"),new Date(),new Date());
	}

	/**
	 * インスタンスの生成を確かめる
	 */
	@Test
	void testDividendIncomeDtoIntStringIntStringDateBigDecimalDateDate() {
		DividendIncomeDto did = new DividendIncomeDto(1,"fukumura",1,"PFF", new Date(),
				new BigDecimal("0.25"),new Date(),new Date());
	}

	/**
	 * フィールドにインカムIDを格納して取り出せることを確かめる
	 *
	 */
	@Test
	void testSetGetDividend_income_id() {
		DividendIncomeDto did = new DividendIncomeDto();
		int expected = 0;
		did.setDividend_income_id( 0 );
		int actual =  did.getDividend_income_id();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにユーザIDを格納して取り出せることを確かめる
	 */
	@Test
	void testSetGetUser_id() {
		DividendIncomeDto did = new DividendIncomeDto();
		String expected = "fukumura";
		did.setUser_id("fukumura");
		String actual =  did.getUser_id();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにティッカーIDを格納して取り出せることを確かめる
	 */
	@Test
	void testSetGetTicker_id() {
		DividendIncomeDto did = new DividendIncomeDto();
		int expected = 0;
		did.setTicker_id(0);
		int actual =  did.getTicker_id();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドに受領日情報を格納して取り出せることを確かめる
	 */
	@Test
	void testSetGetReceipt_date() {
		DividendIncomeDto did = new DividendIncomeDto();
		Date expected = new Date();
		did.setReceipt_date(new Date());
		Date actual =  did.getReceipt_date();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドに税引き後配当受取額情報を格納して取り出せることを確かめる
	 */
	@Test
	void testSetGetAftertax_income() {
		DividendIncomeDto did = new DividendIncomeDto();
		BigDecimal expected = new BigDecimal("0.12");
		did.setAftertax_income(new BigDecimal("0.12"));
		BigDecimal actual =  did.getAftertax_income();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにデータ作成日情報を格納して取り出せることを確かめる
	 */
	@Test
	void testSetGetCreated_at() {
		DividendIncomeDto did = new DividendIncomeDto();
		Date expected = new Date();
		did.setCreated_at(new Date());
		Date actual =  did.getCreated_at();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにデータ更新日情報を格納して取り出せることを確かめる
	 */
	@Test
	void testSetGetUpdate_at() {
		DividendIncomeDto did = new DividendIncomeDto();
		Date expected = new Date();
		did.setUpdate_at(new Date());
		Date actual =  did.getUpdate_at();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにティッカーシンボルを格納して取り出せることを確かめる
	 */
	@Test
	void testSetGetTicker_symbol() {
		DividendIncomeDto did = new DividendIncomeDto();
		String expected = "VT";
		did.setTicker_symbol("VT");
		String actual =  did.getTicker_symbol();
		assertEquals(expected, actual);
	}

}

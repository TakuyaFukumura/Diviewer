package test.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dto.TickerDto;

class TickerDtoTest {

	/**
	 * インスタンスの生成できるか確認
	 */
	@Test
	void testTickerDto() {
		TickerDto td = new TickerDto(0, "VT");
		int expected = 0;
		int actual =  td.getTicker_id();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにティッカーIDをセットとゲットができるか確認
	 */
	@Test
	void testvSetGetTicker_id() {
		TickerDto td = new TickerDto();
		int expected = 0;
		td.setTicker_id(0);
		int actual =  td.getTicker_id();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにティッカーシンボルをセットとゲットができるか確認
	 */
	@Test
	void testSetGetTicker_symbol() {
		TickerDto td = new TickerDto();
		String expected = "VT";
		td.setTicker_symbol("VT");
		String actual =  td.getTicker_symbol();
		assertEquals(expected, actual);
	}

}

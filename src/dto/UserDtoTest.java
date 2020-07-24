package dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

class UserDtoTest {

	/**
	 *インスタンスの生成テスト
	 *ユーザID「fukumura」パスワード「1114」ニックネーム「福村」作成日＆更新日は現在日時
	 */
	@Test
	void testUserDto() {
		String expected = "fukumura";
		Date created_at = new Date();
		Date update_at = new Date();
		UserDto ud = new UserDto("fukumura", "1114", "福村", created_at, update_at );
		String actual =  ud.getUser_id();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにユーザIDをセットした後、
	 * 正常に取り出せるか確認
	 */
	@Test
	void testSetGetUser_id() {
		UserDto ud = new UserDto();
		String expected = "fukumura";
		ud.setUser_id("fukumura");
		String actual =  ud.getUser_id();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにパスワードを入力した後、
	 * その値を取り出せるかどうか確認
	 */
	@Test
	void testSetGetUser_pass() {
		UserDto ud = new UserDto();
		String expected = "1114";
		ud.setUser_pass("1114");
		String actual =  ud.getUser_pass();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにニックネーム情報を格納した後、
	 * ゲッターで取り出せるか確認する
	 */
	@Test
	void testSetGetNickname() {
		UserDto ud = new UserDto();
		String expected = "福村";
		ud.setNickname("福村");
		String actual =  ud.getNickname();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにデータ作成日情報を格納した後、
	 * その値を取り出せるか確認する
	 */
	@Test
	void testGetCreated_at() {
		UserDto ud = new UserDto();
		Date expected = new Date();
		ud.setCreated_at(new Date());
		Date actual =  ud.getCreated_at();
		assertEquals(expected, actual);
	}

	/**
	 * フィールドにデータ更新日情報を格納した後、
	 * その値を取り出せるか確認する
	 */
	@Test
	void testSetGetUpdate_at() {
		UserDto ud = new UserDto();
		Date expected = new Date();
		ud.setUpdate_at(new Date());
		Date actual =  ud.getUpdate_at();
		assertEquals(expected, actual);
	}

}

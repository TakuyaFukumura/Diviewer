/**
 *
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.UserDto;

/**
 * @author bx0045
 *ユーザ情報の取得編集処理を行うクラス
 */
public class UserDao extends BasisDao{

	private UserDto userDto = new UserDto();
	private List<UserDto> userList = new ArrayList<>();

	/**
	 * CSVからユーザー情報をテーブルに追加する
	 * @return 成功true 失敗false
	 */
	public boolean insert(String user_id, String user_pass,
			String nickname, String created_at, String update_at) {
		flag = false;
		sql = "INSERT INTO user_table VALUES ( ?, ?, ?,"
				+ "TO_DATE( ?, 'YYYY-MM-DD'), TO_DATE( ?, 'YYYY-MM-DD') )";
		if (openConnection()) {
			try {
				pstmt.setString(1, user_id);
				pstmt.setString(2, user_pass);
				pstmt.setString(3, nickname);
				pstmt.setString(4, created_at);
				pstmt.setString(5, update_at);
				if (executeUpdate() == 1) {
					flag = true;
				}
			} catch (SQLException e) {
				printSQLException(e);
			}finally{
				closeConnection();
			}
		}
		return flag;
	}

	/**
	 * ティッカーテーブル初期化
	 * @return 成功true 失敗false
	 */
	public boolean delete() {
		sql = "DELETE FROM user_table ";
		return deleteAll();
	}

	/**
	 * user_table全件取得
	 * @return 全ユーザ情報
	 */
	public List<UserDto> getUserAll() {
		userList = new ArrayList<>();
		sql = "SELECT user_id,user_pass,nickname,created_at,update_at "
				+ "FROM user_table ";
		if (openConnection()) { //DB接続処理
			try {
				executeQuery(); //SQL実行から値取得までの処理
			} catch (SQLException e) {
					printSQLException(e);
			}finally{
				closeConnection(); //DB切断処理
			}
		}
		return userList;
	}

	/**
	 * ユーザIDからユーザ情報を取得する
	 * @param user_id ユーザID
	 * @return UserDTOユーザ情報 失敗時null
	 */
	public UserDto getUserById(String user_id) {
		userDto = null;
		sql = "select user_id,user_pass,nickname,created_at,update_at from "
				+ "user_table where user_id = ? ";
		if (openConnection()) { //DB接続処理
			try {
				pstmt.setString(1, user_id); //SQLに情報加える
				executeQuery(); //SQL実行から値取得までの処理
			} catch (SQLException e) {
				printSQLException(e);
			}finally{
				closeConnection(); //DB切断処理
			}
		}
		return userDto;
	}

	/**
	 * SQLの実行結果からデータを取得する処理
	 * 値はフィールドのuserDtoに格納している
	 * @param rs クエリ実行結果
	 * @throws java.sql.SQLException SQL実行エラー
	 */
	public void convertReserSet(ResultSet rs) throws SQLException {
		while (rs.next()) {
			userDto = new UserDto();
			userDto.setUser_id(rs.getString("user_id"));
			userDto.setUser_pass(rs.getString("user_pass"));
			userDto.setNickname(rs.getString("nickname"));
			userDto.setCreated_at(rs.getDate("created_at"));
			userDto.setUpdate_at(rs.getDate("update_at"));
			userList.add(userDto);
		}
	}
}

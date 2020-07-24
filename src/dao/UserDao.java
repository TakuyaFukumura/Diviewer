/**
 *
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.UserDto;

/**
 * @author bx0045
 *ユーザ情報の取得編集処理を行うクラス
 */
public class UserDao{
	/** MYSQLドライバ名 */
	String CLASSNAME_ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	/** MYSQL接続用URL */
	String URL_ORACLE = "jdbc:oracle:thin:@localhost:1521/xepdb1";
	/** MYSQL接続用ユーザ名（root） */
	String USERNAME_ORACLE = "scott";
	/** MYSQL接続用パスワード（root） */
	String PASSWORD_ORACLE = "tiger";

	protected Connection con;
	protected PreparedStatement pstmt;
	private String sql = "select user_id,user_pass,nickname,created_at,update_at from "
			+ "user_table where user_id = ? ";
	private UserDto userDto = null;

	/**
	 * sqlのセッター
	 * 通常は使う必要がない
	 * テスト用にSQLを書き換えられるようにする
	 * @param sql 実行するSQL文
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * ユーザIDからユーザ情報を取得する
	 * @param user_id ユーザID
	 * @return UserDTOユーザ情報 失敗時null
	 */
	public UserDto getUserById(String user_id) {
		if (openConnection()) { //DB接続処理
			try {
				pstmt.setString(1, user_id); //SQLに情報加える
				executeQueryAndField();//SQL実行から値取得までの処理
			} catch (SQLException e) {
					printSQLException(e);
			}finally{
				closeConnection(); //DB切断処理
			}
		}
		return userDto;
	}

	/**
	 * SQLを実行して、
	 * 取得した値をFieldにセットする処理
	 * フィールドのpstmtを使用する
	 */
	public void executeQueryAndField(){
		if (pstmt != null) {
			try {
				ResultSet rs;
				rs = pstmt.executeQuery(); //データベースを検索するメソッドSELECT用
				convertReserSet(rs); //FieldのuserDtoを使用して値セット
				rs.close();
			} catch (SQLException e) { //SQLの実行エラー
				printSQLException(e);
			}finally{
				closeConnection(); //DB切断処理
			}
		}
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
		}
	}

	/**
	 * SQLException発生時にエラーメッセージを表示する
	 * @param e エラーを説明する文字列
	 */
	public void printSQLException(SQLException e) {
		if(e != null) {
			System.out.println("ERROR CODE :" + e.getErrorCode());
			System.out.println(pstmt.toString() + "を実行\n" + e.getMessage() + "が発生。");
		}
	}

	/**
	 * DB接続処理
	 * @return 成功true 失敗false
	 */
	public boolean openConnection() {
		boolean flag = false;
		try {
			Class.forName(CLASSNAME_ORACLE_DRIVER);
			con = DriverManager.getConnection(URL_ORACLE, USERNAME_ORACLE, PASSWORD_ORACLE);
			pstmt = con.prepareStatement(sql); //SQL使用部
			con.setAutoCommit(false);
			flag = true;
		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {
			printSQLException(e);
		}
		return flag;
	}

	/**
	 * DB切断処理
	 */
	public void closeConnection() {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			pstmt = null;
			if (con != null) {
				con.close();
			}
			con = null;
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
}

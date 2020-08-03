/**
 *
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author fukumura
 *
 */
public abstract class BasisDao {
	/** MYSQLドライバ名 */
	String CLASSNAME_ORACLE_DRIVER = "org.h2.Driver";
	/** MYSQL接続用URL */
	String URL_ORACLE = "jdbc:h2:tcp://localhost/~/example";
	/** MYSQL接続用ユーザ名（root） */
	String USERNAME_ORACLE = "blackchoco1114";
	/** MYSQL接続用パスワード（root） */
	String PASSWORD_ORACLE = "katuo045A";

	protected Connection con;
	protected PreparedStatement pstmt;
	protected String sql = "";

	/**
	 * DB接続処理
	 * @return 成功true 失敗false
	 */
	public boolean openConnection() {
		boolean flag = false;
		try {
			Class.forName(CLASSNAME_ORACLE_DRIVER);
			con = DriverManager.getConnection(URL_ORACLE,
					USERNAME_ORACLE, PASSWORD_ORACLE);
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
	 * SQLを実行して、
	 * 取得した値をFieldにセットする処理
	 * フィールドのpstmtを使用する
	 */
	public void executeQuery() throws SQLException {
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
	 * 値はフィールドのDtoに格納している
	 * @param rs クエリ実行結果
	 * @throws java.sql.SQLException SQL実行エラー
	 */
	abstract  void convertReserSet(ResultSet rs) throws SQLException;

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

}

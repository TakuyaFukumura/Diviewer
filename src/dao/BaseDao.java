/**
 *
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.BaseDto;

/**
 * @author bx0045
 *
 */
/**
 * DAOの基底クラス、基本的なDBアクセスメソッドが宣言されている<br>
 * createConnectionにSQLを指定する<br>
 * 汎用メソッドで対応できないものはフィールド変数を使用して独自処理を行う<br>
 * システム上正常でない処理結果にはexceptionが設定する<br>
 * 一覧はlistに格納する
 */
public abstract class BaseDao{
	/** MYSQLドライバ名 */
	String CLASSNAME_ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	/** MYSQL接続用URL */
	String URL_ORACLE = "jdbc:oracle:thin:@localhost:1521/xepdb1";
	/** MYSQL接続用ユーザ名（root） */
	String USERNAME_ORACLE = "scott";
	/** MYSQL接続用パスワード（root） */
	String PASSWORD_ORACLE = "tiger";


	/**ユーザテーブル名 */
	String TABLE_NAME_USER_TABLE = "user_table";

	/**
	 * select時にデータを格納する変数、常に上書きされるべき
	 */
	protected List<BaseDto> list;
	protected Connection con;
	protected PreparedStatement pstmt;
	protected String tableName;
//	/**
//	 * 処理中に発生した例外
//	 */
//	protected StockManagementException exception;

	public BaseDao() {

	}

//	/**
//	 * データベース処理中に発生したエラーを取得する
//	 *
//	 * @return 発生した例外
//	 */
//	public StockManagementException getException() {
//		return exception;
//	}

//	public List<BaseDto> getList() {
//		return list;
//	}

	/**
	 * 接続処理、コネクションの生成を行う、使用するSQLのステートメントを生成 失敗時にはexceptionが設定される
	 *
	 * @param sql
	 *            使用するSQL
	 * @return 接続ステータス、成功true 失敗false
	 */
	protected boolean createConnection(String sql) {
		boolean flag = false;
//		exception = null;
		try {

			Class.forName(CLASSNAME_ORACLE_DRIVER);
			con = DriverManager.getConnection(URL_ORACLE, USERNAME_ORACLE,
					PASSWORD_ORACLE);
			pstmt = con.prepareStatement(sql);
			con.setAutoCommit(false);
			flag = true;
		} catch (ClassNotFoundException e) {
//			exception = new DatabaseDriverNotFoundException();
//			System.out.println(exception.getMessage() + "が発生。jarファイルの配置を確認する");
		} catch (SQLException e) {
//			exception = new DatabaseConnectionFailureException();
//			System.out.println(exception.getMessage() + "が発生。データベース接続の設定値を確認する");
		}
		return flag;
	}

	/**
	 * 汎用メソッド、select(選択)処理を行う<br>
	 * 取得したレコードはconvertReserSetでlistに格納（上書き）される。<br>
	 * 失敗時にはexceptionが設定される
	 *
	 * @return 選択結果 成功 true 失敗 false
	 */
	protected boolean executeComonQuery() {
		boolean flag = false;
		if (pstmt!=null) { //falseになってしまう
			try {
				ResultSet rs;
				rs = pstmt.executeQuery();
				list = new ArrayList<BaseDto>();
				convertReserSet(rs);
				rs.close();
				flag = true;
			} catch (SQLException e) {
//				setException(e.getErrorCode());
				System.out.println("ERROR CODE :" + e.getErrorCode());
				System.out.println(pstmt.toString() + "を実行\n" + e.getMessage()
						+ "が発生。");
			}finally{
				closeConnection();
			}
//		} else if (!isError()) {
//			exception = new DatabaseSelectFailureException();
		}
		return flag;

	}

	/**
	 * ResultSetを各Beanに置き換え、フィールドのlistに格納する
	 *
	 * @param rs
	 *            SELECT後のResultSET
	 * @throws SQLException コンバートエラー
	 */
	public abstract void convertReserSet(ResultSet rs) throws SQLException;
//
	//@param sql
	/**
	 * 汎用メソッド、select(選択)処理以外を行う、実行時に影響のあった行数を返す 失敗時にはexceptionが設定される
	 *            select以外の命令
	 * @return 行数 失敗-1
	 */
	protected int executeComonUpdate() {
		int updateRowCount = -1;

		if (pstmt!=null) {
			try {
				updateRowCount = pstmt.executeUpdate();
				con.commit();
			} catch (SQLException e) {
				//setException(e.getErrorCode());
				System.out.println("ERROR CODE :" + e.getErrorCode());
				System.out.println(pstmt.toString() + "を実行\n" + e.getMessage()
						+ "が発生。");
			}finally{
				closeConnection();
			}
//		} else if (!isError()) {
//			exception = new DatabaseRegistFailureException();
		}
		return updateRowCount;
	}


//	//@param sql  //作ったけど不要になったメソッド
//	/**
//	 * 汎用メソッド、select(選択)処理以外を行う、実行時に影響のあった行数を返す 失敗時にはexceptionが設定される
//	 *            select以外の命令
//	 * @return 行数 失敗-1
//	 */
//	protected int executeComonDelete() {
//		int updateRowCount = -1;
//
//		if (pstmt!=null) {
//			try {
//				updateRowCount = pstmt.executeUpdate();
//				con.commit();
//			} catch (SQLException e) {
//				System.out.println("ERROR CODE :" + e.getErrorCode());
//				System.out.println(pstmt.toString() + "を実行\n" + e.getMessage()
//						+ "が発生。");
//			}finally{
//				closeConnection();
//			}
//		}
//		return updateRowCount;
//	}
//
//	/**
//	 * 全件取得するselect
//	 *
//	 * @return 取得したレコード,失敗時null
//	 */
//	public List<BaseDto> select() {
//		list = null;
//		String sql = "select * from " + tableName + " where delete_flg = 0";
//		if (createConnection(sql)) {
//			executeComonQuery();
//		}
//		return list;
//	}
//
//	/**
//	 * 開始行数から指定した件数を取得するselect
//	 *
//	 * @param start
//	 *            開始行数
//	 * @param count
//	 *            取得件数
//	 * @return 取得したレコード,失敗時null
//	 */
//	public List<BaseDto> select(int start, int count) {
//		list = null;
//		String sql = null;
//		if(tableName.equals("product_master")) {
//			sql = "select RN,id,cd,name,price,distributer,regist_user,regist_date,"+
//			"update_user,update_date,delete_flg from "+
//			"( select ROWNUM as RN,id,cd,name,price,distributer,regist_user,"+
//			"regist_date,update_user,update_date,delete_flg from product_master "+
//			"where delete_flg = 0 order by id asc )"+
//			"where RN>?"+
//			"and RN<?";
//		}else if(tableName.equals("stock_master")) {
////			sql = "select * from(select ROWNUM as id,cd,amount,regist_user,regist_date,update_user,update_date,delete_flg from stock_master order by id asc )where id>? and id<?";
//			sql = "select RN,id,cd,amount,regist_user,regist_date,update_user,update_date,delete_flg from" +
//					 "(select ROWNUM as RN,id,cd,amount,regist_user,regist_date,update_user,update_date,delete_flg from stock_master where delete_flg = 0 order by id asc )" +
//					"where RN>? and RN<?";
//		}else if(tableName.equals("user_level_master")) {
//			sql = "select * from(select ROWNUM as id,user_level_id,name,delete_flg from user_level_master order by id asc )where id>? and id<?";
//		}else if(tableName.equals("user_master")) {
////			sql = "select * from(select ROWNUM as id,user_id,user_pw,user_level_id,regist_user,regist_date,update_user,update_date,delete_flg from user_master order by id asc )where id>? and id<?";
//			sql = "select RN,id,user_id,user_pw,user_level_id,regist_user,regist_date,update_user,update_date,delete_flg from"
//					+"(select ROWNUM as RN,id,user_id,user_pw,user_level_id,regist_user,regist_date,update_user,update_date,delete_flg from user_master where delete_flg = 0 order by id asc )"
//					+"where RN>? and RN<?";
//		}
//		if (createConnection(sql)) {
//			try {
//				pstmt.setInt(1, start);
//				pstmt.setInt(2, start + count +1);
//				executeComonQuery();
//			} catch (SQLException e) {
//				exception = new DatabaseFailureException();
//			}finally{
//				closeConnection();
//			}
//		}
//		return list;
//	}
//
//	/**
//	 * 指定したIDのデータを取得する
//	 *
//	 * @param id
//	 *            取得するID
//	 * @return 取得した1レコード,失敗時null
//	 */
//	public BasDto selectById(int id) {
//		list = null;
//		BasDto bean = null;
//		String sql = "select *  from " + tableName
//				+ " where delete_flg = 0 and id = ?";
//		if (createConnection(sql)) {
//			try {
//				pstmt.setInt(1, id);
//				if (executeComonQuery()) {
//
//					if (!isNull(list) && list.size() == 1) {
//						bean = list.get(0);
//					} else {
//						System.out.println(list.size());
//						exception = new RecordNotFoundException();
//					}
//				}
//			} catch (SQLException e) {
//				exception = new DatabaseFailureException();
//			}finally{
//				closeConnection();
//			}
//
//
//		}
//		return bean;
//	}
//
//	/**
//	 * 更新処理 失敗時はexceptionが設定される
//	 *
//	 * @param bean
//	 *            更新情報
//	 * @return 更新結果 成功 true 失敗 false
//	 */
//	public boolean update(BasDto bean) {
//		exception = new UndefinedMethodException();
//		return false;
//	}
//
//	/**
//	 * 登録処理 失敗時はexceptionが設定される
//	 *
//	 * @param bean
//	 *            登録情報
//	 * @return 登録結果 成功 true 失敗 false
//	 */
//	public boolean insert(BasDto bean) {
//		exception = new UndefinedMethodException();
//		return false;
//	}
//
//	/**
//	 * 削除処理 失敗時はexceptionが設定される
//	 *
//	 * @param bean
//	 *            削除情報
//	 * @return 削除結果 成功 true 失敗 false
//	 */
//	public boolean delete(BasDto bean) {
//		exception = new UndefinedMethodException();
//		return false;
//	}
//
//	/**
//	 * レコード件数を取得
//	 *
//	 * @return レコード件数 処理失敗時は-1
//	 */
//	public int getRowCount() {
//		int rowCount = -1;
//		String sql = "select count(id) as count from " + tableName
//				+ " where delete_flg = 0";
//
//		if (createConnection(sql)) {
//			try {
//				ResultSet rs;
//				rs = pstmt.executeQuery();
//				if (rs.next()) {
//					rowCount = rs.getInt("count");
//				} else {
//					exception = new DatabaseSelectFailureException();
//				}
//				rs.close();
//
//			} catch (SQLException e) {
//				exception = new DatabaseFailureException();
//				System.out.println(e.getMessage() + "が発生。SQL文を確認する");
//			}finally{
//				closeConnection();
//			}
//
//
//		}
//
//		return rowCount;
//	}
//
//	/**
//	 * 処理中にエラーが発生した場合はtrueになる
//	 *
//	 * @return エラー発生の有無
//	 */
//	public boolean isError() {
//		return !isNull(exception);
//	}
//
	/**
	 * 切断処理
	 */
	protected void closeConnection() {
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
			// ユーザに実害がないのでexceptionは設定しない
			System.out.println("closeConnection:" + e.getMessage());
		}
	}
//
//	/**
//	 * SQLのエラーコードに応じてエラーを設定
//	 *
//	 * @param errorCode
//	 *            SQLエラーコード
//	 */
//	protected abstract void setException(int errorCode);

}
